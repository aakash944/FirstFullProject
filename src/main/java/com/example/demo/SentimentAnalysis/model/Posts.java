package com.example.demo.SentimentAnalysis.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.lang.NonNull;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Document(collection = "posts_db")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Posts {
    @Id
    private ObjectId id;

    @NonNull
    private String title;

    @NonNull
    private String content;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime dateTime;

    @DBRef
    private List<Comment> list = new ArrayList<>();

    @DBRef
    private List<Reaction> reactionsList=new ArrayList<>();
}
