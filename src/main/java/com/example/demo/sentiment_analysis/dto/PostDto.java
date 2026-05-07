package com.example.demo.sentiment_analysis.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostDto {
    private ObjectId userId;
    private String title;
    private String content;

}
