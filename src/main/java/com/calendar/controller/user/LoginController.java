package com.calendar.controller.user;

import com.calendar.exception.CustomException;
import com.calendar.exception.ErrorCode;
import com.calendar.dto.request.user.LoginRequestDto;
import com.calendar.service.sign.SessionAndCookieSettingService;
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
    private final SessionAndCookieSettingService sessionAndCookieSettingService;

    //로그인
    @PostMapping("/login")
    public ResponseEntity<String> login(
            @RequestBody LoginRequestDto dto,
            HttpServletRequest req,
            HttpServletResponse res){
        Long userId = userLoginService.loginUser(dto);
        if(userId != null) {
            sessionAndCookieSettingService.remember(req, res, userId);

            return ResponseEntity.ok("로그인 완료");
        }else{
            throw new CustomException(ErrorCode.WRONG_EMAIL_OR_PASSWORD);
        }
    }

    //로그아웃
    @PostMapping("/logout")
    public ResponseEntity<String> logout(
            HttpServletRequest req,
            HttpServletResponse res){
        if(req.getSession().getAttribute("userId") == null){
            throw new CustomException(ErrorCode.BAD_GATEWAY);
        }
        sessionAndCookieSettingService.delete(req, res);
        return ResponseEntity.ok("로그아웃 완료");
    }
}
