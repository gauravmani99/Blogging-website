package com.registation.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.registation.demo.entity.Post;
import com.registation.demo.entity.UserDtls;
import com.registation.demo.reposistry.PostRepository;

@Service
public class PostService {
    // Removed duplicate postRepository and getPostsByUser
    public int getPostCountByUser(UserDtls user) {
        return postRepository.findByUser(user).size();
    }
    public Post getPostById(Long postId) {
        return postRepository.findById(postId).orElse(null);
    }

    public void updatePostImage(Long postId, byte[] imageBytes) {
        Post post = postRepository.findById(postId).orElse(null);
        if (post != null) {
            post.setImage(imageBytes);
            postRepository.save(post);
        }
    }
    @Autowired
    private com.registation.demo.reposistry.UserRepository userRepository;
    public UserDtls getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }
    @Autowired
    private PostRepository postRepository;

    public Post savePost(Post post) {
        return postRepository.save(post);
    }

    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    public List<Post> getPostsByUser(UserDtls user) {
        return postRepository.findByUser(user);
    }

    public void deletePost(Long postId) {
        postRepository.deleteById(postId);
    }

    public void updatePostText(Long postId, String newText) {
        Post post = postRepository.findById(postId).orElse(null);
        if (post != null) {
            post.setText(newText);
            postRepository.save(post);
        }
    }
}
