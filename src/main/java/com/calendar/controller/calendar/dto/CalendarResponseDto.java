package com.calendar.controller.calendar.dto;

import com.calendar.controller.calendar.model.Calendar;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor
public class CalendarResponseDto {

    private Long id;
    private String title;
    private String content;
    private String name;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private String created;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private String updated;

    public CalendarResponseDto(Calendar calendar){
        this.id = calendar.getId();
        this.title = calendar.getTitle();
        this.content = calendar.getContent();
        this.created = calendar.getCreated().toString();
        this.updated = calendar.getUpdated().toString();
        this.name = calendar.getUser().getName();
    }
}
