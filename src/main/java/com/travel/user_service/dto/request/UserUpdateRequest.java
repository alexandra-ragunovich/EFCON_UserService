package com.travel.user_service.dto.request;

import jakarta.validation.constraints.Email;
import lombok.Data;

@Data
public class UserUpdateRequest {
    @Email
    private String email;
    private String userName;
    private String phone;
    private String avatarUrl;
}
