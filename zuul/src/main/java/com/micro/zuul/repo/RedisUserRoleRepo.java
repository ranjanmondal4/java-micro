package com.micro.zuul.repo;

import com.micro.zuul.domain.RedisUserRole;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RedisUserRoleRepo extends CrudRepository<RedisUserRole, String> {
    RedisUserRole findByToken(String token);
}
