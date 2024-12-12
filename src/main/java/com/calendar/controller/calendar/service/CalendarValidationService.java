package com.calendar.controller.calendar.service;

import com.calendar.common.exception.CustomException;
import com.calendar.common.exception.ErrorCode;
import com.calendar.controller.calendar.model.Calendar;
import com.calendar.controller.calendar.repository.CalendarRepository;
import com.calendar.controller.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CalendarValidationService {

    private final CalendarRepository calendarRepository;

    public Calendar validateCalendar(Long id) {
        return calendarRepository.findByIdWithUser(id)
                .orElseThrow(() -> new CustomException(ErrorCode.CONTENT_NOT_FOUND));
    }

    public void authorityExtracted(Calendar calendar, User user){
        if(!calendar.getUser().getId().equals(user.getId())){
            throw new CustomException(ErrorCode.FORBIDDEN_OPERATION);
        }
    }
}
