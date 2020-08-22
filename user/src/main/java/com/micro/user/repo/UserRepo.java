package com.micro.user.repo;

import com.micro.user.domain.user.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface UserRepo extends MongoRepository<User, String> {
    @Query("{'emails.mailId': ?0}")
    User findBySecondaryEmailId(String emailId);

    @Query("{'primaryEmail.mailId': ?0}")
    User findByPrimaryEmailId(String emailId);
}
