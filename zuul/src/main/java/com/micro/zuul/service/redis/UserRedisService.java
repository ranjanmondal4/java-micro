package com.micro.zuul.service.redis;

import com.micro.zuul.domain.RedisUserRole;
import com.micro.zuul.repo.RedisUserRoleRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserRedisService {
    @Autowired
    RedisUserRoleRepo redisUserRoleRepo;

    public boolean addUserDetails(RedisUserRole userRole){
        return redisUserRoleRepo.save(userRole) != null;
    }
}
