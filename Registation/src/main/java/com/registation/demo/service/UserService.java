package com.registation.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.registation.demo.entity.UserDtls;
import com.registation.demo.reposistry.UserRepository;

@Service
public class UserService {
    public void createAdminIfNotExists() {
        String adminEmail = "admin@gaurav.com";
        if (userRepository.findByEmail(adminEmail) == null) {
            UserDtls admin = new UserDtls();
            admin.setName("Admin");
            admin.setEmail(adminEmail);
            admin.setPassword("admin123");
            admin.setConfirmPassword("admin123");
            userRepository.save(admin);
        }
    }
    @Autowired
    private UserRepository userRepository;


    public UserDtls saveUser(UserDtls user) {
        // Save password as plain text (not secure)
        return userRepository.save(user);
    }
}
