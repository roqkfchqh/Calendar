package com.calendar.service.sign;

import com.calendar.exception.CustomException;
import com.calendar.exception.ErrorCode;
import com.calendar.dto.request.user.LoginRequestDto;
import com.calendar.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserLoginService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    //로그인
    public Long loginUser(LoginRequestDto dto){
        Long userId = userRepository.findIdByEmail(dto.getEmail());
        if(userId == null || !passwordEncoder.matches(dto.getPassword(), userRepository.findPasswordById(userId))){
            throw new CustomException(ErrorCode.WRONG_EMAIL_OR_PASSWORD);
        }
        return userId;
    }
}