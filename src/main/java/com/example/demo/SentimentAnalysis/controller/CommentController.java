package com.example.demo.SentimentAnalysis.controller;

import com.example.demo.SentimentAnalysis.model.Comment;
import com.example.demo.SentimentAnalysis.service.CommentService;
import org.bson.types.ObjectId;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comment")
public class CommentController {
    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping("/getOfComment")
    public List<Comment> getAllComment() {
        return commentService.commentCollect();
    }

    @PostMapping("/postOfComment/{id}")
    public void createComment(@PathVariable ObjectId id,@RequestBody Comment comment) {
        commentService.newComment(id,comment);
    }

    @DeleteMapping("/delete/{id}/{IdPosts}")
    public void deleteById(@PathVariable ObjectId id,@PathVariable ObjectId IdPosts) {
        commentService.removeComment(id,IdPosts);
    }

    @PutMapping("/update/{id}")
    public void updateComment(@PathVariable ObjectId id,
                              @RequestBody Comment comment) {
        commentService.updateCreateComment(id, comment);
    }

}
