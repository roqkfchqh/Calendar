package com.calendar.controller.calendar.dto;

import com.calendar.controller.calendar.model.Calendar;
import com.calendar.controller.comment.model.Comment;
import com.calendar.controller.user.model.User;

import java.util.List;

public class CalendarMapper {

    //entity -> dto
    public static CalendarResponseDto toDto(Calendar calendar, Integer commentsNum){
        return CalendarResponseDto.builder()
                .id(calendar.getId())
                .title(calendar.getTitle())
                .content(calendar.getContent())
                .name(calendar.getUser().getName())
                .commentsNum(commentsNum)
                .created(calendar.getCreated().toString())
                .updated(calendar.getUpdated().toString())
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
