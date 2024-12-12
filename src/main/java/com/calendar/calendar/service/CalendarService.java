package com.calendar.calendar.service;

import com.calendar.calendar.dto.CalendarMapper;
import com.calendar.calendar.dto.CalendarRequestDto;
import com.calendar.calendar.dto.CalendarResponseDto;
import com.calendar.calendar.model.Calendar;
import com.calendar.calendar.repository.CalendarRepository;
import com.calendar.comment.repository.CommentRepository;
import com.calendar.user.model.User;
import com.calendar.user.service.UserValidationService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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

    //findAll(paging)
    @Transactional(readOnly = true)
    public Page<CalendarResponseDto> pageCalendars(int page, int size){
        Pageable pageable = PageRequest.of(page, size, Sort.by("created").descending());
        return calendarRepository.findAllWithCommentNum(pageable);
    }
}
