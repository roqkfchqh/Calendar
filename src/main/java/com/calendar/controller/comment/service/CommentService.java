package com.calendar.controller.comment.service;

import com.calendar.common.exception.CustomException;
import com.calendar.common.exception.ErrorCode;
import com.calendar.controller.calendar.model.Calendar;
import com.calendar.controller.calendar.service.CalendarValidationService;
import com.calendar.controller.comment.dto.CommentMapper;
import com.calendar.controller.comment.dto.CommentRequestDto;
import com.calendar.controller.comment.dto.CommentResponseDto;
import com.calendar.controller.comment.model.Comment;
import com.calendar.controller.comment.repository.CommentRepository;
import com.calendar.controller.user.model.User;
import com.calendar.controller.user.service.UserValidationService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final UserValidationService userValidationService;
    private final CalendarValidationService calendarValidationService;

    //create
    public CommentResponseDto createComment(Long calendarId, CommentRequestDto dto, HttpServletRequest req){
        User user = userValidationService.validateUser(req);
        Calendar calendar = calendarValidationService.validateCalendar(calendarId);

        Comment comment = CommentMapper.toEntity(dto, user, calendar);
        commentRepository.save(comment);
        return CommentMapper.toDto(comment);
    }

    //read

    //update
    public CommentResponseDto updateComment(Long commentId, CommentRequestDto dto, HttpServletRequest req){
        User user = userValidationService.validateUser(req);
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new CustomException(ErrorCode.COMMENT_NOT_FOUND));

        Comment updateComment = comment.updateComment(dto.getContent());
        commentRepository.save(updateComment);
        return CommentMapper.toDto(updateComment);
    }

    //delete
    public void deleteComment(Long commentId, HttpServletRequest req){
        userValidationService.validateUser(req);
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new CustomException(ErrorCode.COMMENT_NOT_FOUND));
        commentRepository.delete(comment);
    }
}
