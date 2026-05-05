package com.example.demo.SentimentAnalysis.service;

import com.example.demo.SentimentAnalysis.model.Comment;
import com.example.demo.SentimentAnalysis.model.Posts;
import com.example.demo.SentimentAnalysis.repository.CommentRepo;
import com.example.demo.SentimentAnalysis.repository.PostsRepo;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


@Service
public class CommentService {
    private final CommentRepo commentRepo;
    private final PostsRepo postsRepo;

    public CommentService(CommentRepo commentRepo, PostsRepo postsRepo) {
        this.commentRepo = commentRepo;

        this.postsRepo = postsRepo;
    }

    public List<Comment> commentCollect() {
        return commentRepo.findAll();
    }

    public void newComment(ObjectId id, Comment comment) {
        comment.setDateTime(LocalDateTime.now());
        Optional<Posts> byId = postsRepo.findById(id);
        Posts posts = byId.get();
        Comment savedComment = commentRepo.save(comment);
        posts.getList().add(savedComment);
        postsRepo.save(posts);

    }

    public void removeComment(ObjectId id, ObjectId idPosts) {
        Optional<Posts> byId = postsRepo.findById(idPosts);
        Posts posts = byId.get();
        posts.getList().removeIf(x->x.getId().equals(id));
        postsRepo.save(posts);
        commentRepo.deleteById(id);
    }

    public void updateCreateComment(ObjectId id, Comment comment) {
        Optional<Comment> byId = commentRepo.findById(id);
        if (byId.isPresent()) {
            Comment commentExist = byId.get();
            commentExist.setText(comment.getText() != null &&
                    !comment.getText().isEmpty() ?
                    comment.getText() : commentExist.getText());
            commentRepo.save(commentExist);
        }
    }
}
