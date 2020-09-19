package com.micro.user.domain;

import com.micro.user.domain.user.User;
import com.micro.user.dto.folder.AddFolderDTO;
import com.mongodb.lang.NonNull;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
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
    @Indexed
    @DBRef
    private User user;
    @Indexed
    @DBRef
    private Folder parentFolder;
    private boolean active = true;
    @NonNull
    private boolean deletable;
    @NonNull
    private boolean movable;
    @NonNull
    private Set<Document> documents;

    public static Folder of(AddFolderDTO folderDTO, User user, Folder parentFolder, boolean deletable, boolean movable){
        Folder folder = of(folderDTO.getName(), folderDTO.getDescription(), user, deletable, movable);
        folder.parentFolder = parentFolder;
        return folder;
    }

    public static Folder of(String name, String description, User user, boolean deletable, boolean movable){
        Folder folder = new Folder();
        folder.name = name;
        folder.description = description;
        folder.user = user;
        folder.createdOn = LocalDate.now();
        folder.deletable = deletable;
        folder.movable = movable;
        folder.setDocuments(new HashSet<>(0));
        return folder;
    }
}
