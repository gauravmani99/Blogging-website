package com.registation.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.registation.demo.entity.UserDtls;
import com.registation.demo.reposistry.UserRepository;

@Service
public class AuthService {
    @Autowired
    private UserRepository userRepository;


    public UserDtls authenticate(String email, String password) {
        UserDtls user = userRepository.findByEmail(email);
        if (user != null && password.equals(user.getPassword())) {
            return user;
        }
        return null;
    }
}
