package com.ahmadarif.jwt.repository;

import com.ahmadarif.jwt.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by ARIF on 13-Apr-17.
 */
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username );
}