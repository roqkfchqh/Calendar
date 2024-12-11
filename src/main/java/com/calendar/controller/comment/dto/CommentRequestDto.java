package com.calendar.controller.comment.dto;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CommentRequestDto {

    @Size(min = 10, max = 70, message = "내용은 10자 이상 70자 이하로만 입력 가능합니다.")
    private String content;
}
