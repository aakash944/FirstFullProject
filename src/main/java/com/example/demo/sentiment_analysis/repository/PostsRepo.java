package com.example.demo.sentiment_analysis.repository;

import com.example.demo.sentiment_analysis.enumeration.TypeOfAccess;
import com.example.demo.sentiment_analysis.model.Posts;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface PostsRepo extends MongoRepository<Posts, ObjectId> {
    void deleteByUserId(ObjectId userId);
    Slice<Posts> findByTypeOrUserId(TypeOfAccess typeOfAccess, ObjectId id, Pageable pageable);
}
