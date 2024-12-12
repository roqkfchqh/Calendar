package com.calendar.mapper;

import com.calendar.dto.request.comment.CommentRequestDto;
import com.calendar.dto.response.CommentResponseDto;
import com.calendar.model.Calendar;
import com.calendar.model.Comment;
import com.calendar.model.User;

public class CommentMapper {

    //entity -> dto
    public static CommentResponseDto toDto(Comment comment){
        return CommentResponseDto.builder()
                .id(comment.getId())
                .content(comment.getContent())
                .name(comment.getUser().getName())
                .created(comment.getCreated())
                .updated(comment.getUpdated())
                .build();
    }

    //dto -> entity
    public static Comment toEntity(CommentRequestDto dto, User user, Calendar calendar){
        return Comment.builder()
                .content(dto.getContent())
                .user(user)
                .calendar(calendar)
                .build();
    }
}
