package com.calendar.user.service;

import com.calendar.common.exception.CustomException;
import com.calendar.common.exception.ErrorCode;
import com.calendar.user.model.User;
import com.calendar.user.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserValidationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public User validateUser(HttpServletRequest req) {
        User sessionUser = (User) req.getSession().getAttribute("user");
        if(sessionUser == null){
            throw new CustomException(ErrorCode.LOGIN_REQUIRED);
        }

        return userRepository.findById(sessionUser.getId())
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
    }

    public void validatePassword(String sessionPassword, String inputPassword){
        if(!passwordEncoder.matches(sessionPassword, inputPassword)){
            throw new CustomException(ErrorCode.WRONG_PASSWORD);
        }
    }

    public void isEmailTaken(String email){
        if(userRepository.findByEmail(email).isPresent()){
            throw new CustomException(ErrorCode.ALREADY_USED_EMAIL);
        }
    }
}
