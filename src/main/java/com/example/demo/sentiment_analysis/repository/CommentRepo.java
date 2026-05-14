package com.example.demo.sentiment_analysis.repository;

import com.example.demo.sentiment_analysis.model.Comment;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface CommentRepo extends MongoRepository<Comment, ObjectId> {
    void deleteByPostId(ObjectId postsId);
    void deleteByUserId(ObjectId userId);

    Slice<Comment> findByPostIdIn(List<ObjectId> publicPostIds, Pageable pageable);
}
