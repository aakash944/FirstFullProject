package com.example.demo.sentiment_analysis.service;

import com.example.demo.sentiment_analysis.dto.PostDto;
import com.example.demo.sentiment_analysis.enumeration.TypeOfAccess;
import com.example.demo.sentiment_analysis.exception.PostsNotFoundException;
import com.example.demo.sentiment_analysis.exception.UserNotFoundException;
import com.example.demo.sentiment_analysis.model.Posts;

import com.example.demo.sentiment_analysis.model.Reaction;
import com.example.demo.sentiment_analysis.model.Users;
import com.example.demo.sentiment_analysis.pagination_slice.PaginatedResponse;
import com.example.demo.sentiment_analysis.repository.CommentRepo;
import com.example.demo.sentiment_analysis.repository.PostsRepo;

import com.example.demo.sentiment_analysis.repository.ReactionRepo;
import com.example.demo.sentiment_analysis.repository.UserRepo;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


import java.nio.file.AccessDeniedException;
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

    public PaginatedResponse<Posts> getPostsByUserEmail(String userEmail, Pageable pageable) {

        Users currentUser = userRepo.findByUserEmail(userEmail);

        if (currentUser == null) {
            throw new UsernameNotFoundException("User not found");
        }

        Slice<Posts> slice = postsRepo.findByTypeOrUserId(
                TypeOfAccess.PUBLIC,
                currentUser.getId(),
                pageable
        );
        PaginatedResponse<Posts> response = new PaginatedResponse<>();
        response.setContent(slice.getContent());
        response.setPageNumber(slice.getNumber());
        response.setPageSize(slice.getSize());
        response.setFirst(slice.isFirst());
        response.setLast(!slice.hasNext());
        response.setHasNext(slice.hasNext());

        return response;

    }

    public Posts createPostForUser(PostDto postDto, String currentUserEmail) {
        Users currentUser = userRepo.findByUserEmail(currentUserEmail);
        Posts posts = new Posts();
        posts.setType(postDto.getTypeOfAccess());
        posts.setUserId(currentUser.getId());
        posts.setContent(postDto.getContent());
        posts.setTitle(postDto.getTitle());
        posts.setCreateAt(LocalDateTime.now());

        return postsRepo.save(posts);
    }

    public void removePost(ObjectId id, String userEmail) throws AccessDeniedException {

        Users currentUser = userRepo.findByUserEmail(userEmail);
        Posts post = postsRepo.findById(id)
                .orElseThrow(() ->
                        new PostsNotFoundException("Post not found"));

        // OWNERSHIP CHECK
        if (!post.getUserId().equals(currentUser.getId())) {
            throw new AccessDeniedException(
                    "You can delete only your own post"
            );
        }

        // delete related comments
        commentRepo.deleteByPostId(post.getId());

        // delete related reactions
        reactionRepo.deleteByPostId(post.getId());

        // delete post
        postsRepo.delete(post);
    }

    public Posts updatePost(ObjectId id, PostDto postDto, String userEmail) {
        Users currentUser = userRepo.findByUserEmail(userEmail);
        Posts posts = postsRepo.findById(id)
                .orElseThrow(() -> new PostsNotFoundException("Post is not found"));
        if (!posts.getUserId().equals(currentUser.getId())) {
            throw new UserNotFoundException("User not found exception");
        }

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
