package com.calendar.controller.calendar.service;

import com.calendar.controller.calendar.dto.CalendarMapper;
import com.calendar.controller.calendar.dto.CalendarRequestDto;
import com.calendar.controller.calendar.dto.CalendarResponseDto;
import com.calendar.controller.calendar.model.Calendar;
import com.calendar.controller.calendar.repository.CalendarRepository;
import com.calendar.controller.comment.model.Comment;
import com.calendar.controller.comment.repository.CommentRepository;
import com.calendar.controller.user.model.User;
import com.calendar.controller.user.service.UserValidationService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CalendarService {

    private final CalendarRepository calendarRepository;
    private final CalendarValidationService calendarValidationService;
    private final UserValidationService userValidationService;
    private final CommentRepository commentRepository;

    //create
    public CalendarResponseDto createCalendar(CalendarRequestDto dto, HttpServletRequest req){
        User user = userValidationService.validateUser(req);

        Calendar calendar = CalendarMapper.toEntity(dto, user);
        calendarRepository.save(calendar);

        Integer commentsNum = commentRepository.countByCalendar(calendar);
        return CalendarMapper.toDto(calendar, commentsNum);
    }

    //read
    public CalendarResponseDto readCalendar(Long id){
        Calendar calendar = calendarValidationService.validateCalendar(id);
        Integer commentsNum = commentRepository.countByCalendar(calendar);

        return CalendarMapper.toDto(calendar, commentsNum);
    }

    //update
    public CalendarResponseDto updateCalendar(Long id, CalendarRequestDto dto, HttpServletRequest req){
        User user = userValidationService.validateUser(req);
        Calendar calendar = calendarValidationService.validateCalendar(id);
        CalendarValidationService.authorityExtracted(calendar, user);

        calendar.updateCalendar(dto.getTitle(), dto.getContent());
        calendarRepository.save(calendar);
        Integer commentsNum = commentRepository.countByCalendar(calendar);
        return CalendarMapper.toDto(calendar, commentsNum);
    }

    //delete
    public void deleteCalendar(Long id, HttpServletRequest req){
        User user = userValidationService.validateUser(req);
        Calendar calendar = calendarValidationService.validateCalendar(id);
        CalendarValidationService.authorityExtracted(calendar, user);

        calendarRepository.deleteById(id);
    }

    //findAll(paging)
    public Page<CalendarResponseDto> pageCalendars(int page, int size){
        Pageable pageable = PageRequest.of(page, size, Sort.by("created").descending());
        Page<Calendar> calendars = calendarRepository.findAll(pageable);
        Integer commentsNum = commentRepository.countByCalendar((Calendar) calendars);
        return calendars.map(calendar -> CalendarMapper.toDto(calendar, commentsNum));
    }


}
