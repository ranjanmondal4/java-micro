package com.micro.user.repo;

import com.micro.user.domain.RedisUserRole;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.List;

@Slf4j
@Repository
public class RedisUserRoleTemplate {

    @Autowired
    private RedisTemplate redisTemplate;

    private HashOperations hashOperations;

    @PostConstruct
    public void init(){
        this.hashOperations = redisTemplate.opsForHash();
    }
//
//    public UserRepository() {
//        this.hashOperations = redisTemplate.opsForHash();
//    }

//    public void add(RedisUserRole user) {
//        hashOperations.put("USER", user.getToken(), user);
//        log.info("User with token {} saved", user.getToken());
//    }

//    public RedisUserRole get(String token) {
//        return (RedisUserRole) hashOperations.get("USER", token);
//    }

    public RedisUserRole get(String token) {
        String userId = (String) hashOperations.get(token, "userId");
        List<String> roles = (List<String>) hashOperations.get(token, "roles");
        return RedisUserRole.of(userId, token, roles);
//        return (RedisUserRole) hashOperations.get("USER", token);
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
