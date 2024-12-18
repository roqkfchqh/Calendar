package com.calendar.service.validation;

import com.calendar.exception.CustomException;
import com.calendar.exception.ErrorCode;
import com.calendar.model.Calendar;
import com.calendar.repository.CalendarRepository;
import com.calendar.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CalendarValidationService {

    private final CalendarRepository calendarRepository;

    public Calendar validateCalendar(Long id) {
        return calendarRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.CONTENT_NOT_FOUND));
    }

    public void authorityExtracted(Calendar calendar, User user){
        if(!calendar.getUser().getId().equals(user.getId())){
            throw new CustomException(ErrorCode.FORBIDDEN_OPERATION);
        }
    }
}
