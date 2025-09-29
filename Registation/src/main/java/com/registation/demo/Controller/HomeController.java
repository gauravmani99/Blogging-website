package com.registation.demo.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    @GetMapping("/")
    public String home(org.springframework.ui.Model model, jakarta.servlet.http.HttpServletRequest request) {
        // Invalidate session to log out any user or admin
        jakarta.servlet.http.HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        java.util.List<com.registation.demo.entity.Post> posts = new java.util.ArrayList<>();
        for (com.registation.demo.entity.Post post : postService.getAllPosts()) {
            if (post.getImage() != null && post.getImage().length > 0) {
                String base64 = java.util.Base64.getEncoder().encodeToString(post.getImage());
                post.setBase64Image(base64);
            } else {
                post.setBase64Image("");
            }
            posts.add(post);
        }
        posts.sort((a, b) -> Long.compare(b.getId(), a.getId())); // newest first
        java.util.List<com.registation.demo.entity.Post> recentPosts = posts.size() > 3 ? posts.subList(0, 3) : posts;
        model.addAttribute("recentPosts", recentPosts);
        return "index";
    }

    @org.springframework.beans.factory.annotation.Autowired
    private com.registation.demo.service.PostService postService;
}
