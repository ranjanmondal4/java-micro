package com.micro.user.domain;

import com.micro.user.dto.folder.AddFolderDTO;
import com.mongodb.lang.NonNull;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.Set;

@Document
@Data
@ToString(exclude = {"documents"})
@NoArgsConstructor
public class Folder {
    @Id
    private String folderId;
    @NonNull
    private String name;
    private String description;
    @NonNull
    private LocalDate createdOn;
    @NonNull
    private boolean deleted;
    @Indexed
    private String userId;
    @Indexed
    private String parentFolderId;
    @NonNull
    private boolean deletable;
    @NonNull
    private boolean movable;

    private Set<Document> documents;

    public static Folder of(AddFolderDTO folderDTO, String userId, String parentFolderId){
        Folder folder = new Folder();
        folder.name = folderDTO.getName();
        folder.description = folderDTO.getDescription();
        LocalDate now = LocalDate.now();
        folder.createdOn = now;
        folder.deleted = false;
        folder.userId = userId;
        folder.deletable = false;
        folder.movable = false;
        folder.parentFolderId = parentFolderId;
        return folder;
    }
}
