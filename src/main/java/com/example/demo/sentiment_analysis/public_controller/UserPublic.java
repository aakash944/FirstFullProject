package com.example.demo.sentiment_analysis.public_controller;

import com.example.demo.sentiment_analysis.dto.UserDto;
import com.example.demo.sentiment_analysis.user.model.Users;
import com.example.demo.sentiment_analysis.user.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/public")
public class UserPublic {
    private final UserService userService;

    public UserPublic( UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/sign_up")
//    @Valid
    public ResponseEntity<Users> createUser(
            @RequestBody UserDto userDto) {
        Users users = userService.newUserCreate(userDto);
        return new ResponseEntity<>(users, HttpStatus.CREATED);
    }

}
