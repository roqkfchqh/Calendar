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
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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

    //read
    public Page<CommentResponseDto> readCommentsCalender(Long calendarId, int page, int size){
        Pageable pageable = PageRequest.of(page, size, Sort.by("created").descending());
        Calendar calendar = calendarValidationService.validateCalendar(calendarId);

        Page<Comment> commentPage = commentRepository.findByCalendar(calendar, pageable);
        return commentPage.map(CommentMapper::toDto);
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
