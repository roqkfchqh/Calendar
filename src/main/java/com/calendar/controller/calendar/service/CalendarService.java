package com.calendar.controller.calendar.service;

import com.calendar.controller.calendar.dto.CalendarMapper;
import com.calendar.controller.calendar.dto.CalendarRequestDto;
import com.calendar.controller.calendar.dto.CalendarResponseDto;
import com.calendar.controller.calendar.model.Calendar;
import com.calendar.controller.calendar.repository.CalendarRepository;
import com.calendar.controller.user.model.User;
import com.calendar.controller.user.service.UserValidationService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CalendarService {

    private final CalendarRepository calendarRepository;
    private final CalendarValidationService calendarValidationService;
    private final UserValidationService userValidationService;

    public CalendarResponseDto createCalendar(CalendarRequestDto dto, HttpServletRequest req){
        User user = userValidationService.validateUser(req);

        Calendar calendar = CalendarMapper.toEntity(dto, user);
        calendarRepository.save(calendar);
        return CalendarMapper.toDto(calendar);
    }

    public CalendarResponseDto readCalendar(Long id, HttpServletRequest req){
        User user = userValidationService.validateUser(req);
        Calendar calendar = calendarValidationService.validateCalendar(id);
        CalendarValidationService.authorityExtracted(calendar, user);

        return CalendarMapper.toDto(calendar);
    }



    public CalendarResponseDto updateCalendar(Long id, CalendarRequestDto dto, HttpServletRequest req){
        User user = userValidationService.validateUser(req);
        Calendar calendar = calendarValidationService.validateCalendar(id);
        CalendarValidationService.authorityExtracted(calendar, user);

        calendar.updateCalendar(dto.getTitle(), dto.getContent());
        calendarRepository.save(calendar);
        return CalendarMapper.toDto(calendar);
    }

    public void deleteCalendar(Long id, HttpServletRequest req){
        User user = userValidationService.validateUser(req);
        Calendar calendar = calendarValidationService.validateCalendar(id);
        CalendarValidationService.authorityExtracted(calendar, user);

        calendarRepository.deleteById(id);
    }


}
