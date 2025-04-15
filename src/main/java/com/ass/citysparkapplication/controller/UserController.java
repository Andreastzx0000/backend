package com.ass.citysparkapplication.controller;

import com.ass.citysparkapplication.dto.UserProfileResponse;
import com.ass.citysparkapplication.dto.UserProfileUpdateRequest;
import com.ass.citysparkapplication.model.User;
import com.ass.citysparkapplication.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/{email}")
    public User getUserByEmail(@PathVariable String email) {
        return userService.getUserByEmail(email);
    }

    @PostMapping
    public User createUser(@RequestBody User user) {
        return userService.saveUser(user);
    }

    @PutMapping("/{userId}/profile")
    public void updateUserProfile(
            @PathVariable Integer userId,
            @RequestBody UserProfileUpdateRequest request
    ) {
        userService.updateUserProfile(userId, request);
    }

    @GetMapping("/{userId}/profile")
    public UserProfileResponse getUserProfile(@PathVariable Integer userId) {
        return userService.getUserProfile(userId);
    }
}
