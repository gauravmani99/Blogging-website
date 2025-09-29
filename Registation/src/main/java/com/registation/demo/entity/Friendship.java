package com.registation.demo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table
public class Friendship {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserDtls user;

    @ManyToOne
    @JoinColumn(name = "friend_id")
    private UserDtls friend;

    @Column
    private String status; // "pending", "accepted"

    // Getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public UserDtls getUser() { return user; }
    public void setUser(UserDtls user) { this.user = user; }
    public UserDtls getFriend() { return friend; }
    public void setFriend(UserDtls friend) { this.friend = friend; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
