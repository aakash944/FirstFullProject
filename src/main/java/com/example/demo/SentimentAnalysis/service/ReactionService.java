package com.example.demo.SentimentAnalysis.service;

import com.example.demo.SentimentAnalysis.model.Posts;
import com.example.demo.SentimentAnalysis.model.Reaction;
import com.example.demo.SentimentAnalysis.repository.PostsRepo;
import com.example.demo.SentimentAnalysis.repository.ReactionRepo;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReactionService {
    private final ReactionRepo reactionRepo;
    private final PostsRepo postsRepo;

    public ReactionService(ReactionRepo reactionRepo, PostsRepo postsRepo) {
        this.reactionRepo = reactionRepo;
        this.postsRepo = postsRepo;
    }

    public List<Reaction> allReaction() {
        return reactionRepo.findAll();
    }

    public void createReaction(ObjectId postId, Reaction reaction) {
        Optional<Posts> byId = postsRepo.findById(postId);
        if (byId.isEmpty()) {
            throw new RuntimeException("Posts is not found");
        }
        Posts posts = byId.get();
        toggleReaction(posts, reaction);
    }

    public void toggleReaction(Posts post, Reaction reaction) {
        List<Reaction> reactionsList = post.getReactionsList();
        Optional<Reaction> firstExisting =
                reactionsList.stream().
                        filter(x -> x.getId().
                                equals(reaction.getId())).findFirst();
        if (!firstExisting.isEmpty()) {
            Reaction reactionSaved = reactionRepo.save(reaction);
            post.getReactionsList().add(reactionSaved);
            postsRepo.save(post);

        } else {

            boolean equals = post.getReactionsList().getReactionType().equals(reaction.getReactionType());
            if (equals) {
                post.getReactionsList().removeIf(x -> x.getId().equals(reaction.getId()));
                postsRepo.save(post);
            }
//        }


//            List<Reaction> reactions = post.getReactionsList();
//
//            Optional<Reaction> existing = reactions.stream()
//                    .filter(r -> r.getId().equals(reaction.getId()))
//                    .findFirst();
//
//            if (!existing.isEmpty()) {
//                // ADD
//                Reaction saved = reactionRepo.save(reaction);
//                reactions.add(saved);
//
//            } else {
//                Reaction old = existing.get();
//
//                if (old.getReactionType().equals(reaction.getReactionType())) {
//                    // REMOVE (toggle off)
//                    reactions.remove(old);
//                    reactionRepo.delete(old);
//
//                } else {
//                    // UPDATE reaction type
//                    old.setReactionType(reaction.getReactionType());
//                    reactionRepo.save(old);
//                }
//            }
//
//            postsRepo.save(post);
//        }
        }
    }
}
