package com.calendar.controller.user.service;

import com.calendar.common.exception.BadInputException;
import com.calendar.controller.user.dto.LoginRequestDto;
import com.calendar.controller.user.dto.SignupRequestDto;
import com.calendar.controller.user.dto.UserMapper;
import com.calendar.controller.user.dto.UserResponseDto;
import com.calendar.controller.user.model.User;
import com.calendar.controller.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public boolean isEmailTaken(String email){
        return userRepository.findByEmail(email).isPresent();
    }

    public UserResponseDto registerUser(SignupRequestDto dto){
        if(isEmailTaken(dto.getEmail())){
            throw new BadInputException("이미 사용중인 이메일입니다.");
        }
        User user = UserMapper.toEntity(dto);
        userRepository.save(user);
        return UserMapper.toDto(user);
    }

    public User validateUser(LoginRequestDto dto){
        return userRepository.findByEmailAndPassword(dto.getEmail(), dto.getPassword())
                .orElse(null);
    }
}
