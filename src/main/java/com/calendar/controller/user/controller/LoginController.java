package com.calendar.controller.user.controller;

import com.calendar.common.exception.CustomException;
import com.calendar.common.exception.ErrorCode;
import com.calendar.controller.user.dto.LoginRequestDto;
import com.calendar.controller.user.model.User;
import com.calendar.controller.user.service.UserLoginService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class LoginController {

    private final UserLoginService userLoginService;

    @PostMapping("/login")
    public ResponseEntity<String> login(
            @RequestBody LoginRequestDto dto,
            HttpServletRequest req,
            HttpServletResponse res){
        User user = userLoginService.loginUser(dto);
        if(user != null) {
            SessionAndCookie.remember(req, res, user);

            return ResponseEntity.ok("로그인 완료");
        }else{
            throw new CustomException(ErrorCode.WRONG_EMAIL_OR_PASSWORD);
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(
            HttpServletRequest req,
            HttpServletResponse res){
        SessionAndCookie.delete(req, res);
        return ResponseEntity.ok("로그아웃 완료");
    }
}
