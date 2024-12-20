package com.calendar.service.paging;

import com.calendar.model.Calendar;
import com.calendar.dto.response.CommentResponseDto;
import com.calendar.repository.CommentRepository;
import com.calendar.exception.CustomException;
import com.calendar.exception.ErrorCode;
import com.calendar.service.validation.CalendarValidationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentPagingService {

    private final CommentRepository commentRepository;
    private final CalendarValidationService calendarValidationService;

    //comment paging
    public Page<CommentResponseDto> readCommentsCalender(Long calendarId, int page, int size){
        if(page < 1 || size < 1){
            throw new CustomException(ErrorCode.PAGING_ERROR);
        }
        Pageable pageable = PageRequest.of(page - 1, size, Sort.by("created").descending());
        Calendar calendar = calendarValidationService.validateCalendar(calendarId);

        Page<CommentResponseDto> comments = commentRepository.findCommentDtoByCalendar(calendar, pageable);
        if(comments.isEmpty()){
            throw new CustomException(ErrorCode.NOT_FOUND);
        }
        return comments;
    }
}
