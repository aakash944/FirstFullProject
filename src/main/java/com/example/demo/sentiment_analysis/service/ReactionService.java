package com.example.demo.sentiment_analysis.service;

import com.example.demo.sentiment_analysis.dto.ReactionDto;
import com.example.demo.sentiment_analysis.exception.UserNotFoundException;
import com.example.demo.sentiment_analysis.model.Reaction;
import com.example.demo.sentiment_analysis.model.Users;
import com.example.demo.sentiment_analysis.repository.PostsRepo;
import com.example.demo.sentiment_analysis.repository.ReactionRepo;
import com.example.demo.sentiment_analysis.repository.UserRepo;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class ReactionService {

    private final ReactionRepo reactionRepo;
    private final UserRepo userRepo;
    private final PostsRepo postsRepo;

    public ReactionService(ReactionRepo reactionRepo, UserRepo userRepo, PostsRepo postsRepo) {
        this.reactionRepo = reactionRepo;
        this.userRepo = userRepo;
        this.postsRepo = postsRepo;
    }

    public Optional<Reaction> allReaction(String userEmail) {

        Users currentUser = userRepo.findByUserEmail(userEmail);

        if (currentUser == null) {
            throw new UsernameNotFoundException("User not found");
        }
        return reactionRepo.findByUserId(currentUser.getId());
    }

    public void createReaction(ReactionDto dto, String userEmail) {
        Users byUserEmail = userRepo.findByUserEmail(userEmail);
        userRepo.findById(byUserEmail.getId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        postsRepo.findById(dto.getPostId())
                .orElseThrow(() -> new RuntimeException("Post not found"));


        Optional<Reaction> existing = reactionRepo
                .findByUserIdAndPostId(byUserEmail.getId(), dto.getPostId());

        if (existing.isEmpty() && dto.getUserId().equals(byUserEmail.getId())) {
            Reaction reaction = new Reaction();
            reaction.setUserId(dto.getUserId());
            reaction.setPostId(dto.getPostId());
            reaction.setReactionType(dto.getReactionType());
            reaction.setCreatedAt(LocalDateTime.now());

            reactionRepo.save(reaction);

        } else {
            Reaction old = existing.get();

            if (old.getReactionType().equals(dto.getReactionType())) {
                reactionRepo.delete(old);

            } else {
                old.setReactionType(dto.getReactionType());
                reactionRepo.save(old);
            }
        }
    }
}

