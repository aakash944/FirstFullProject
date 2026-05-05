package com.example.demo.SentimentAnalysis.controller;

import com.example.demo.SentimentAnalysis.model.Posts;
import com.example.demo.SentimentAnalysis.service.LogicOfPosts;
import org.bson.types.ObjectId;
import org.springframework.web.bind.annotation.*;


import java.util.List;


@RestController
@RequestMapping("/api/controller")
public class FrontControllerOfPosts {
    private final LogicOfPosts logicOfSentiment;

    public FrontControllerOfPosts(LogicOfPosts logicOfSentiment) {
        this.logicOfSentiment = logicOfSentiment;
    }

    @GetMapping
    public List<Posts> getOfAllSentiment() {
        return logicOfSentiment.getSentimentResult();
    }

    @PostMapping("/post/{userEmail}")
    public void createPost(@PathVariable String userEmail, @RequestBody Posts sentimentContent) {
        logicOfSentiment.newPostCreate(userEmail, sentimentContent);
    }

    @DeleteMapping("/delete/{id}/{userEmail}")
    public void deletePost(@PathVariable ObjectId id,@PathVariable String userEmail) {
        logicOfSentiment.removePost(id,userEmail);
    }

    @PutMapping("/update/{id}")
    public void updatePost(@PathVariable ObjectId id,
                           @RequestBody Posts sentimentContent) {
        logicOfSentiment.newPostUpdate(id, sentimentContent);
    }
}
