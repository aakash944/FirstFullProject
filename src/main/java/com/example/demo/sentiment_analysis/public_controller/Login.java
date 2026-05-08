package com.example.demo.sentiment_analysis.public_controller;

import com.example.demo.sentiment_analysis.dto.UserDto;
import com.example.demo.sentiment_analysis.model.Users;
import com.example.demo.sentiment_analysis.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/signUp")
public class Login {
    private final UserService userService;

    public Login(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/createNewUser")
//    @Valid
    public ResponseEntity<Users> createUser(
            @RequestBody UserDto userDto) {
        Users users = userService.newUserCreate(userDto);
        return new ResponseEntity<>(users, HttpStatus.CREATED);
    }
}
