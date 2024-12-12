package com.calendar.service.sign;

import com.calendar.exception.CustomException;
import com.calendar.exception.ErrorCode;
import com.calendar.dto.request.user.LoginRequestDto;
import com.calendar.model.User;
import com.calendar.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserLoginService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional(readOnly = true)
    public User loginUser(LoginRequestDto dto){
        Optional<User> user = userRepository.findByEmail(dto.getEmail());
        if(user.isEmpty() || !passwordEncoder.matches(dto.getPassword(), user.get().getPassword())){
            throw new CustomException(ErrorCode.WRONG_EMAIL_OR_PASSWORD);
        }
        return user.get();
    }
}