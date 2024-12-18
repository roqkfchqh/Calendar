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
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final CalendarRepository calendarRepository;
    private final UserValidationService userValidationService;

    //read
    public UserResponseDto readUser(Long userId){
        User user = userValidationService.validateUser(userId);
        return UserMapper.toDto(user);
    }

    //update
    @Transactional
    public UserResponseDto updateUser(
            UpdateRequestDto dto,
            Long userId){

        User user = userValidationService.validateUser(userId);
        userValidationService.validatePassword(user.getPassword(), dto.getCurrentPassword());

        user.updateUser(dto.getName(), dto.getNewPassword());
        return UserMapper.toDto(user);
    }

    //delete
    public void deleteUser(Long userId, CurrentPasswordRequestDto currentPasswordRequestDto){
        User user = userValidationService.validateUser(userId);
        userValidationService.validatePassword(user.getPassword(), currentPasswordRequestDto.getCurrentPassword());
        calendarRepository.deleteByUserId(user.getId());
        userRepository.deleteById(user.getId());
    }

    //get(for cookie)
    public User getUserById(Long userId){
        return userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
    }
}
