package com.example.demo.SentimentAnalysis.repository;

import com.example.demo.SentimentAnalysis.model.Posts;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PostsRepo extends MongoRepository<Posts, ObjectId> {

}
