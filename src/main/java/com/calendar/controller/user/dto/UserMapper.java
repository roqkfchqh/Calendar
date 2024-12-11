package com.calendar.controller.user.dto;

import com.calendar.controller.user.model.User;

public class UserMapper {

    //entity -> dto
    public static UserResponseDto toDto(User user){
        return UserResponseDto.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .createdAt(String.valueOf(user.getCreatedAt()))
                .build();
    }

    //loginDto -> entity
}
