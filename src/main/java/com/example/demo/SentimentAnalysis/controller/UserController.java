package com.example.demo.SentimentAnalysis.controller;


import com.example.demo.SentimentAnalysis.model.Users;
import com.example.demo.SentimentAnalysis.service.UserService;
import org.bson.types.ObjectId;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/getAllUser")
    public List<Users> getOfAllUser() {
        return userService.getUserDb();
    }

    @PostMapping("/createNewUser")
    public Users createUser(@RequestBody Users userInfo) {

        return userService.newUserCreate(userInfo);
    }

    @DeleteMapping("/delete/{id}")
    public void deletePost(@PathVariable ObjectId id) {
        userService.removeUser(id);
    }

    @PutMapping("/update/{id}")
    public void updatePost(@PathVariable ObjectId id,
                           @RequestBody Users userInfo) {
        userService.newUserUpdate(id, userInfo);
    }
}
