package com.calendar.controller.user.controller;

import com.calendar.controller.user.dto.SignupRequestDto;
import com.calendar.controller.user.model.User;
import com.calendar.controller.user.service.UserSignupService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
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
            HttpServletRequest req,
            HttpServletResponse res) {
        User user = userSignupService.registerUser(dto);

        SessionAndCookie.remember(req, res, user);

        return ResponseEntity.ok("회원가입 완료");
    }
}
