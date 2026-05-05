package com.example.demo.SentimentAnalysis.repository;

import com.example.demo.SentimentAnalysis.model.Reaction;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ReactionRepo extends MongoRepository<Reaction,ObjectId> {

}
