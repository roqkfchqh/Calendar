package com.calendar.controller.user.service;

import com.calendar.controller.user.dto.SignupRequestDto;
import com.calendar.controller.user.dto.UserMapper;
import com.calendar.controller.user.dto.UserResponseDto;
import com.calendar.controller.user.model.User;
import com.calendar.controller.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserSignupService {

    private final UserRepository userRepository;
    private final UserValidationService userValidationService;

    public UserResponseDto registerUser(SignupRequestDto dto){
        userValidationService.isEmailTaken(dto.getEmail());
        userValidationService.isUsernameTaken(dto.getName());

        User user = UserMapper.toEntity(dto);
        userRepository.save(user);
        return UserMapper.toDto(user);
    }
}
