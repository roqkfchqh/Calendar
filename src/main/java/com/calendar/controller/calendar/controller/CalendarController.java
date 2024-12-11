package com.calendar.controller.calendar.controller;

import com.calendar.controller.calendar.dto.CalendarRequestDto;
import com.calendar.controller.calendar.dto.CalendarResponseDto;
import com.calendar.controller.calendar.service.CalendarService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/calendars")
@RequiredArgsConstructor
public class CalendarController {

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
    public ResponseEntity<CalendarResponseDto> deleteCalendar(
            @PathVariable Long calendarId,
            HttpServletRequest req){
        calendarService.deleteCalendar(calendarId, req);
        return ResponseEntity.noContent().build();
    }

}
