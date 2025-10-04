package com.caseStudy.user_service.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record UserUpdateRequestDTO(@Email @NotBlank String email, @Pattern(regexp="^[\\p{L} ]+$") String fullname) {
}
