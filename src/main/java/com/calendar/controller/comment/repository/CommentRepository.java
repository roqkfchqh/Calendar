package com.calendar.controller.comment.repository;

import com.calendar.controller.calendar.model.Calendar;
import com.calendar.controller.comment.model.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    Page<Comment> findByCalendar(Calendar calendar, Pageable pageable);

    Integer countByCalendar(Calendar calendar);
}
