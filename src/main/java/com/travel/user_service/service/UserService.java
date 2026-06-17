package com.travel.user_service.service;

import com.travel.user_service.dto.request.LoginRequest;
import com.travel.user_service.dto.request.UserRequest;
import com.travel.user_service.dto.request.UserUpdateRequest;
import com.travel.user_service.dto.responce.LoginResponse;
import com.travel.user_service.dto.responce.UserResponse;
import com.travel.user_service.entity.PasswordResetTokenEntity;
import com.travel.user_service.entity.UserEntity;
import com.travel.user_service.jwt.JwtService;
import com.travel.user_service.mappers.UserMapper;
import com.travel.user_service.repository.PasswordResetTokenRepository;
import com.travel.user_service.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.UUID;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private static final String INVALID_CREDENTIALS = "Неверный email или пароль";
    private final PasswordResetTokenRepository tokenRepository;
    private final EmailService emailService;

    @Transactional
    public UserResponse register(UserRequest request){

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Пользователь с таким email уже существует!");
        }

        UserEntity user=userMapper.toEntity(request);
        String encodedPassword=passwordEncoder.encode(request.getPassword());
        user.setPassword(encodedPassword);
        UserEntity savedUser = userRepository.save(user);
        return userMapper.toResponse(savedUser);
    }
    public LoginResponse login(LoginRequest request) {

        UserEntity user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException(INVALID_CREDENTIALS));
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException(INVALID_CREDENTIALS);
        }
        String token = jwtService.generateToken(user.getEmail(), user.getId());
        return new LoginResponse(token);
    }

    public UserResponse getUserProfile(int id) {

        return userMapper.toResponse(getUserOrThrow(id));
    }

    @Transactional
    public void deleteUser(int id) {

        userRepository.delete(getUserOrThrow(id));
    }

    @Transactional
    public UserResponse updateUserProfile(int id, UserUpdateRequest request) {

        UserEntity user = getUserOrThrow(id);
        userMapper.updateEntityFromRequest(request, user);
        UserEntity updatedUser = userRepository.save(user);
        return userMapper.toResponse(updatedUser);
}
    private UserEntity getUserOrThrow(int id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Пользователь не найден"));
    }

    @Transactional
    public void processForgotPassword(String email) {
        UserEntity user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Пользователь не найден"));
        tokenRepository.deleteByUserId(user.getId());
        String token = UUID.randomUUID().toString();
        PasswordResetTokenEntity tokenEntity = new PasswordResetTokenEntity();
        tokenEntity.setToken(token);
        tokenEntity.setUser(user);
        tokenEntity.setExpiryDate(LocalDateTime.now().plusHours(1));
        tokenRepository.save(tokenEntity);
        emailService.sendPasswordResetEmail(user.getEmail(), token);
    }

    @Transactional
    public void resetPassword(String token, String newPassword) {
        PasswordResetTokenEntity tokenEntity = tokenRepository.findByToken(token)
                .orElseThrow(() -> new RuntimeException("Неверный токен сброса"));

        if (tokenEntity.getExpiryDate().isBefore(LocalDateTime.now())) {
            tokenRepository.delete(tokenEntity);
            throw new RuntimeException("Срок действия ссылки истек");
        }
        UserEntity user = tokenEntity.getUser();
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
        tokenRepository.delete(tokenEntity);
    }

}
