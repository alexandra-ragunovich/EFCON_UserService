package com.travel.user_service.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Table(name = PasswordResetTokenEntity.TABLE_NAME)
public class PasswordResetTokenEntity {

    public static final String TABLE_NAME = "password_reset_tokens";
    public static final String ID = "id";
    public static final String TOKEN = "token";
    public static final String USER_ID = "user_id";
    public static final String EXPIRY_DATE = "expiry_date";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = ID)
    private int id;

    @Column(name = TOKEN, nullable = false, unique = true)
    private String token;

    @OneToOne(targetEntity = UserEntity.class, fetch = FetchType.EAGER)
    @JoinColumn(name = USER_ID, nullable = false)
    private UserEntity user;

    @Column(name = EXPIRY_DATE, nullable = false)
    private LocalDateTime expiryDate;

}