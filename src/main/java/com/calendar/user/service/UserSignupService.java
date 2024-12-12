package com.calendar.user.service;

import com.calendar.common.encoder.BcryptEncoder;
import com.calendar.user.dto.SignupRequestDto;
import com.calendar.user.dto.UserMapper;
import com.calendar.user.model.User;
import com.calendar.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class UserSignupService {

    private final UserRepository userRepository;
    private final UserValidationService userValidationService;
    private final BcryptEncoder bcryptEncoder;

    public User registerUser(SignupRequestDto dto){
        userValidationService.isEmailTaken(dto.getEmail());
        String encodedPassword = bcryptEncoder.encode(dto.getPassword());

        User user = UserMapper.toEntity(dto, encodedPassword);
        userRepository.save(user);
        return user;
    }
}
