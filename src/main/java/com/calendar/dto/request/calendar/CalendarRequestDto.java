package com.calendar.dto.request.calendar;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CalendarRequestDto {

    @Size(min = 2, max = 20, message = "제목은 2자 이상 20자 이하로만 입력 가능합니다.")
    private String title;

    @Size(min = 10, max = 300, message = "내용은 10자 이상 300자 이하로만 입력 가능합니다.")
    private String content;
}
