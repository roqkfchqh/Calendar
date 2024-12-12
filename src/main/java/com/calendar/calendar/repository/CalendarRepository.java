package com.calendar.calendar.repository;

import com.calendar.calendar.dto.CalendarResponseDto;
import com.calendar.calendar.model.Calendar;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CalendarRepository extends JpaRepository<Calendar, Long> {

    @Modifying
    @Query("DELETE FROM Calendar c WHERE c.user.id = :userId")
    void deleteByUserId(@Param("userId") Long userId);

    @Query("SELECT new com.calendar.calendar.dto.CalendarResponseDto(" +
            "c.id, c.title, c.content, c.user.name, COUNT(com), c.created, c.updated) " +
            "FROM Calendar c LEFT JOIN Comment com ON com.calendar = c " +
            "GROUP BY c.id, c.title, c.content, c.user.name, c.created, c.updated " +
            "ORDER BY c.updated DESC")
    Page<CalendarResponseDto> findAllWithCommentNum(Pageable pageable);
}
