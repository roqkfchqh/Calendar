package com.calendar.repository;

import com.calendar.dto.response.CalendarResponseDto;
import com.calendar.model.Calendar;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CalendarRepository extends JpaRepository<Calendar, Long> {

    //user 삭제 시 calendar 함께 삭제
    void deleteByUserId(Long userId);

    //calendar paging + comment num 과 함께
    @Query("SELECT new com.calendar.dto.response.CalendarResponseDto(" +
            "c.id, c.title, c.content, c.user.name, COUNT(com), c.created, c.updated) " +
            "FROM Calendar c LEFT JOIN Comment com ON com.calendar = c " +
            "GROUP BY c.id, c.title, c.content, c.user.name, c.created, c.updated " +
            "ORDER BY c.updated DESC")
    Page<CalendarResponseDto> findAllWithCommentNum(Pageable pageable);
}
