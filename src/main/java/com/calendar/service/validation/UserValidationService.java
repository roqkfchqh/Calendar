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

    //user 유효한지(repository 호출 여기서 하고 User 반환해서 사용함) 또는 only validate 용도
    public User validateUser(Long userId) {
        if(userId == null){
            throw new CustomException(ErrorCode.LOGIN_REQUIRED);
        }

        return userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
    }

    //user password 유효한지
    public void validatePassword(Long userId, String inputPassword){
        if(!passwordEncoder.matches(inputPassword, userRepository.findPasswordById(userId))){
            throw new CustomException(ErrorCode.WRONG_PASSWORD);
        }
    }

    //이미 사용중인 이메일인지
    public void isEmailTaken(String email){
        if(userRepository.findByEmail(email).isPresent()){
            throw new CustomException(ErrorCode.ALREADY_USED_EMAIL);
        }
    }
}
