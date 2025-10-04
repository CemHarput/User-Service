package com.caseStudy.user_service.dto;

import com.caseStudy.user_service.model.User;

public record UserDTO(String email,String fullname,String status,String createDate,String updateDate) {
    public static UserDTO convertFromUser(User user){
        return new UserDTO(
                user.getEmail(),
                user.getFullName(),
                user.getStatus().toString(),
                user.getCreatedAt().toString(),
                user.getUpdatedAt().toString()
        );
    }
}
