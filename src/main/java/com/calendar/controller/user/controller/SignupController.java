package com.calendar.controller.user.controller;

import com.calendar.controller.user.dto.SignupRequestDto;
import com.calendar.controller.user.dto.UserResponseDto;
import com.calendar.controller.user.service.UserSignupService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class SignupController {

    private final UserSignupService userSignupService;

    @PostMapping("/signup")
    public ResponseEntity<String> signup(
            @Valid @RequestBody SignupRequestDto dto,
            HttpServletRequest req){
        UserResponseDto user = userSignupService.registerUser(dto);

        req.getSession().setAttribute("user", user);
        req.getSession().setMaxInactiveInterval(1800);
        return ResponseEntity.ok("회원가입 완료");
    }
}
