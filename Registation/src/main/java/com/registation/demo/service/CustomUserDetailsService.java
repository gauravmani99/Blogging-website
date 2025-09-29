package com.registation.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.registation.demo.entity.UserDtls;
import com.registation.demo.reposistry.UserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserDtls user = userRepository.findByEmail(email);
        if (user == null) {
            System.out.println("DEBUG: User not found for email: " + email);
            throw new UsernameNotFoundException("User not found with email: " + email);
        }
        System.out.println("DEBUG: Found user: " + user.getEmail() + ", password in DB: " + user.getPassword());
        // Password entered by user is not available here, but you can check if authentication fails in SecurityConfig
        return User.withUsername(user.getEmail())
                .password(user.getPassword())
                .authorities("USER")
                .build();
    }
}