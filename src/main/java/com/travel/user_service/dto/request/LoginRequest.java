package com.travel.user_service.dto.request;

import lombok.Data;
import jakarta.validation.constraints.NotBlank;


@Data
public class LoginRequest {

    @NotBlank
    private String email;

    @NotBlank
    private String password;
}
