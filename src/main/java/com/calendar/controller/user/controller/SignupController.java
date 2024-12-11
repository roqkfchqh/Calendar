package com.calendar.controller.user.controller;

import com.calendar.controller.user.dto.SignupRequestDto;
import com.calendar.controller.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class SignupController {

    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<String> signup(
            @Valid @RequestBody SignupRequestDto signupRequestDto){
        userService.registerUser(signupRequestDto);
        return ResponseEntity.ok("회원가입 완료");
    }
}
