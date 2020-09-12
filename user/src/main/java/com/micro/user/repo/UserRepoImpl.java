package com.micro.user.repo;

import com.micro.user.domain.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserRepoImpl {
    private static final String COLLECTION_NAME = "user";
    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private UserRepo userRepo;

    public User addUser(User user){
        return mongoTemplate.insert(user, COLLECTION_NAME);
    }

    public User findByPrimaryEmailId(String email){
        return userRepo.findByPrimaryEmailId(email);
    }

    public User findByUserName(String userName){
        return userRepo.findByUserName(userName);
    }

    public User save(User user){
        return userRepo.save(user);
    }

    public Optional<User> findById(String userId){
        return userRepo.findById(userId);
    }
}
