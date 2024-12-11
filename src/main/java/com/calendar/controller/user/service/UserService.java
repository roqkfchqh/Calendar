package com.calendar.controller.user.service;

import com.calendar.common.exception.BadInputException;
import com.calendar.controller.user.dto.LoginRequestDto;
import com.calendar.controller.user.dto.SignupRequestDto;
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

    public void registerUser(SignupRequestDto signupRequestDto){
        if(isEmailTaken(signupRequestDto.getEmail())){
            throw new BadInputException("이미 사용중인 이메일입니다.");
        }
        User user = User.builder()
                .name(signupRequestDto.getName())
                .email(signupRequestDto.getEmail())
                .password(signupRequestDto.getPassword())
                .build();

        userRepository.save(user);
    }

    public User validateUser(LoginRequestDto loginRequestDto){
        return userRepository.findByEmailAndPassword(loginRequestDto.getEmail(), loginRequestDto.getPassword())
                .orElse(null);
    }
}
