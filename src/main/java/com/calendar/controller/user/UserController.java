package com.calendar.controller.user;

import com.calendar.dto.request.user.CurrentPasswordRequestDto;
import com.calendar.dto.request.user.UpdateRequestDto;
import com.calendar.dto.response.UserResponseDto;
import com.calendar.service.sign.SessionAndCookieSettingService;
import com.calendar.service.crud.UserService;
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
    private final SessionAndCookieSettingService sessionAndCookieSettingService;

    //read
    @GetMapping
    public ResponseEntity<UserResponseDto> getUser(
            HttpServletRequest req){
        Long userId = (Long) req.getSession().getAttribute("userId");
        return ResponseEntity.ok(userService.readUser(userId));
    }

    //update
    @PatchMapping
    public ResponseEntity<UserResponseDto> updateUser(
            @Valid @RequestBody UpdateRequestDto dto,
            HttpServletRequest req){
        Long userId = (Long) req.getSession().getAttribute("userId");
        UserResponseDto user = userService.updateUser(dto, userId);
        return ResponseEntity.ok(user);
    }

    //delete
    @PostMapping("/delete")
    public ResponseEntity<String> deleteUser(
            @RequestBody CurrentPasswordRequestDto currentPasswordRequestDto,
            HttpServletRequest req,
            HttpServletResponse res){
        Long userId = (Long) req.getSession().getAttribute("userId");
        userService.deleteUser(userId, currentPasswordRequestDto);
        req.getSession().invalidate();
        sessionAndCookieSettingService.delete(req, res);
        return ResponseEntity.ok("회원 탈퇴가 정상적으로 완료되었습니다.");
    }
}
