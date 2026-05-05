package com.example.demo.SentimentAnalysis.repository;

import com.example.demo.SentimentAnalysis.model.Comment;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CommentRepo extends MongoRepository<Comment, ObjectId> {
}
