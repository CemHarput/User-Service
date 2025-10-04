package com.caseStudy.user_service.dto;

import com.caseStudy.user_service.model.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record UserCreateRequestDTO(@Email @NotBlank String email,@Pattern(regexp="^[\\p{L} ]+$") String fullName) {

    public User toEntity() {
        User user = new User();
        user.setEmail(this.email);
        user.setFullName(this.fullName);

        return user;
    }
}
