package com.micro.user.repo;

import com.micro.user.domain.Folder;
import com.micro.user.domain.user.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FolderRepo extends MongoRepository<Folder, String> {
    List<Folder> findAllByUserAndParentFolderIsNull(User user);
}
