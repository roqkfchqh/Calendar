package com.calendar.repository;

import com.calendar.model.Calendar;
import com.calendar.dto.response.CommentResponseDto;
import com.calendar.model.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    @Query("SELECT new com.calendar.dto.response.CommentResponseDto(" +
            "c.id, c.content, c.user.name, c.created, c.updated) " +
            "FROM Comment c WHERE c.calendar = :calendar")
    Page<CommentResponseDto> findCommentDtoByCalendar(@Param("calendar") Calendar calendar, Pageable pageable);

    Long countByCalendar(Calendar calendar);

    @Modifying
    @Query("DELETE FROM Comment c WHERE c.calendar.id = :calendarId")
    void deleteByCalendarId(@Param("calendarId") long calendarId);
}
