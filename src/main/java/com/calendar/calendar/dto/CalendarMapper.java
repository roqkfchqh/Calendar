package com.calendar.calendar.dto;

import com.calendar.calendar.model.Calendar;
import com.calendar.user.model.User;

public class CalendarMapper {

    //entity -> dto
    public static CalendarResponseDto toDto(Calendar calendar, Long commentNum){
        return CalendarResponseDto.builder()
                .id(calendar.getId())
                .title(calendar.getTitle())
                .content(calendar.getContent())
                .name(calendar.getUser().getName())
                .commentNum(commentNum)
                .created(calendar.getCreated())
                .updated(calendar.getUpdated())
                .build();
    }

    //dto -> entity
    public static Calendar toEntity(CalendarRequestDto dto, User user){
        return Calendar.builder()
                .title(dto.getTitle())
                .content(dto.getContent())
                .user(user)
                .build();
    }
}
