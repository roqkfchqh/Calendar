package com.calendar.controller.comment.dto;

import com.calendar.controller.calendar.model.Calendar;
import com.calendar.controller.comment.model.Comment;
import com.calendar.controller.user.model.User;

public class CommentMapper {

    //entity -> dto
    public static CommentResponseDto toDto(Comment comment){
        return CommentResponseDto.builder()
                .id(comment.getId())
                .content(comment.getContent())
                .name(comment.getUser().getName())
                .created(comment.getCreated().toString())
                .updated(comment.getUpdated().toString())
                .build();
    }

    //dto -> entity
    public static Comment toEntity(CommentResponseDto dto, User user, Calendar calendar){
        return Comment.builder()
                .content(dto.getContent())
                .user(user)
                .calendar(calendar)
                .build();
    }
}
