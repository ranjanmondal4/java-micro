package com.micro.user.service;

import com.micro.user.configuration.DataNotFoundException;
import com.micro.user.configuration.LocaleService;
import com.micro.user.configuration.MessageConstants;
import com.micro.user.domain.Folder;
import com.micro.user.domain.user.User;
import com.micro.user.dto.folder.AddFolderDTO;
import com.micro.user.repo.FolderRepoImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
public class FolderService {
    @Autowired
    private FolderRepoImpl folderRepo;
    @Autowired
    private LocaleService localeService;
    @Autowired
    private UserService userService;

    public Folder addFolder(AddFolderDTO folderDTO, String userId){
        Folder parentFolder = folderRepo.findByFolderId(folderDTO.getParentFolderId())
                .orElseThrow(() -> DataNotFoundException.of(MessageConstants.DATA_NOT_FOUND));

        log.error("{}, {}", parentFolder.getUser().getId(), userId);
        log.error("{}", parentFolder.getUser().getId().equalsIgnoreCase(userId));
        if(!parentFolder.getUser().getId().equalsIgnoreCase(userId))
            throw DataNotFoundException.of(MessageConstants.USER_NOT_FOUND);

        Folder folder = Folder.of(folderDTO, parentFolder.getUser(), parentFolder);
        return folderRepo.addFolder(folder);
    }

    public List<Folder> addBasicFolders(User user) {
        List<Folder> folders = getBasicFolders(user);
        return folderRepo.addFolders(folders);
    }

    public List<Folder> getBasicFolders(User user) {
        return Arrays.asList(Folder.of("Legal", "Legal Documents", user),
                Folder.of("Health", "Health Documents", user),
                Folder.of("Housing", "Housing Documents", user),
                Folder.of("Finance", "Finance Documents", user),
                Folder.of("Family", "Family Documents", user),
                Folder.of("Other", "Other Documents", user));
    }

    public List<Folder> getBasicFoldersByUser(User user) {
        return folderRepo.findAllFoldersByUserAndParentFolderIsNull(user);
    }

}
