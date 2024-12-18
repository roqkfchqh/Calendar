package com.calendar.controller.user;

import com.calendar.dto.request.user.SignupRequestDto;
import com.calendar.service.sign.UserSignupService;
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
        Long userId = userSignupService.registerUser(dto);

        SessionAndCookie.remember(req, res, userId);

        return ResponseEntity.ok("회원가입 완료");
    }
}
