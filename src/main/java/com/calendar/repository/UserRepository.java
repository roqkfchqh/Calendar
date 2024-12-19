package com.calendar.repository;

import com.calendar.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{

    //email 로 id 찾기
    Long findIdByEmail(String email);

    //email 로 user 찾기
    Optional<User> findByEmail(String email);

    //id 로 password 찾기
    @Query("SELECT u.password FROM User u WHERE u.id = :id")
    String findPasswordById(Long id);
}
