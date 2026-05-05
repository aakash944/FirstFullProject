package com.example.demo.SentimentAnalysis.controller;

import com.example.demo.SentimentAnalysis.model.Reaction;
import com.example.demo.SentimentAnalysis.service.ReactionService;
import org.bson.types.ObjectId;
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

    @PostMapping("/postOfReaction/{id}")
    public void reactEmoji(@PathVariable ObjectId id,
                           @RequestBody Reaction reaction) {
        reactionService.createReaction(id, reaction);
    }
}
