package com.techies.dtlr.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.techies.dtlr.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
   User findByEmail(String email);
}

