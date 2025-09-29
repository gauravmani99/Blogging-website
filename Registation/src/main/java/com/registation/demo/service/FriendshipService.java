package com.registation.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.registation.demo.entity.Friendship;
import com.registation.demo.entity.UserDtls;
import com.registation.demo.reposistry.FriendshipRepository;

@Service
public class FriendshipService {
    public Friendship findById(Long id) {
        return friendshipRepository.findById(id).orElse(null);
    }
    @Autowired
    private FriendshipRepository friendshipRepository;

    public Friendship sendFriendRequest(UserDtls from, UserDtls to) {
        Friendship existing = friendshipRepository.findByUserAndFriend(from, to);
        if (existing == null) {
            Friendship request = new Friendship();
            request.setUser(from);
            request.setFriend(to);
            request.setStatus("pending");
            return friendshipRepository.save(request);
        }
        return existing;
    }

    public void acceptFriendRequest(Friendship request) {
        request.setStatus("accepted");
        friendshipRepository.save(request);
    }

    public List<Friendship> getFriends(UserDtls user) {
        return friendshipRepository.findByUserAndStatus(user, "accepted");
    }

    public List<Friendship> getFriendRequests(UserDtls user) {
        return friendshipRepository.findByFriendAndStatus(user, "pending");
    }

    public boolean areFriends(UserDtls user1, UserDtls user2) {
        Friendship f = friendshipRepository.findByUserAndFriend(user1, user2);
        return f != null && "accepted".equals(f.getStatus());
    }
}
