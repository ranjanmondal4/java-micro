package com.micro.user.service;

import com.micro.user.configuration.DataNotFoundException;
import com.micro.user.configuration.LocaleService;
import com.micro.user.domain.Folder;
import com.micro.user.dto.folder.AddFolderDTO;
import com.micro.user.repo.FolderRepoImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Slf4j
@Service
public class FolderService {
    @Autowired
    private FolderRepoImpl folderRepo;

    @Autowired
    private LocaleService localeService;

    public Folder addFolder(AddFolderDTO folderDTO, String userId){

        Folder parentFolder = folderRepo.findByFolderId(folderDTO.getParentId());
        log.info(":: comes hers 3");
        if(Objects.isNull(parentFolder))
            throw DataNotFoundException.of(localeService.getMessage("error.occured"));

        Folder folder = Folder.of(folderDTO, userId, parentFolder.getFolderId());
        return folderRepo.addFolder(folder);
    }

}
