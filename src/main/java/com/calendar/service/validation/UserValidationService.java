package com.calendar.service.validation;

import com.calendar.exception.CustomException;
import com.calendar.exception.ErrorCode;
import com.calendar.model.User;
import com.calendar.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserValidationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public User validateUser(Long userId) {
        if(userId == null){
            throw new CustomException(ErrorCode.LOGIN_REQUIRED);
        }

        return userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
    }

    public void validatePassword(String sessionPassword, String inputPassword){
        if(!passwordEncoder.matches(inputPassword, sessionPassword)){
            throw new CustomException(ErrorCode.WRONG_PASSWORD);
        }
    }

    public void isEmailTaken(String email){
        if(userRepository.findByEmail(email).isPresent()){
            throw new CustomException(ErrorCode.ALREADY_USED_EMAIL);
        }
    }
}
