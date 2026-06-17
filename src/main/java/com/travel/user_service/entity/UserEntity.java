package com.travel.user_service.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;


@Entity
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Table(name=UserEntity.TABLE_NAME)
public class UserEntity {

    public static final String TABLE_NAME="users";
    public static final String ID="id";
    public static final String EMAIL="email";
    public static final String PASSWORD="password";
    public static final String USER_NAME="user_name";
    public static final String AVATAR_URL="avatar_url";
    public static final String REGISTERED_AT="registered_at";
    public static final String PHONE = "phone";
    public static final String UPDATED_DATE="updated_date";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name=ID)
    private int id;

    @Email
    @Column(name=EMAIL,nullable = false)
    private String email;


    @Column(name=PASSWORD, nullable = false)
    private String password;

    @Column(name = USER_NAME, nullable = false)
    private String userName;

    @Column(name = PHONE)
    private String phone;

    @Column(name = AVATAR_URL)
    private String avatarUrl;

    @CreationTimestamp
    @Column(name = REGISTERED_AT)
    private LocalDateTime registeredAt;

    @UpdateTimestamp
    @Column(name = UPDATED_DATE)
    private LocalDateTime updatedDate;

}
