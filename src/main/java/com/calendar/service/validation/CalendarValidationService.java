package com.calendar.service.validation;

import com.calendar.common.exception.CustomException;
import com.calendar.common.exception.ErrorCode;
import com.calendar.model.Calendar;
import com.calendar.repository.CalendarRepository;
import com.calendar.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CalendarValidationService {

    private final CalendarRepository calendarRepository;

    @Transactional(readOnly = true)
    public Calendar validateCalendar(Long id) {
        return calendarRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.CONTENT_NOT_FOUND));
    }

    @Transactional(readOnly = true)
    public void authorityExtracted(Calendar calendar, User user){
        if(!calendar.getUser().getId().equals(user.getId())){
            throw new CustomException(ErrorCode.FORBIDDEN_OPERATION);
        }
    }
}
