package com.caseStudy.user_service.model;


import com.caseStudy.user_service.enums.UserStatus;
import com.caseStudy.user_service.util.NameNormalizer;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import org.hibernate.annotations.UuidGenerator;

import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "users",
        uniqueConstraints = @UniqueConstraint(name = "uk_users_email", columnNames = "email"),
        indexes = {
                @Index(name = "idx_users_normalized_name", columnList = "normalized_name"),
                @Index(name = "idx_users_status", columnList = "status")
        })
public class User extends  AuditBase{

    @Id
    @Column(name = "id", nullable = false, updatable = false, columnDefinition = "uuid")
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Email
    @NotBlank
    @Column(name = "email", nullable = false, length = 320)
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    private UserStatus status = UserStatus.PENDING;


    @NotBlank
    @Pattern(regexp = "^[\\p{L} ]+$",
            message = "Full name must contain letters and spaces only")
    @Column(name = "full_name", nullable = false, length = 150)
    private String fullName;


    @Column(name = "normalized_name", nullable = false, length = 150)
    private String normalizedName;



    @Override
    protected void beforeSaveOrUpdate() {
        if (fullName != null) {
            this.fullName = fullName.trim();
            this.normalizedName = NameNormalizer.normalize(this.fullName);
        }
        if (email != null) {
            this.email = email.trim().toLowerCase();
        }
    }


    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public UserStatus getStatus() {
        return status;
    }

    public void setStatus(UserStatus status) {
        this.status = status;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getNormalizedName() {
        return normalizedName;
    }

    public void setNormalizedName(String normalizedName) {
        this.normalizedName = normalizedName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User user)) return false;
        return Objects.equals(getId(), user.getId()) && Objects.equals(getEmail(), user.getEmail()) && getStatus() == user.getStatus() && Objects.equals(getFullName(), user.getFullName()) && Objects.equals(getNormalizedName(), user.getNormalizedName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getEmail(), getStatus(), getFullName(), getNormalizedName());
    }
}
