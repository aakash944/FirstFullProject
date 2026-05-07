package com.example.demo.sentiment_analysis.service;

import com.example.demo.sentiment_analysis.dto.PostDto;
import com.example.demo.sentiment_analysis.exception.PostsNotFoundException;
import com.example.demo.sentiment_analysis.exception.UserNotFoundException;
import com.example.demo.sentiment_analysis.model.Posts;

import com.example.demo.sentiment_analysis.model.Users;
import com.example.demo.sentiment_analysis.repository.CommentRepo;
import com.example.demo.sentiment_analysis.repository.PostsRepo;

import com.example.demo.sentiment_analysis.repository.ReactionRepo;
import com.example.demo.sentiment_analysis.repository.UserRepo;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;


import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


@Service
public class LogicOfPosts {
    private final PostsRepo postsRepo;
    private final UserRepo userRepo;
    private final CommentRepo commentRepo;
    private final ReactionRepo reactionRepo;

    public LogicOfPosts(PostsRepo postsRepo, UserRepo userRepo, CommentRepo commentRepo, ReactionRepo reactionRepo) {
        this.postsRepo = postsRepo;
        this.userRepo = userRepo;
        this.commentRepo = commentRepo;
        this.reactionRepo = reactionRepo;
    }

    public List<Posts> getSentimentResult() {
        return postsRepo.findAll();
    }

    public Posts newPostCreate(PostDto postDto) {
        Optional<Users> byId = userRepo.findById(postDto.getUserId());
        if (byId.isPresent()) {
            Users users = byId.get();
            Posts posts = new Posts();
            posts.setUserId(users.getId());
            posts.setContent(postDto.getContent());
            posts.setTitle(postDto.getTitle());
            posts.setCreateAt(LocalDateTime.now());
            return postsRepo.save(posts);
        } else {
            throw new UserNotFoundException("Users is not found " + byId);
        }
    }

    public void removePost(ObjectId id) {
        postsRepo.deleteById(id);
        commentRepo.deleteByPostId(id);
        reactionRepo.deleteByUserId(id);
    }

    public Posts newPostUpdate(ObjectId id, PostDto postDto) {

         userRepo.findById(postDto.getUserId())
                .orElseThrow(() -> new UserNotFoundException("User is not found"));

        Posts posts = postsRepo.findById(id)
                .orElseThrow(() -> new PostsNotFoundException("Post is not found"));

        if (postDto.getContent() != null && !postDto.getContent().isEmpty()) {
            posts.setContent(postDto.getContent());
        }

        if (postDto.getTitle() != null && !postDto.getTitle().isEmpty()) {
            posts.setTitle(postDto.getTitle());
        }

        posts.setCreateAt(LocalDateTime.now());

        return postsRepo.save(posts);
    }
}
