package com.calendar.service.sign;

import com.calendar.dto.request.user.SignupRequestDto;
import com.calendar.mapper.UserMapper;
import com.calendar.model.User;
import com.calendar.repository.UserRepository;
import com.calendar.service.validation.UserValidationService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class UserSignupService {

    private final UserRepository userRepository;
    private final UserValidationService userValidationService;
    private final PasswordEncoder passwordEncoder;

    public Long registerUser(SignupRequestDto dto){
        userValidationService.isEmailTaken(dto.getEmail());
        String encodedPassword = passwordEncoder.encode(dto.getPassword());

        User user = UserMapper.toEntity(dto, encodedPassword);
        userRepository.save(user);
        return user.getId();
    }
}
