package com.registation.demo.reposistry;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.registation.demo.entity.Post;
import com.registation.demo.entity.UserDtls;

public interface PostRepository extends JpaRepository<Post, Long> {
	List<Post> findByUser(UserDtls user);
}
