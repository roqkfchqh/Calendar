package com.calendar.controller.calendar.service;

import com.calendar.common.exception.BadInputException;
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
                .orElseThrow(() -> new BadInputException("해당 일정을 찾을 수 없습니다."));
    }

    public static void authorityExtracted(Calendar calendar, User user){
        if(!calendar.getUser().getId().equals(user.getId())){
            throw new BadInputException("권한이 없습니다.");
        }
    }
}
