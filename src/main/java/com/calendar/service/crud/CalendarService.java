package com.calendar.service.crud;

import com.calendar.mapper.CalendarMapper;
import com.calendar.dto.request.calendar.CalendarRequestDto;
import com.calendar.dto.response.CalendarResponseDto;
import com.calendar.model.Calendar;
import com.calendar.repository.CalendarRepository;
import com.calendar.repository.CommentRepository;
import com.calendar.model.User;
import com.calendar.service.validation.CalendarValidationService;
import com.calendar.service.validation.UserValidationService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class CalendarService {

    private final CalendarRepository calendarRepository;
    private final CommentRepository commentRepository;
    private final CalendarValidationService calendarValidationService;
    private final UserValidationService userValidationService;


    //create
    public CalendarResponseDto createCalendar(CalendarRequestDto dto, HttpServletRequest req){
        User user = userValidationService.validateUser(req);

        Calendar calendar = CalendarMapper.toEntity(dto, user);
        calendarRepository.save(calendar);
        Long commentNum = commentRepository.countByCalendar(calendar);
        return CalendarMapper.toDto(calendar, commentNum);
    }

    //read
    @Transactional(readOnly = true)
    public CalendarResponseDto readCalendar(Long id){
        Calendar calendar = calendarValidationService.validateCalendar(id);
        Long commentNum = commentRepository.countByCalendar(calendar);
        return CalendarMapper.toDto(calendar,commentNum);
    }

    //update
    public CalendarResponseDto updateCalendar(Long id, CalendarRequestDto dto, HttpServletRequest req){
        User user = userValidationService.validateUser(req);
        Calendar calendar = calendarValidationService.validateCalendar(id);
        calendarValidationService.authorityExtracted(calendar, user);

        calendar.updateCalendar(dto.getTitle(), dto.getContent());
        Long commentNum = commentRepository.countByCalendar(calendar);
        calendarRepository.save(calendar);
        return CalendarMapper.toDto(calendar,commentNum);
    }

    //delete
    public void deleteCalendar(Long id, HttpServletRequest req){
        User user = userValidationService.validateUser(req);
        Calendar calendar = calendarValidationService.validateCalendar(id);
        calendarValidationService.authorityExtracted(calendar, user);

        commentRepository.deleteByCalendarId(id);
        calendarRepository.deleteById(id);
    }
}
