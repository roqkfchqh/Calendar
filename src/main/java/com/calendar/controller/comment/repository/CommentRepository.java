package com.calendar.controller.comment.repository;

import com.calendar.controller.calendar.model.Calendar;
import com.calendar.controller.comment.model.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    Page<Comment> findByCalendar(Calendar calendar, Pageable pageable);

    @Query("SELECT c FROM Comment c WHERE c.calendar.id = :id")
    List<Comment> findByCalendarId(@Param("id") Long id);

    Integer countByCalendar(Calendar calendar);
}
