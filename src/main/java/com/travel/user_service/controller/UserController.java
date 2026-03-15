package com.travel.user_service.controller;

import com.travel.user_service.dto.request.LoginRequest;
import com.travel.user_service.dto.request.UserRequest;
import com.travel.user_service.dto.request.UserUpdateRequest;
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
    @GetMapping("/{id}")
    public UserResponse getProfile(@PathVariable int id) {
        return userService.getUserProfile(id);
    }
    @PatchMapping("/{id}")
    public UserResponse updateProfile(@PathVariable int id, @Valid @RequestBody UserUpdateRequest request) {
        return userService.updateUserProfile(id, request);
    }
    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable int id) {
        userService.deleteUser(id);
    }
    @PostMapping("/login")
    public LoginResponse login(@Valid @RequestBody LoginRequest request) {
        return userService.login(request);
    }
}
