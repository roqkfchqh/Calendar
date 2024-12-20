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
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final UserValidationService userValidationService;
    private final CalendarValidationService calendarValidationService;

    //create
    @Transactional
    public CommentResponseDto createComment(Long calendarId, CommentRequestDto dto, Long userId){
        User user = userValidationService.validateUser(userId);
        Calendar calendar = calendarValidationService.validateCalendar(calendarId);

        Comment comment = CommentMapper.toEntity(dto, user, calendar);
        commentRepository.save(comment);
        return CommentMapper.toDto(comment);
    }

    //update
    @Transactional
    public CommentResponseDto updateComment(Long commentId, CommentRequestDto dto, Long userId){
        userValidationService.validateUser(userId);
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new CustomException(ErrorCode.COMMENT_NOT_FOUND));

        comment.updateComment(dto.getContent());
        return CommentMapper.toDto(comment);
    }

    //delete
    public void deleteComment(Long commentId, Long userId){
        userValidationService.validateUser(userId);
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new CustomException(ErrorCode.COMMENT_NOT_FOUND));
        commentRepository.delete(comment);
    }
}
