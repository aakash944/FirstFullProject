package com.example.demo.sentiment_analysis.controller;

import com.example.demo.sentiment_analysis.dto.CommentDto;
import com.example.demo.sentiment_analysis.model.Comment;
import com.example.demo.sentiment_analysis.service.CommentService;
import org.bson.types.ObjectId;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<List<Comment>> getAllComment() {
        List<Comment> comments = commentService.commentCollect();
        return new ResponseEntity<>(comments, HttpStatus.OK);

    }

    @PostMapping("/postOfComment")
    public ResponseEntity<Comment> createComment(@RequestBody CommentDto comment) {
        Comment commentCreate = commentService.newComment(comment);
        return new ResponseEntity<>(commentCreate,HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable ObjectId id) {
        commentService.removeComment(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Comment> updateComment(@PathVariable ObjectId id,
                              @RequestBody CommentDto commentDto) {
        Comment comment = commentService
                .updateCreateComment(id, commentDto);
        return new ResponseEntity<>(comment,HttpStatus.OK);
    }

}
