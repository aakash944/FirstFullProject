package com.example.demo.sentiment_analysis.controller;

import com.example.demo.sentiment_analysis.dto.ReactionDto;
import com.example.demo.sentiment_analysis.model.Reaction;
import com.example.demo.sentiment_analysis.service.ReactionService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.nio.file.AccessDeniedException;
import java.util.List;



@RestController
@RequestMapping("/api/react")
public class ReactionController {
    private final ReactionService reactionService;

    public ReactionController(ReactionService reactionService) {
        this.reactionService = reactionService;
    }

    @GetMapping
    public List<Reaction> getAllReaction() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        org.springframework.security.core.userdetails.User principal =
                (org.springframework.security.core.userdetails.User) authentication.getPrincipal();
        return reactionService.getAllReactions(principal.getUsername());
    }


    @PostMapping
    public void reactEmoji(@RequestBody ReactionDto reactionDto) throws AccessDeniedException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        org.springframework.security.core.userdetails.User principal =
                (org.springframework.security.core.userdetails.User) authentication.getPrincipal();
        reactionService.createReaction(reactionDto,principal.getUsername());
    }
}
