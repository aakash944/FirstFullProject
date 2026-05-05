package com.example.demo.SentimentAnalysis.service;


import com.example.demo.SentimentAnalysis.model.Users;
import com.example.demo.SentimentAnalysis.repository.UserRepo;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepo userRepo;

    public UserService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    public List<Users> getUserDb() {
        return userRepo.findAll();
    }

    public Users newUserCreate(Users userInfo) {
        userInfo.setDateTime(LocalDateTime.now());
        return userRepo.save(userInfo);
    }

    public void removeUser(ObjectId id) {
        userRepo.deleteById(id);
    }

    public void newUserUpdate(ObjectId id, Users userInfo) {
        Optional<Users> byId = userRepo.findById(id);
        Users userExist = byId.get();
        if (byId.isPresent()){
            userExist.setUserEmail(userInfo.getUserEmail()!=null
                    && !userInfo.getUserEmail().isEmpty()? userInfo.getUserEmail()
                    : userExist.getUserEmail());

            userExist.setPassword(userInfo.getPassword()!=null
                    && !userInfo.getPassword().isEmpty()? userInfo.getPassword()
                    : userExist.getPassword());
        }
        userRepo.save(userExist);
    }
}