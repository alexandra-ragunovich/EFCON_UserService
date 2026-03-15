package com.travel.user_service.mappers;

import com.travel.user_service.dto.request.UserRequest;
import com.travel.user_service.dto.responce.UserResponse;
import com.travel.user_service.dto.request.UserUpdateRequest;
import com.travel.user_service.entity.UserEntity;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UserMapper {
    public UserEntity toEntity(UserRequest request){
        UserEntity userEntity=new UserEntity();
        userEntity.setEmail(request.getEmail());
        userEntity.setPassword(request.getPassword());
        userEntity.setUserName(request.getUserName());
        userEntity.setPhone(request.getPhone());
        userEntity.setAvatarUrl(request.getAvatarUrl());
return userEntity;
    }
    public UserResponse toResponse(UserEntity userEntity){
        UserResponse userResponse=new UserResponse();
        userResponse.setId(userEntity.getId());
        userResponse.setEmail(userEntity.getEmail());
        userResponse.setUserName(userEntity.getUserName());
        userResponse.setPhone(userEntity.getPhone());
        userResponse.setAvatarUrl(userEntity.getAvatarUrl());
        userResponse.setRegisteredAt(userEntity.getRegisteredAt());
        return userResponse;
    }
    public void updateEntityFromRequest(UserUpdateRequest request, UserEntity userEntity) {

        Optional.ofNullable(request.getUserName()).ifPresent(userEntity::setUserName);
        Optional.ofNullable(request.getEmail()).ifPresent(userEntity::setEmail);
        Optional.ofNullable(request.getPhone()).ifPresent(userEntity::setPhone);
        Optional.ofNullable(request.getAvatarUrl()).ifPresent(userEntity::setAvatarUrl);

    }

}
