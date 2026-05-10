package com.example.demo.sentiment_analysis.service;

import com.example.demo.sentiment_analysis.dto.CommentDto;
import com.example.demo.sentiment_analysis.exception.PostsNotFoundException;
import com.example.demo.sentiment_analysis.model.Comment;
import com.example.demo.sentiment_analysis.model.Posts;
import com.example.demo.sentiment_analysis.model.Users;
import com.example.demo.sentiment_analysis.repository.CommentRepo;
import com.example.demo.sentiment_analysis.repository.PostsRepo;
import com.example.demo.sentiment_analysis.repository.UserRepo;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

import java.nio.file.AccessDeniedException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


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

    public List<Comment> getCommentByEmail(String userEmail) {
        Users byUserEmail = userRepo.findByUserEmail(userEmail);
        return commentRepo.findByUserId(byUserEmail.getId());
    }

    public Comment newComment(CommentDto commentDto, String userEmail) throws AccessDeniedException {
        Users currentUser = userRepo.findByUserEmail(userEmail);
        Posts post = postsRepo.findById(commentDto.getPostId())
                .orElseThrow(() ->
                        new PostsNotFoundException("Post not found"));

        // OWNER CHECK
        if (!post.getUserId().equals(currentUser.getId())) {
            throw new AccessDeniedException(
                    "You can only comment on your own post"
            );
        }

        Comment comment = new Comment();

        comment.setUserId(currentUser.getId());
        comment.setPostId(post.getId());
        comment.setText(commentDto.getText());
        comment.setCreatedAt(LocalDateTime.now());

        return commentRepo.save(comment);
    }

    public void removeComment(ObjectId id, String userName) throws AccessDeniedException {
        Users byUserEmail = userRepo.findByUserEmail(userName);
        Optional<Comment> byId = commentRepo.findById(id);
        Comment comment = byId.get();
        if (!byUserEmail.getId().equals(comment.getUserId())) {
            throw new AccessDeniedException(
                    "You can only remove on your own post"
            );
        }
        commentRepo.deleteById(id);
    }

    public Comment updateCreateComment(ObjectId id, CommentDto commentDto, String userEmail) {
        Users byUserEmail = userRepo.findByUserEmail(userEmail);

        Optional<Comment> byId = commentRepo.findById(id);

        Comment comment = byId.get();

        postsRepo.findById(commentDto.getPostId()).
                orElseThrow(() -> new PostsNotFoundException("Post not found"));
        if (!byUserEmail.getId().equals(comment.getUserId())) {
            throw new PostsNotFoundException("Posts not found exception ");
        }
        Comment commentExist = new Comment();
        commentExist.setUserId(commentDto.getUserId());
        commentExist.setPostId(commentDto.getPostId());

        commentExist.setText(commentDto.getText() != null &&
                !commentDto.getText().isEmpty() ?
                commentDto.getText() : commentExist.getText());

        commentExist.setCreatedAt(LocalDateTime.now());
        return commentRepo.save(commentExist);
    }
}

