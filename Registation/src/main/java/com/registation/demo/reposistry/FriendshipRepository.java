package com.registation.demo.reposistry;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.registation.demo.entity.Friendship;
import com.registation.demo.entity.UserDtls;

public interface FriendshipRepository extends JpaRepository<Friendship, Long> {
    List<Friendship> findByUser(UserDtls user);
    List<Friendship> findByFriend(UserDtls friend);
    List<Friendship> findByUserAndStatus(UserDtls user, String status);
    List<Friendship> findByFriendAndStatus(UserDtls friend, String status);
    Friendship findByUserAndFriend(UserDtls user, UserDtls friend);
}
