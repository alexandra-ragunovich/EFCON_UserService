package com.travel.user_service.dto.responce;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserResponse {
    private int id;
    private String email;
    private String userName;
    private String phone;
    private String avatarUrl;
    private LocalDateTime registeredAt;
}
