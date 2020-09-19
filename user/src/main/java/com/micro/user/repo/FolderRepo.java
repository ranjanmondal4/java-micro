package com.micro.user.repo;

import com.micro.user.domain.Folder;
import com.micro.user.domain.user.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FolderRepo extends MongoRepository<Folder, String> {
    List<Folder> findAllByUserAndParentFolderIsNullAndActiveTrue(User user);
    Optional<Folder> findOneByFolderIdAndUser_IdAndActiveTrue(String folderId, String userId);

    long countByParentFolder_FolderIdAndUser_IdAndActiveTrue(String parentFolderId, String userId);

    long countByParentFolder_FolderIdAndNameAndUser_IdAndActiveTrue(String parentFolderId, String name, String userId);

   // {"parentFolder.$id": ObjectId("5f5e5adcc9437c0d6e58df7a")}
//    @Query("{'parentFolder.$id': ?0}")
    List<Folder> findAllByParentFolder_FolderIdAndUser_IdAndActiveTrue(String parentFolderId, String userId);
    List<Folder> findAllByParentFolderIsNullAndUser_IdAndActiveTrue(String userId);
}
