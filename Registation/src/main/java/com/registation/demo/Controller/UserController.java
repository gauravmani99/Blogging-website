package com.registation.demo.Controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.registation.demo.entity.UserDtls;
import com.registation.demo.service.UserService;

@Controller
public class UserController {

    @Autowired
    private UserService userService;


    @GetMapping("/register")
    public String showForm(Model model) {
        model.addAttribute("user", new UserDtls()); // empty form object
        return "register"; // register.html
    }

    @PostMapping("/register")
    public String processForm(@ModelAttribute UserDtls user, Model model) {
        userService.saveUser(user);
        model.addAttribute("user", new UserDtls());
        model.addAttribute("message", "Registration successful!");
        return "register";
    }
}
