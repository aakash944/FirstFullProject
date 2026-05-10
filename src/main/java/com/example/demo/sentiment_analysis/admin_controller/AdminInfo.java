package com.example.demo.sentiment_analysis.admin_controller;

import com.example.demo.sentiment_analysis.model.Users;
import com.example.demo.sentiment_analysis.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/Admin_userInfo")
public class AdminInfo {
    private final UserService userService;
    public AdminInfo(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/getAllUser")
    public ResponseEntity<List<Users>> getAllUser() {
        List<Users> userDb = userService.getUserDb();
        return ResponseEntity.ok(userDb);

    }
}