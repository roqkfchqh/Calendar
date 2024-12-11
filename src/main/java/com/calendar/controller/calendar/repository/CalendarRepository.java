package com.calendar.controller.calendar.repository;

import com.calendar.controller.calendar.model.Calendar;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CalendarRepository extends JpaRepository<Calendar, Long> {

    @EntityGraph(attributePaths = {"user"})
    @Query("SELECT c FROM Calendar c WHERE c.id = :id")
    Optional<Calendar> findByIdWithUser(@Param("id") Long id);

    @Modifying
    @Query("DELETE FROM Calendar c WHERE c.user.id = :userId")
    void deleteByUserId(@Param("userId") Long userId);
}
