package com.calendar.user.service;

import com.calendar.common.encoder.BcryptEncoder;
import com.calendar.common.exception.CustomException;
import com.calendar.common.exception.ErrorCode;
import com.calendar.user.dto.LoginRequestDto;
import com.calendar.user.model.User;
import com.calendar.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserLoginService {

    private final UserRepository userRepository;
    private final BcryptEncoder bcryptEncoder;

    @Transactional(readOnly = true)
    public User loginUser(LoginRequestDto dto){
        Optional<User> user = userRepository.findByEmail(dto.getEmail());
        if(user.isEmpty() || !bcryptEncoder.matches(dto.getPassword(), user.get().getPassword())){
            throw new CustomException(ErrorCode.WRONG_EMAIL_OR_PASSWORD);
        }
        return user.get();
    }
}