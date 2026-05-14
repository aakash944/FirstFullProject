package com.example.demo.sentiment_analysis.admin_controller;

import com.example.demo.sentiment_analysis.model.Users;
import com.example.demo.sentiment_analysis.pagination_slice.PaginatedResponse;
import com.example.demo.sentiment_analysis.service.UserService;
import org.springframework.data.domain.Slice;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.data.domain.Pageable;


@RestController
@RequestMapping("/Admin_userInfo")
public class AdminInfo {
    private final UserService userService;

    public AdminInfo(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/getAllUser")
    public ResponseEntity<PaginatedResponse<Users>> getAllUser(Pageable pageable) {
        return ResponseEntity.ok(userService.getUserDb(pageable));
    }
}