package com.micro.user.dto.folder;

import com.micro.user.domain.Folder;
import org.mapstruct.Mapper;

@Mapper
public interface FolderDtoMapper {
    FolderDTO toFolderDTO(Folder folder);
//    Contact toContact(ContactDto contactDto);

}
