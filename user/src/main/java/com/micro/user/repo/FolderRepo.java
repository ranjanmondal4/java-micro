package com.micro.user.repo;

import com.micro.user.domain.Folder;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FolderRepo extends MongoRepository<Folder, String> {
}
