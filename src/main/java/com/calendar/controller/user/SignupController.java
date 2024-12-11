package com.calendar.controller.user;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class SignupController {

    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<String> signup(
            @RequestParam String name,
            @RequestParam String email,
            @RequestParam String password){

        if(userService.isEmailTaken(email)){
            return ResponseEntity.badRequest().body("이미 사용중인 이메일입니다.");
        }

        userService.registerUser(name, email, password);
        return ResponseEntity.ok("회원가입 완료");
    }
}
