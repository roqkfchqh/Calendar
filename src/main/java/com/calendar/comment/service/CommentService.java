package com.calendar.comment.service;

import com.calendar.common.exception.CustomException;
import com.calendar.common.exception.ErrorCode;
import com.calendar.calendar.model.Calendar;
import com.calendar.calendar.service.CalendarValidationService;
import com.calendar.comment.dto.CommentMapper;
import com.calendar.comment.dto.CommentRequestDto;
import com.calendar.comment.dto.CommentResponseDto;
import com.calendar.comment.model.Comment;
import com.calendar.comment.repository.CommentRepository;
import com.calendar.user.model.User;
import com.calendar.user.service.UserValidationService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
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
    @Transactional(readOnly = true)
    public Page<CommentResponseDto> readCommentsCalender(Long calendarId, int page, int size){
        Pageable pageable = PageRequest.of(page, size, Sort.by("created").descending());
        Calendar calendar = calendarValidationService.validateCalendar(calendarId);

        return commentRepository.findCommentDtoByCalendar(calendar, pageable);
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
