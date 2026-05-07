package com.example.demo.sentiment_analysis.service;

import com.example.demo.sentiment_analysis.dto.CommentDto;
import com.example.demo.sentiment_analysis.exception.CommentNotFoundException;
import com.example.demo.sentiment_analysis.exception.PostsNotFoundException;
import com.example.demo.sentiment_analysis.exception.UserNotFoundException;
import com.example.demo.sentiment_analysis.model.Comment;
import com.example.demo.sentiment_analysis.repository.CommentRepo;
import com.example.demo.sentiment_analysis.repository.PostsRepo;
import com.example.demo.sentiment_analysis.repository.UserRepo;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;



@Service
public class CommentService {
    private final CommentRepo commentRepo;
    private final UserRepo userRepo;
    private final PostsRepo postsRepo;

    public CommentService(CommentRepo commentRepo, UserRepo userRepo, PostsRepo postsRepo) {
        this.commentRepo = commentRepo;

        this.userRepo = userRepo;
        this.postsRepo = postsRepo;
    }

    public List<Comment> commentCollect() {
        return commentRepo.findAll();
    }

    public Comment newComment(CommentDto commentDto) {
        userRepo.findById(commentDto.getUserId()).
                orElseThrow(() -> new UserNotFoundException("User not found"));
        postsRepo.findById(commentDto.getPostId()).
                orElseThrow(() -> new PostsNotFoundException("Post not found"));
        Comment comment = new Comment();
        comment.setUserId(commentDto.getUserId());
        comment.setPostId(commentDto.getPostId());
        comment.setText(commentDto.getText());
        comment.setCreatedAt(LocalDateTime.now());
        return commentRepo.save(comment);
    }

    public void removeComment(ObjectId id) {
        commentRepo.deleteById(id);
    }

    public Comment updateCreateComment(ObjectId id, CommentDto commentDto) {
        commentRepo.findById(id).orElseThrow(()->new CommentNotFoundException("Comment is not found "));
        userRepo.findById(commentDto.getUserId()).
                orElseThrow(() -> new UserNotFoundException("User not found"));
        postsRepo.findById(commentDto.getPostId()).
                orElseThrow(() -> new PostsNotFoundException("Post not found"));
        Comment commentExist = new Comment();
        commentExist.setText(commentDto.getText() != null &&
                !commentDto.getText().isEmpty() ?
                commentDto.getText() : commentExist.getText());
        commentExist.setCreatedAt(LocalDateTime.now());
        return commentRepo.save(commentExist);
    }
}

