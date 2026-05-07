package com.example.demo.sentiment_analysis.controller;

import com.example.demo.sentiment_analysis.dto.PostDto;
import com.example.demo.sentiment_analysis.model.Posts;
import com.example.demo.sentiment_analysis.service.LogicOfPosts;
import org.bson.types.ObjectId;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<List<Posts>> getOfAllSentiment() {
        List<Posts> sentimentResult = logicOfSentiment.getSentimentResult();
        return new ResponseEntity<>(sentimentResult, HttpStatus.OK);
    }

    @PostMapping("/post")
    public ResponseEntity<Posts> createPost(@RequestBody PostDto postDto) {
        Posts posts = logicOfSentiment.newPostCreate(postDto);
        return new ResponseEntity<>(posts,HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable ObjectId id) {
        logicOfSentiment.removePost(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Posts> updatePost(@PathVariable ObjectId id,
                           @RequestBody PostDto postDto) {
        Posts posts = logicOfSentiment.newPostUpdate(id, postDto);
        return new ResponseEntity<>(posts,HttpStatus.OK);
    }
}
