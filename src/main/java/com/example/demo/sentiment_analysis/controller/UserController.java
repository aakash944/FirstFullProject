package com.example.demo.sentiment_analysis.controller;


import com.example.demo.sentiment_analysis.dto.UserDto;
import com.example.demo.sentiment_analysis.model.Users;
import com.example.demo.sentiment_analysis.service.UserService;
//import jakarta.validation.Valid;
import org.bson.types.ObjectId;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/getAllUser")
    public ResponseEntity<List<Users>> getOfAllUser() {
        List<Users> userDb = userService.getUserDb();
        return ResponseEntity.ok(userDb);
    }


    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable ObjectId id) {
        userService.removeUser(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<Users> updateUser(@PathVariable ObjectId id,
                                            @RequestBody UserDto userInfo) {

        Users updatedUser = userService.newUserUpdate(id, userInfo);
        return ResponseEntity.ok(updatedUser);
    }
}
