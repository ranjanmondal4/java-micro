package com.micro.user.repo;

import com.micro.user.domain.user.Contact;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ContactRepo extends MongoRepository<Contact, String> {

}
