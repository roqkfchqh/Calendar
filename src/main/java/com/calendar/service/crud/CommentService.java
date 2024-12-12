package com.calendar.service.crud;

import com.calendar.exception.CustomException;
import com.calendar.exception.ErrorCode;
import com.calendar.model.Calendar;
import com.calendar.mapper.CommentMapper;
import com.calendar.dto.request.comment.CommentRequestDto;
import com.calendar.dto.response.CommentResponseDto;
import com.calendar.model.Comment;
import com.calendar.repository.CommentRepository;
import com.calendar.model.User;
import com.calendar.service.validation.CalendarValidationService;
import com.calendar.service.validation.UserValidationService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
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

    //update
    public CommentResponseDto updateComment(Long commentId, CommentRequestDto dto, HttpServletRequest req){
        userValidationService.validateUser(req);
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new CustomException(ErrorCode.COMMENT_NOT_FOUND));

        comment.updateComment(dto.getContent());
        return CommentMapper.toDto(comment);
    }

    //delete
    public void deleteComment(Long commentId, HttpServletRequest req){
        userValidationService.validateUser(req);
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new CustomException(ErrorCode.COMMENT_NOT_FOUND));
        commentRepository.delete(comment);
    }
}
