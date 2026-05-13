package com.example.demo.sentiment_analysis.controller;

import com.example.demo.sentiment_analysis.dto.CommentDto;
import com.example.demo.sentiment_analysis.model.Comment;
import com.example.demo.sentiment_analysis.service.CommentService;
import org.bson.types.ObjectId;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import java.nio.file.AccessDeniedException;
import java.util.List;

@RestController
@RequestMapping("/api/comment")
public class CommentController {
    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping
    public ResponseEntity<List<Comment>> getAllCommentOfUser() {
        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();
        User principal = (User) authentication.getPrincipal();
        List<Comment> comments = commentService.getCommentByEmail(principal.getUsername());
        return new ResponseEntity<>(comments, HttpStatus.OK);

    }

    @PostMapping
    public ResponseEntity<Comment> createComment(@RequestBody CommentDto commentDto) throws AccessDeniedException {
        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();
        User principal = (User) authentication.getPrincipal();
        Comment commentCreated = commentService.newComment(commentDto, principal.getUsername());
        return new ResponseEntity<>(commentCreated, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable ObjectId id) throws AccessDeniedException {
        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();
        User principal = (User) authentication.getPrincipal();
        commentService.removeComment(id, principal.getUsername());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Comment> updateComment(@PathVariable ObjectId id,
                                                 @RequestBody CommentDto commentDto) throws AccessDeniedException {
        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();
        User principal = (User) authentication.getPrincipal();
        Comment comment = commentService
                .updateComment(id, commentDto,principal.getUsername());
        return new ResponseEntity<>(comment, HttpStatus.OK);
    }

}
