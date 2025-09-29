package com.registation.demo.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.registation.demo.entity.UserDtls;
import com.registation.demo.reposistry.UserRepository;

@Controller
public class LoginController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/login")
    public String showLoginForm() {
        return "login";
    }

    @PostMapping("/login")
    public String processLogin(@RequestParam String username, @RequestParam String password, Model model) {
        UserDtls user = userRepository.findByEmail(username);
        if (user != null && password.equals(user.getPassword())) {
            if ("admin@gaurav.com".equals(user.getEmail())) {
                model.addAttribute("users", userRepository.findAll());
                return "admin-dashboard";
            } else {
                // Set session or authentication as needed
                return "redirect:/users";
            }
        }
        model.addAttribute("error", "Invalid email or password.");
        return "login";
    }
}
