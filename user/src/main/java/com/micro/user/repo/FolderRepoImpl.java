package com.micro.user.repo;

import com.micro.user.domain.Folder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.BasicQuery;
import org.springframework.stereotype.Service;

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

    public Folder findByFolderId(String folderId){
        log.info(":: comes hers");

        BasicQuery queryDoc = new BasicQuery("{folderId : " + folderId + "}");
        log.info(":: comes hers 2");
        return mongoTemplate.findOne(queryDoc, Folder.class);
    }
}
