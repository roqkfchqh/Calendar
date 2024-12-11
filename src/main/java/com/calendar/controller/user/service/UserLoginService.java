package com.calendar.controller.user.service;

import com.calendar.common.exception.BadInputException;
import com.calendar.controller.user.dto.LoginRequestDto;
import com.calendar.controller.user.model.User;
import com.calendar.controller.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class UserLoginService {

    private final UserRepository userRepository;

    public User loginUser(LoginRequestDto dto){
        User user = userRepository.findByEmail(dto.getEmail())
                .orElseThrow(() -> new BadInputException("이메일이나 비밀번호가 유효하지 않습니다."));
        if(!user.getPassword().equals(dto.getPassword())){
            throw new BadInputException("이메일이나 비밀번호가 유효하지 않습니다.");
        }
        return user;
    }
}
