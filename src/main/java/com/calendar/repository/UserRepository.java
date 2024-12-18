package com.calendar.repository;

import com.calendar.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{

    Long findIdByEmail(String email);

    Optional<User> findByEmail(String email);

    @Query("SELECT u.password FROM User u WHERE u.id = :id")
    String findPasswordById(Long id);
}
