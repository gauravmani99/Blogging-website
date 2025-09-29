package com.registation.demo.reposistry;

import org.springframework.data.jpa.repository.JpaRepository;

import com.registation.demo.entity.UserDtls;

public interface UserRepository extends JpaRepository<UserDtls, Long> {
	UserDtls findByEmail(String email);
}
