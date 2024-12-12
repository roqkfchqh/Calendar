package com.calendar.service.crud;

import com.calendar.exception.CustomException;
import com.calendar.exception.ErrorCode;
import com.calendar.repository.CalendarRepository;
import com.calendar.dto.request.user.CurrentPasswordRequestDto;
import com.calendar.dto.request.user.UpdateRequestDto;
import com.calendar.mapper.UserMapper;
import com.calendar.dto.response.UserResponseDto;
import com.calendar.model.User;
import com.calendar.repository.UserRepository;
import com.calendar.service.validation.UserValidationService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {

    private final UserRepository userRepository;
    private final CalendarRepository calendarRepository;
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

        user.updateUser(dto.getName(), dto.getNewPassword());
        return UserMapper.toDto(user);
    }

    //delete
    public void deleteUser(HttpServletRequest req, CurrentPasswordRequestDto currentPasswordRequestDto){
        User user = userValidationService.validateUser(req);
        userValidationService.validatePassword(user.getPassword(), currentPasswordRequestDto.getCurrentPassword());
        calendarRepository.deleteByUserId(user.getId());
        userRepository.deleteById(user.getId());
    }

    //get(for cookie)
    public User getUserByEmail(String email){
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
    }
}
