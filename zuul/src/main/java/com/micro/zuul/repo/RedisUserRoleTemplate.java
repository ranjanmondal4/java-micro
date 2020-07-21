package com.micro.zuul.repo;

import com.micro.zuul.domain.RedisUserRole;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;

@Slf4j
@Repository
public class RedisUserRoleTemplate {
    @Autowired
    RedisTemplate redisTemplate;

    private HashOperations hashOperations;

    @PostConstruct
    public void init(){
        this.hashOperations = redisTemplate.opsForHash();
    }
//
//    public UserRepository() {
//        this.hashOperations = redisTemplate.opsForHash();
//    }

    public void add(RedisUserRole user) {
        hashOperations.put("USER", user.getToken(), user);
        log.info("User with token {} saved", user.getToken());
    }

    public RedisUserRole get(String token) {
        return (RedisUserRole) hashOperations.get("USER", token);
    }

//    public Map<String, User> getAll(){
//        return hashOperations.entries("USER");
//    }
//
//    public void update(User user) {
//        hashOperations.put("USER", user.getUserId(), user);
//        logger.info(String.format("User with ID %s updated", user.getUserId()));
//    }
//
//    public void delete(String userId) {
//        hashOperations.delete("USER", userId);
//        logger.info(String.format("User with ID %s deleted", userId));
//    }
}
