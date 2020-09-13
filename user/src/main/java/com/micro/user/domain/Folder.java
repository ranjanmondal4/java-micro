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
    @DBRef
    private User user;
    @Indexed
    @DBRef
    private Folder parentFolder;
    @NonNull
    private boolean deletable;
    @NonNull
    private boolean movable;

    private Set<Document> documents;

    public static Folder of(AddFolderDTO folderDTO, User user, Folder parentFolder){
        Folder folder = of(folderDTO.getName(), folderDTO.getDescription(), user);
        folder.parentFolder = parentFolder;
        return folder;
    }

    public static Folder of(String name, String description, User user){
        Folder folder = new Folder();
        folder.name = name;
        folder.description = description;
        folder.user = user;
        folder.createdOn = LocalDate.now();
        folder.deleted = false;
        folder.deletable = false;
        folder.movable = false;
        return folder;
    }
}
