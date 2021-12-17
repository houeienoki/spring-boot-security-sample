package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.domain.user.model.User;

public interface UserRepository extends JpaRepository<User, String> {
}
