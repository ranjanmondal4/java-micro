package com.micro.zuul.dto.document;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
public class FolderDTO {

    private String folderId;
    private String name;
    private String description;

    private boolean deletable;
    private boolean movable;
    private Set<Document> documents;
    private List<FolderDTO> folders;
    private long numberOfDocuments;
}

