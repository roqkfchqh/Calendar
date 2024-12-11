package com.calendar.controller.user.controller;

import com.calendar.controller.user.dto.UpdateRequestDto;
import com.calendar.controller.user.dto.UserResponseDto;
import com.calendar.controller.user.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    //read
    @GetMapping
    public ResponseEntity<UserResponseDto> getUser(
            HttpServletRequest req){
        return ResponseEntity.ok(userService.readUser(req));
    }

    //update
    @PatchMapping
    public ResponseEntity<UserResponseDto> updateUser(
            @Valid @RequestBody UpdateRequestDto dto,
            HttpServletRequest req,
            HttpServletResponse res){
        UserResponseDto user = userService.updateUser(dto, req);
        SessionAndCookie.extracted(req, res, user);
        return ResponseEntity.ok(user);
    }

    //delete
    @DeleteMapping
    public ResponseEntity<UserResponseDto> deleteUser(
            HttpServletRequest req){
        userService.deleteUser(req);
        req.getSession().invalidate();
        return ResponseEntity.noContent().build();
    }

}
