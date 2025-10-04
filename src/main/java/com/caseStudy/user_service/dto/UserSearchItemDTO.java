package com.caseStudy.user_service.dto;

import com.caseStudy.user_service.enums.UserStatus;
import com.caseStudy.user_service.model.User;

public record UserSearchItemDTO(String email, String fullName, UserStatus userStatus) {

    public static UserSearchItemDTO from(User user) {
        return new UserSearchItemDTO(
                user.getEmail(),
                user.getFullName(),
                user.getStatus()
        );
    }
}
