package com.micro.user.service;

import com.micro.user.configuration.DataNotFoundException;
import com.micro.user.configuration.LocaleService;
import com.micro.user.configuration.MessageConstants;
import com.micro.user.domain.Folder;
import com.micro.user.domain.user.User;
import com.micro.user.dto.folder.AddFolderDTO;
import com.micro.user.repo.FolderRepo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Slf4j
@Service
public class FolderService {
    @Autowired
    private FolderRepo folderRepo;
    @Autowired
    private LocaleService localeService;
    @Autowired
    private UserService userService;

    public Folder addFolder(AddFolderDTO folderDTO, String userId){
        Folder parentFolder = folderRepo.findById(folderDTO.getParentFolderId())
                .orElseThrow(() -> DataNotFoundException.of(MessageConstants.DATA_NOT_FOUND));

        if(!parentFolder.getUser().getId().equalsIgnoreCase(userId))
            throw DataNotFoundException.of(MessageConstants.USER_NOT_FOUND);

        Folder folder = Folder.of(folderDTO, parentFolder.getUser(), parentFolder, true);
        return folderRepo.save(folder);
    }

    public Folder moveFolder(String folderId, String newParentFolderId, String userId) {
        Folder folder = folderRepo.findOneByFolderIdAndUser_IdAndActiveTrue(folderId, userId).orElseThrow(() ->
                DataNotFoundException.of(localeService.getMessage(MessageConstants.DATA_NOT_FOUND, "Folder")));

        if(!StringUtils.isBlank(newParentFolderId)){
            Folder parentFolder = folderRepo.findOneByFolderIdAndUser_IdAndActiveTrue(newParentFolderId, userId).orElseThrow(() ->
                    DataNotFoundException.of(localeService.getMessage(MessageConstants.DATA_NOT_FOUND, "Parent Folder")));
            folder.setParentFolder(parentFolder);
        }else {
            folder.setParentFolder(null);
        }

        return folderRepo.save(folder);
    }

    public List<Folder> addBasicFolders(User user) {
        List<Folder> folders = getBasicFolders(user);
        return folderRepo.saveAll(folders);
    }

    public List<Folder> getBasicFolders(User user) {
        return Arrays.asList(Folder.of("Legal", "Legal Documents", user, false),
                Folder.of("Health", "Health Documents", user, false),
                Folder.of("Housing", "Housing Documents", user, false),
                Folder.of("Finance", "Finance Documents", user, false),
                Folder.of("Family", "Family Documents", user, false),
                Folder.of("Other", "Other Documents", user, false));
    }

    public List<Folder> getBasicFoldersByUser(User user) {
        return folderRepo.findAllByUserAndParentFolderIsNullAndActiveTrue(user);
    }

    public Folder deleteFolderByFolderIdAndUserId(String folderId, String userId){
        Folder folder = folderRepo.findOneByFolderIdAndUser_IdAndActiveTrue(folderId, userId).orElseThrow(() ->
                DataNotFoundException.of(localeService.getMessage(MessageConstants.DATA_NOT_FOUND, "Folder")));

        if(!folder.isDeletable())
            throw DataNotFoundException.of(localeService.getMessage("Operation not allowed"));

        if(!folder.getDocuments().isEmpty())
            throw DataNotFoundException.of(localeService.getMessage("Folder is not empty"));

        long childFoldersCount = folderRepo.countByParentFolder_FolderIdAndUser_IdAndActiveTrue(folderId, userId);
        if(childFoldersCount > 0)
            throw DataNotFoundException.of(localeService.getMessage("Folder is not empty"));

        folder.setActive(false);
        return folderRepo.save(folder);
    }

}
