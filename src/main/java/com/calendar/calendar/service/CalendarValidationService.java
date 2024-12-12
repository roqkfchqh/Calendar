package com.calendar.calendar.service;

import com.calendar.common.exception.CustomException;
import com.calendar.common.exception.ErrorCode;
import com.calendar.calendar.model.Calendar;
import com.calendar.calendar.repository.CalendarRepository;
import com.calendar.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
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
