package com.registation.demo.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.registation.demo.entity.Friendship;
import com.registation.demo.entity.UserDtls;
import com.registation.demo.reposistry.UserRepository;
import com.registation.demo.service.FriendshipService;

@Controller
public class FriendshipController {
    @Autowired
    private com.registation.demo.service.PostService postService;
    public String viewFriendPosts(@PathVariable Long id, Model model) {
        UserDtls currentUser = getCurrentUser();
        UserDtls friend = userRepository.findById(id).orElse(null);
        if (friend != null && friendshipService.areFriends(currentUser, friend)) {
            List<com.registation.demo.entity.Post> posts = postService.getPostsByUser(friend);
            model.addAttribute("posts", posts);
            model.addAttribute("friend", friend);
            return "friend_posts";
        }
        return "redirect:/friends";
    }
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private FriendshipService friendshipService;

    private UserDtls getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return userRepository.findByEmail(auth.getName());
    }

    @GetMapping("/users")
    public String showAllUsers(Model model) {
        UserDtls currentUser = getCurrentUser();
        List<UserDtls> users = userRepository.findAll();
        model.addAttribute("users", users);
        model.addAttribute("currentUser", currentUser);
        return "users";
    }

    @PostMapping("/friend/request/{id}")
    public String sendFriendRequest(@PathVariable Long id) {
        UserDtls currentUser = getCurrentUser();
        UserDtls toUser = userRepository.findById(id).orElse(null);
        if (toUser != null && !currentUser.getId().equals(toUser.getId())) {
            friendshipService.sendFriendRequest(currentUser, toUser);
        }
        return "redirect:/users";
    }

    @GetMapping("/friend/requests")
    public String viewFriendRequests(Model model) {
        UserDtls currentUser = getCurrentUser();
        List<Friendship> requests = friendshipService.getFriendRequests(currentUser);
        model.addAttribute("requests", requests);
        return "friend_requests";
    }

    @PostMapping("/friend/accept/{requestId}")
    public String acceptFriendRequest(@PathVariable Long requestId) {
        Friendship request = friendshipService.findById(requestId);
        if (request != null) {
            friendshipService.acceptFriendRequest(request);
        }
        return "redirect:/friend/requests";
    }

    @GetMapping("/friends")
    public String viewFriends(Model model) {
        UserDtls currentUser = getCurrentUser();
        List<Friendship> friends = friendshipService.getFriends(currentUser);
        model.addAttribute("friends", friends);
        return "friends";
    }
}
