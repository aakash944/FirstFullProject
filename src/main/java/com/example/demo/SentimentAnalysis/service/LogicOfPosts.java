package com.example.demo.SentimentAnalysis.service;

import com.example.demo.SentimentAnalysis.model.Posts;
import com.example.demo.SentimentAnalysis.model.Users;
import com.example.demo.SentimentAnalysis.repository.PostsRepo;
import com.example.demo.SentimentAnalysis.repository.UserRepo;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class LogicOfPosts {
    private final PostsRepo sentimentRepo;
    private final UserRepo userRepo;

    public LogicOfPosts(PostsRepo sentimentRepo, UserRepo userRepo) {
        this.sentimentRepo = sentimentRepo;
        this.userRepo = userRepo;
    }

    public List<Posts> getSentimentResult() {
        return sentimentRepo.findAll();
    }

    public void newPostCreate(String userEmail, Posts sentimentContent) {
        sentimentContent.setDateTime(LocalDateTime.now());
        Users byUserEmail = userRepo.findByUserEmail(userEmail);
        Posts postSaved = sentimentRepo.save(sentimentContent);
        byUserEmail.getPost().add(postSaved);
        userRepo.save(byUserEmail);
    }

    public void removePost(ObjectId id, String userEmail) {
        Users byUserEmail = userRepo.findByUserEmail(userEmail);
       byUserEmail.getPost().removeIf(x->x.getId().equals(id));
       userRepo.save(byUserEmail);
        sentimentRepo.deleteById(id);
    }

    public void newPostUpdate(ObjectId id, Posts sentiment) {
        Optional<Posts> byId = sentimentRepo.findById(id);
        Posts oldContent = byId.get();

        if (byId.isPresent()) {
            oldContent.setContent(sentiment.getContent() != null
                    && !sentiment.getContent().isEmpty() ?
                    sentiment.getContent() : oldContent.getContent());

            oldContent.setTitle(sentiment.getTitle() != null
                    && !sentiment.getTitle().isEmpty() ?
                    sentiment.getTitle() : oldContent.getTitle());
        }
        sentimentRepo.save(oldContent);
    }
}
