package com.micro.user.repo;

import com.micro.user.domain.Folder;
import com.micro.user.domain.user.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.BasicQuery;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class FolderRepoImpl {
    @Autowired
    private FolderRepo folderRepo;

    private static final String COLLECTION_NAME = "folder";
    @Autowired
    private MongoTemplate mongoTemplate;

    public Folder addFolder(Folder folder){
        return mongoTemplate.insert(folder, COLLECTION_NAME);
    }

    public List<Folder> addFolders(List<Folder> folders){
        return folderRepo.saveAll(folders);
    }

    public Optional<Folder> findByFolderId(String folderId) {
        return folderRepo.findById(folderId);
//        BasicQuery queryDoc = new BasicQuery("{folderId : " + folderId + "}");
//        return mongoTemplate.findOne(queryDoc, Folder.class);
    }

    public List<Folder> findAllFoldersByUserAndParentFolderIsNull(User user){
        return folderRepo.findAllByUserAndParentFolderIsNull(user);
    }
}
