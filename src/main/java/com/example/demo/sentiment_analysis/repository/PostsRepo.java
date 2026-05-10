package com.example.demo.sentiment_analysis.repository;

import com.example.demo.sentiment_analysis.model.Posts;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface PostsRepo extends MongoRepository<Posts, ObjectId> {
    void deleteByUserId(ObjectId userId);
    List<Posts> findByUserId(ObjectId id);

}
