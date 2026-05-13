package com.example.demo.sentiment_analysis.repository;

import com.example.demo.sentiment_analysis.enumeration.TypeOfAccess;
import com.example.demo.sentiment_analysis.model.Posts;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface PostsRepo extends MongoRepository<Posts, ObjectId> {
    void deleteByUserId(ObjectId userId);
    List<Posts> findByType(TypeOfAccess type);
    List<Posts> findByUserId(ObjectId userId);
}
