package com.micro.zuul.dto.document;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
public class Folder {
    private String folderId;
    private String name;
    private String description;
    private LocalDate createdOn;
    private boolean deleted;
    private Folder parentFolder;
    private boolean deletable;
    private boolean movable;
}