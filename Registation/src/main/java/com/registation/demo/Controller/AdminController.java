
package com.registation.demo.Controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.registation.demo.reposistry.UserRepository;

import jakarta.servlet.http.HttpSession;

@Controller
public class AdminController {
    @GetMapping("/admin-logout")
    public String adminLogout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private com.registation.demo.service.PostService postService;

    @GetMapping("/admin")
    public String adminDashboard(Model model) {
        java.util.List<com.registation.demo.entity.UserDtls> users = userRepository.findAll();
        java.util.Map<Long, Integer> postCounts = new java.util.HashMap<>();
        for (com.registation.demo.entity.UserDtls user : users) {
            postCounts.put(user.getId(), postService.getPostCountByUser(user));
        }
        model.addAttribute("users", users);
        model.addAttribute("postCounts", postCounts);
        return "admin-dashboard";
    }
}
