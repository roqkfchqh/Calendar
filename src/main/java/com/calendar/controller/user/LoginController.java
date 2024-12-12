package com.calendar.controller.user;

import com.calendar.common.exception.CustomException;
import com.calendar.common.exception.ErrorCode;
import com.calendar.dto.request.user.LoginRequestDto;
import com.calendar.model.User;
import com.calendar.service.sign.UserLoginService;
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
        if(req.getSession().getAttribute("user") == null){
            throw new CustomException(ErrorCode.BAD_GATEWAY);
        }
        SessionAndCookie.delete(req, res);
        return ResponseEntity.ok("로그아웃 완료");
    }
}
