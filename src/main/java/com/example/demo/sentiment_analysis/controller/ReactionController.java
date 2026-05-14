package com.example.demo.sentiment_analysis.controller;

import com.example.demo.sentiment_analysis.dto.ReactionDto;
import com.example.demo.sentiment_analysis.model.Reaction;
import com.example.demo.sentiment_analysis.pagination_slice.PaginatedResponse;
import com.example.demo.sentiment_analysis.service.ReactionService;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.nio.file.AccessDeniedException;


@RestController
@RequestMapping("/api/react")
public class ReactionController {
    private final ReactionService reactionService;

    public ReactionController(ReactionService reactionService) {
        this.reactionService = reactionService;
    }

    @GetMapping
    public ResponseEntity<PaginatedResponse<Reaction>>getAllReaction(Pageable pageable) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        org.springframework.security.core.userdetails.User principal =
                (org.springframework.security.core.userdetails.User) authentication.getPrincipal();

        PaginatedResponse<Reaction> allReactions = reactionService.getAllReactions(principal.getUsername(), pageable);
        return new ResponseEntity<>(allReactions, HttpStatus.OK);
    }


    @PostMapping
    public void reactEmoji(@RequestBody ReactionDto reactionDto) throws AccessDeniedException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        org.springframework.security.core.userdetails.User principal =
                (org.springframework.security.core.userdetails.User) authentication.getPrincipal();
        reactionService.createReaction(reactionDto,principal.getUsername());
    }
}
