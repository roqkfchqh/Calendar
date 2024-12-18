package com.calendar.controller.calendar;

import com.calendar.dto.request.calendar.CalendarRequestDto;
import com.calendar.dto.response.CalendarResponseDto;
import com.calendar.service.paging.CalendarPagingService;
import com.calendar.service.crud.CalendarService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/calendars")
@RequiredArgsConstructor
public class CalendarController {

    private static final String PAGE_SIZE = "10";

    private final CalendarService calendarService;
    private final CalendarPagingService calendarPagingService;

    @PostMapping
    public ResponseEntity<CalendarResponseDto> createCalendar(
            @Valid @RequestBody CalendarRequestDto dto,
            HttpServletRequest req){
        Long userId = (Long) req.getSession().getAttribute("userId");
        return ResponseEntity.ok(calendarService.createCalendar(dto, userId));
    }

    @GetMapping("/{calendarId}")
    public ResponseEntity<CalendarResponseDto> readCalendar(
            @PathVariable Long calendarId){
        return ResponseEntity.ok(calendarService.readCalendar(calendarId));
    }

    @PatchMapping("/{calendarId}")
    public ResponseEntity<CalendarResponseDto> updateCalendar(
            @PathVariable Long calendarId,
            @Valid @RequestBody CalendarRequestDto dto,
            HttpServletRequest req){
        Long userId = (Long) req.getSession().getAttribute("userId");
        return ResponseEntity.ok(calendarService.updateCalendar(calendarId, dto, userId));
    }

    @DeleteMapping("/{calendarId}")
    public ResponseEntity<String> deleteCalendar(
            @PathVariable Long calendarId,
            HttpServletRequest req){
        Long userId = (Long) req.getSession().getAttribute("userId");
        calendarService.deleteCalendar(calendarId, userId);
        return ResponseEntity.ok("일정이 성공적으로 삭제되었습니다.");
    }

    @GetMapping
    public ResponseEntity<Page<CalendarResponseDto>> readCalendars(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = PAGE_SIZE) int size){
        Page<CalendarResponseDto> calendars = calendarPagingService.pageCalendars(page, size);
        return ResponseEntity.ok(calendars);
    }
}
