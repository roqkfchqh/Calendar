package com.calendar.calendar.controller;

import com.calendar.calendar.dto.CalendarRequestDto;
import com.calendar.calendar.dto.CalendarResponseDto;
import com.calendar.calendar.service.CalendarService;
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

    @PostMapping
    public ResponseEntity<CalendarResponseDto> createCalendar(
            @Valid @RequestBody CalendarRequestDto dto,
            HttpServletRequest req){
        return ResponseEntity.ok(calendarService.createCalendar(dto, req));
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
        return ResponseEntity.ok(calendarService.updateCalendar(calendarId, dto, req));
    }

    @DeleteMapping("/{calendarId}")
    public ResponseEntity<String> deleteCalendar(
            @PathVariable Long calendarId,
            HttpServletRequest req){
        calendarService.deleteCalendar(calendarId, req);
        return ResponseEntity.ok("일정이 성공적으로 삭제되었습니다.");
    }

    @GetMapping
    public ResponseEntity<Page<CalendarResponseDto>> readCalendars(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = PAGE_SIZE) int size){
        Page<CalendarResponseDto> calendars = calendarService.pageCalendars(page, size);
        return ResponseEntity.ok(calendars);
    }
}
