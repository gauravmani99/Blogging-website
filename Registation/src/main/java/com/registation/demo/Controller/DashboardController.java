package com.registation.demo.Controller;
import java.io.IOException;
import java.util.Base64;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.registation.demo.entity.Post;
import com.registation.demo.entity.UserDtls;
import com.registation.demo.service.PostService;

import jakarta.servlet.http.HttpSession;

@Controller
public class DashboardController {
    @GetMapping("/dashboard")
    public String dashboardRedirect() {
        return "redirect:/posted";
    }
    @Autowired
    private PostService postService;

    @GetMapping("/post")
    public String showPostForm(Model model) {
        org.springframework.security.core.Authentication authentication = org.springframework.security.core.context.SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        if ("admin@gaurav.com".equals(email)) {
            return "redirect:/admin";
        }
        UserDtls user = postService.getUserByEmail(email);
        String userName = (user != null) ? user.getName() : "";
        model.addAttribute("userName", userName);
        return "post";
    }

    @PostMapping("/post")
    public String postImage(@RequestParam("text") String text,
                           @RequestParam("imageFile") MultipartFile imageFile) {
        org.springframework.security.core.Authentication authentication = org.springframework.security.core.context.SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        UserDtls user = postService.getUserByEmail(email);
        if (user == null) {
            return "redirect:/login";
        }
        Post post = new Post();
        post.setText(text);
        post.setUser(user);
        try {
            post.setImage(imageFile.getBytes());
        } catch (IOException e) {
            post.setImage(null);
        }
        postService.savePost(post);
        return "redirect:/posted";
    }

    @GetMapping("/posted")
    public String showPosted(Model model) {
        org.springframework.security.core.Authentication authentication = org.springframework.security.core.context.SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        if ("admin@gaurav.com".equals(email)) {
            return "redirect:/admin";
        }
        UserDtls user = postService.getUserByEmail(email);
        if (user == null) {
            return "redirect:/login";
        }
        List<Post> posts = postService.getPostsByUser(user);
        for (Post post : posts) {
            if (post.getImage() != null && post.getImage().length > 0) {
                String base64 = Base64.getEncoder().encodeToString(post.getImage());
                post.setBase64Image(base64);
            } else {
                post.setBase64Image("");
            }
        }
        model.addAttribute("posts", posts);
        model.addAttribute("userName", user.getName());
        return "posted";
    }

    @GetMapping("/post/delete/{id}")
    public String deletePost(@PathVariable("id") Long postId) {
        org.springframework.security.core.Authentication authentication = org.springframework.security.core.context.SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        UserDtls user = postService.getUserByEmail(email);
        if (user == null) {
            return "redirect:/login";
        }
        postService.deletePost(postId);
        return "redirect:/posted";
    }

    @GetMapping("/post/edit/{id}")
    public String editPostForm(@PathVariable("id") Long postId, Model model) {
        Post post = postService.getPostById(postId);
        if (post == null) {
            return "redirect:/posted";
        }
        org.springframework.security.core.Authentication authentication = org.springframework.security.core.context.SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        UserDtls user = postService.getUserByEmail(email);
        String userName = (user != null) ? user.getName() : "";
        model.addAttribute("userName", userName);
        model.addAttribute("post", post);
        return "post";
    }

    @PostMapping("/post/update")
    public String updatePost(@RequestParam("postId") Long postId,
                            @RequestParam("text") String text,
                            @RequestParam(value = "imageFile", required = false) MultipartFile imageFile) {
        org.springframework.security.core.Authentication authentication = org.springframework.security.core.context.SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        UserDtls user = postService.getUserByEmail(email);
        if (user == null) {
            return "redirect:/login";
        }
        postService.updatePostText(postId, text);
        if (imageFile != null && !imageFile.isEmpty()) {
            try {
                postService.updatePostImage(postId, imageFile.getBytes());
            } catch (IOException e) {
                // ignore image update if error
            }
        }
        return "redirect:/posted";
    }
    // ...existing code...

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }
}
