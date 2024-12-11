package com.calendar.controller.user.service;

import com.calendar.common.exception.BadInputException;
import com.calendar.controller.user.model.User;
import com.calendar.controller.user.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserValidationService {

    private final UserRepository userRepository;

    public User validateUser(HttpServletRequest req) {
        User sessionUser = (User) req.getSession().getAttribute("user");
        if(sessionUser == null){
            throw new BadInputException("로그인이 필요합니다.");
        }

        return userRepository.findById(sessionUser.getId())
                .orElseThrow(() -> new BadInputException("해당 유저를 찾을 수 없습니다."));
    }

    public void validatePassword(String sessionPassword, String inputPassword){
        if(!sessionPassword.equals(inputPassword)){
            throw new BadInputException("비밀번호가 일치하지 않습니다.");
        }
    }

    public void isEmailTaken(String email){
        if(userRepository.findByEmail(email).isPresent()){
            throw new BadInputException("이미 사용중인 이메일입니다.");
        }
    }
}
