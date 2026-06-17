package com.travel.user_service.controller;

import com.travel.user_service.dto.request.*;
import com.travel.user_service.dto.responce.LoginResponse;
import com.travel.user_service.dto.responce.UserResponse;
import com.travel.user_service.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    public UserResponse register(@Valid @RequestBody UserRequest request){
    return userService.register(request);
    }

    @GetMapping("/me")
    public UserResponse getProfile(@RequestHeader("X-User-Id") int userId) {
        return userService.getUserProfile(userId);
    }

    @PatchMapping("/me")
    public UserResponse updateProfile(@RequestHeader("X-User-Id") int userId, @Valid @RequestBody UserUpdateRequest request) {
        return userService.updateUserProfile(userId, request);
    }


    @DeleteMapping("/me")
    public void deleteUser(@RequestHeader("X-User-Id") int userId) {
        userService.deleteUser(userId);
    }

    @PostMapping("/login")
    public LoginResponse login(@Valid @RequestBody LoginRequest request) {
        return userService.login(request);
    }

    @PostMapping("/forgot-password")
    public void forgotPassword(@Valid @RequestBody ForgotPasswordRequest request) {
        userService.processForgotPassword(request.getEmail());
    }

    @PostMapping("/reset-password")
    public void resetPassword(@Valid @RequestBody ResetPasswordRequest request) {
        userService.resetPassword(request.getToken(), request.getNewPassword());
    }
}
