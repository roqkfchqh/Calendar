package com.calendar.comment.repository;

import com.calendar.calendar.model.Calendar;
import com.calendar.comment.dto.CommentResponseDto;
import com.calendar.comment.model.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    @Query("SELECT new com.calendar.comment.dto.CommentResponseDto(" +
            "c.id, c.content, c.user.name, c.created, c.updated) " +
            "FROM Comment c WHERE c.calendar = :calendar")
    Page<CommentResponseDto> findCommentDtoByCalendar(@Param("calendar") Calendar calendar, Pageable pageable);

    Long countByCalendar(Calendar calendar);
}
