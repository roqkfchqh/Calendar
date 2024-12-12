package com.calendar.dto.mapper;

import com.calendar.dto.request.user.SignupRequestDto;
import com.calendar.dto.response.UserResponseDto;
import com.calendar.model.User;

public class UserMapper {

    //entity -> dto
    public static UserResponseDto toDto(User user){
        return UserResponseDto.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .createdAt(user.getCreatedAt().toString())
                .build();
    }

    //signupDto -> entity
    public static User toEntity(SignupRequestDto dto, String encodedPassword){
        return User.builder()
                .name(dto.getName())
                .email(dto.getEmail())
                .password(encodedPassword)
                .build();
    }
}
