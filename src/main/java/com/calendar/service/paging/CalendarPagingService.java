package com.calendar.service.paging;

import com.calendar.dto.response.CalendarResponseDto;
import com.calendar.repository.CalendarRepository;
import com.calendar.exception.CustomException;
import com.calendar.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CalendarPagingService {

    private final CalendarRepository calendarRepository;

    //calendar paging
    public Page<CalendarResponseDto> pageCalendars(int page, int size){
        if(page < 1 || size < 1){
            throw new CustomException(ErrorCode.PAGING_ERROR);
        }
        Pageable pageable = PageRequest.of(page - 1, size, Sort.by("created").descending());
        Page<CalendarResponseDto> calendars = calendarRepository.findAllWithCommentNum(pageable);
        if(calendars.isEmpty()){
            throw new CustomException(ErrorCode.NOT_FOUND);
        }
        return calendars;
    }
}
