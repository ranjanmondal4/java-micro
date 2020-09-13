package com.micro.zuul.dto.document;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class AddFolderDTO {
    @NotBlank
    private String name;
    @NotBlank
    private String description;
    @NotBlank
    private String parentFolderId;
}