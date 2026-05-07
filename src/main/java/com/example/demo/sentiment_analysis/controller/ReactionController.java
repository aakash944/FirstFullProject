package com.example.demo.sentiment_analysis.controller;

import com.example.demo.sentiment_analysis.dto.ReactionDto;
import com.example.demo.sentiment_analysis.model.Reaction;
import com.example.demo.sentiment_analysis.service.ReactionService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/react")
public class ReactionController {
    private final ReactionService reactionService;

    public ReactionController(ReactionService reactionService) {
        this.reactionService = reactionService;
    }

    @GetMapping
    public List<Reaction> getAllReaction() {
        return reactionService.allReaction();
    }

    @PostMapping("/postOfReaction")
    public void reactEmoji(@RequestBody ReactionDto reactionDto) {
        reactionService.createReaction(reactionDto);
    }
}
