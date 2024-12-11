package com.calendar.controller.user.controller;

import com.calendar.controller.user.dto.LoginRequestDto;
import com.calendar.controller.user.dto.UserResponseDto;
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
        UserResponseDto user = userLoginService.loginUser(dto);
        if(user != null) {
            req.getSession().setAttribute("user", user);
            req.getSession().setMaxInactiveInterval(1800);
            return ResponseEntity.ok("로그인 완료");
        }else{
            return ResponseEntity.status(401).body("이메일이나 비밀번호가 유효하지 않습니다.");
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpServletRequest req){
        req.getSession().invalidate();
        return ResponseEntity.ok("로그아웃 완료");
    }
}
