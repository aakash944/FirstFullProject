package com.example.demo.sentiment_analysis.service;


import com.example.demo.sentiment_analysis.dto.UserDto;
import com.example.demo.sentiment_analysis.exception.UserNotFoundException;
import com.example.demo.sentiment_analysis.exception.WeakPasswordException;
import com.example.demo.sentiment_analysis.model.Users;
import com.example.demo.sentiment_analysis.repository.CommentRepo;
import com.example.demo.sentiment_analysis.repository.PostsRepo;
import com.example.demo.sentiment_analysis.repository.ReactionRepo;
import com.example.demo.sentiment_analysis.repository.UserRepo;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Service
public class UserService {
    private static final Set<String> COMMON_PASSWORDS = Set.of(
            "password", "123456", "password123", "admin", "qwerty"
    );
    private final UserRepo userRepo;
    private final PostsRepo postsRepo;
    private final CommentRepo commentRepo;
    private final ReactionRepo reactionRepo;

    public UserService(UserRepo userRepo, PostsRepo postsRepo, CommentRepo commentRepo, ReactionRepo reactionRepo) {
        this.userRepo = userRepo;
        this.postsRepo = postsRepo;
        this.commentRepo = commentRepo;
        this.reactionRepo = reactionRepo;
    }

    public List<Users> getUserDb() {
        return userRepo.findAll();
    }

    public Users newUserCreate(UserDto userInfo) {
        validatePassword(userInfo.getPassword());
        Users users = new Users();
        users.setPassword(userInfo.getPassword());
        users.setUserEmail(userInfo.getUserEmail());
        users.setDateTime(LocalDateTime.now());
        return userRepo.save(users);
    }

    // /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{8,}$/;

    private void validatePassword(String password) {
        if (COMMON_PASSWORDS.contains(password.toLowerCase())) {
            throw new WeakPasswordException("Password is too common");
        }
        if (!password.matches(".*[A-Z].*")) {
            throw new WeakPasswordException("Must contain uppercase letter");
        }

        if (!password.matches(".*[a-z].*")) {
            throw new WeakPasswordException("Must contain lowercase letter");
        }

        if (!password.matches(".*\\d.*")) {
            throw new WeakPasswordException("Must contain number");
        }

        if (!password.matches(".*[@$!%*?&].*")) {
            throw new WeakPasswordException("Must contain special character");
        }


        if (password.matches(".*(.)\\1{4,}.*")) {
            throw new WeakPasswordException("No character can repeat more than 4 times consecutively");
        }


        if (password.matches(".*(\\d)\\1{4,}.*")) {
            throw new WeakPasswordException("No number can repeat more than 4 times consecutively");
        }


        if (password.matches(".*([@$!%*?&])\\1{4,}.*")) {
            throw new WeakPasswordException("No special character can repeat more than 4 times consecutively");
        }
    }

    public void removeUser(ObjectId userId) {
        userRepo.deleteById(userId);
        postsRepo.deleteByUserId(userId);
        commentRepo.deleteByUserId(userId);
        reactionRepo.deleteByUserId(userId);


    }

    public Users newUserUpdate(ObjectId id, UserDto usersDto) {

        Users user = userRepo.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        if (usersDto.getUserEmail() != null && !usersDto.getUserEmail().isEmpty()) {
            user.setUserEmail(usersDto.getUserEmail());
        }

        if (usersDto.getPassword() != null && !usersDto.getPassword().isEmpty()) {
            user.setPassword(usersDto.getPassword());
        }

        return userRepo.save(user);
    }
}