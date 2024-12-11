package com.calendar.controller.user.service;

import com.calendar.common.exception.BadInputException;
import com.calendar.controller.user.dto.*;
import com.calendar.controller.user.model.User;
import com.calendar.controller.user.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserValidationService userValidationService;

    public UserResponseDto readUser(HttpServletRequest req){
        User user = userValidationService.validateUser(req);
        return UserMapper.toDto(user);
    }

    public UserResponseDto updateUser(UpdateRequestDto dto, HttpServletRequest req){
        User user = userValidationService.validateUser(req);
        userValidationService.isUsernameTaken(dto.getName());

        User updatedUser = user.updateUser(dto.getName(), dto.getPassword());
        userRepository.save(updatedUser);
        return UserMapper.toDto(updatedUser);
    }

    public void deleteUser(HttpServletRequest req){
        User user = userValidationService.validateUser(req);
        userRepository.deleteById(user.getId());
    }

    public UserResponseDto getUserByEmail(String email){
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new BadInputException("존재하지 않는 사용자입니다."));
        return UserMapper.toDto(user);
    }
}
