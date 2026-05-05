package com.example.demo.SentimentAnalysis.model;

import com.example.demo.SentimentAnalysis.enumeration.ReactionType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "reaction_db")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Reaction {
    @Id
   private ObjectId id;
    private ReactionType reactionType;
}
