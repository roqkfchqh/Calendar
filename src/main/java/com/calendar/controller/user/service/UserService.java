package com.calendar.controller.user.service;

import com.calendar.common.exception.BadInputException;
import com.calendar.controller.user.dto.*;
import com.calendar.controller.user.model.User;
import com.calendar.controller.user.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserValidationService userValidationService;

    //read
    public UserResponseDto readUser(HttpServletRequest req){
        User user = userValidationService.validateUser(req);
        return UserMapper.toDto(user);
    }

    //update
    public UserResponseDto updateUser(
            UpdateRequestDto dto,
            HttpServletRequest req){

        User user = userValidationService.validateUser(req);
        userValidationService.validatePassword(user.getPassword(), dto.getCurrentPassword());

        User updatedUser = user.updateUser(dto.getName(), dto.getNewPassword());
        userRepository.save(updatedUser);
        return UserMapper.toDto(updatedUser);
    }

    //delete
    public void deleteUser(HttpServletRequest req, CurrentPasswordRequestDto currentPasswordRequestDto){
        User user = userValidationService.validateUser(req);
        userValidationService.validatePassword(user.getPassword(), currentPasswordRequestDto.getCurrentPassword());
        userRepository.deleteById(user.getId());
    }

    //get(for cookie)
    public User getUserByEmail(String email){
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new BadInputException("존재하지 않는 사용자입니다."));
    }
}
