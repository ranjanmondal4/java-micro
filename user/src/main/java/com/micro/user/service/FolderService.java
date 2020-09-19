package com.micro.user.service;

import com.micro.user.configuration.AppUtils;
import com.micro.user.configuration.DataNotFoundException;
import com.micro.user.configuration.LocaleService;
import com.micro.user.configuration.MessageConstants;
import com.micro.user.domain.Document;
import com.micro.user.domain.Folder;
import com.micro.user.domain.user.User;
import com.micro.user.dto.folder.AddFolderDTO;
import com.micro.user.dto.folder.FolderDTO;
import com.micro.user.dto.folder.FolderDtoMapper;
import com.micro.user.repo.FolderRepo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class FolderService {
    @Autowired
    private FolderRepo folderRepo;
    @Autowired
    private LocaleService localeService;
    @Autowired
    private UserService userService;
    @Autowired
    private FolderDtoMapper folderDtoMapper;
    private static final Set<Document> EMPTY_LIST = new HashSet<>(0);

    public Folder addFolder(AddFolderDTO folderDTO, String userId){
        Folder parentFolder = folderRepo.findById(folderDTO.getParentFolderId())
                .orElseThrow(() -> DataNotFoundException.of(MessageConstants.DATA_NOT_FOUND));

        if(!parentFolder.getUser().getId().equalsIgnoreCase(userId))
            throw DataNotFoundException.of(MessageConstants.USER_NOT_FOUND);

        long folderExists = folderRepo.countByParentFolder_FolderIdAndNameAndUser_IdAndActiveTrue(
                folderDTO.getParentFolderId(), folderDTO.getName(), userId);
        if(folderExists > 0)
            throw DataNotFoundException.of(localeService.getMessage(MessageConstants.ALREADY_EXISTS, "Folder"));

        Folder folder = Folder.of(folderDTO, parentFolder.getUser(), parentFolder, true, true);
        return folderRepo.save(folder);
    }

    public Folder moveFolder(String folderId, String newParentFolderId, String userId) {
        Folder folder = folderRepo.findOneByFolderIdAndUser_IdAndActiveTrue(folderId, userId)
                .orElseThrow(() -> DataNotFoundException.of(localeService.getMessage(MessageConstants.DATA_NOT_FOUND,
                        "Folder")));

        if(folder.isMovable())
            throw DataNotFoundException.of(MessageConstants.OPERATION_NOT_ALLOWED);

        List<Folder> folders = null;
        if(!StringUtils.isBlank(newParentFolderId)){
            Folder parentFolder = folderRepo.findOneByFolderIdAndUser_IdAndActiveTrue(newParentFolderId, userId)
                    .orElseThrow(() -> DataNotFoundException.of(localeService.getMessage(MessageConstants.DATA_NOT_FOUND,
                            "Parent Folder")));

            folders = folderRepo.findAllByParentFolder_FolderIdAndUser_IdAndActiveTrue(newParentFolderId, userId);
            folder.setParentFolder(parentFolder);
        }else {
            folders = folderRepo.findAllByParentFolderIsNullAndUser_IdAndActiveTrue(userId);
            folder.setParentFolder(null);
        }

        List<String> foldersNames = folders.stream().map(ele -> ele.getName()).collect(Collectors.toList());
        foldersNames.stream().filter(name -> name.equals(folder.getName()) || name.startsWith(folder.getName()+"("))
                .collect(Collectors.toList());
        if(!foldersNames.isEmpty()) {
            int nextCount = AppUtils.nextCount(foldersNames);
            folder.setName(folder.getName() + " (" + nextCount + ")");
        }
        return folderRepo.save(folder);
    }

    public List<Folder> addBasicFolders(User user) {
        List<Folder> folders = getBasicFolders(user);
        return folderRepo.saveAll(folders);
    }

    public List<Folder> getBasicFolders(User user) {
        return Arrays.asList(Folder.of("Legal", "Legal Documents", user, false, false),
                Folder.of("Health", "Health Documents", user, false, false),
                Folder.of("Housing", "Housing Documents", user, false, false),
                Folder.of("Finance", "Finance Documents", user, false, false),
                Folder.of("Family", "Family Documents", user, false, false),
                Folder.of("Other", "Other Documents", user, false, false));
    }

    public List<Folder> getBasicFoldersByUser(User user) {
        return folderRepo.findAllByUserAndParentFolderIsNullAndActiveTrue(user);
    }

    public Folder deleteFolderByFolderIdAndUserId(String folderId, String userId){
        Folder folder = folderRepo.findOneByFolderIdAndUser_IdAndActiveTrue(folderId, userId).orElseThrow(() ->
                DataNotFoundException.of(localeService.getMessage(MessageConstants.DATA_NOT_FOUND, "Folder")));

        if(!folder.isDeletable())
            throw DataNotFoundException.of(localeService.getMessage(MessageConstants.OPERATION_NOT_ALLOWED));

        if(!folder.getDocuments().isEmpty())
            throw DataNotFoundException.of(localeService.getMessage(MessageConstants.FOLDER_NOT_EMPTY));

        long childFoldersCount = folderRepo.countByParentFolder_FolderIdAndUser_IdAndActiveTrue(folderId, userId);
        if(childFoldersCount > 0)
            throw DataNotFoundException.of(localeService.getMessage(MessageConstants.FOLDER_NOT_EMPTY));

        folder.setActive(false);
        return folderRepo.save(folder);
    }

    public FolderDTO getAllContentsByFolderIdAndUserId(String folderId, String userId) {
        Folder folder = folderRepo.findOneByFolderIdAndUser_IdAndActiveTrue(folderId, userId).orElseThrow(() ->
                DataNotFoundException.of(localeService.getMessage(MessageConstants.DATA_NOT_FOUND, "Folder")));


        List<Folder> childFolders = folderRepo.findAllByParentFolder_FolderIdAndUser_IdAndActiveTrue(folderId, userId);
        List<FolderDTO> childDTOs = childFolders.stream().map(child -> {
            FolderDTO childDTO = folderDtoMapper.toFolderDTO(child);
            childDTO.setNumberOfDocuments(childDTO.getDocuments().stream().filter(Document::isDeleted).count());
            childDTO.setDocuments(EMPTY_LIST);
            return childDTO;
        }).collect(Collectors.toList());

        FolderDTO folderDTO = folderDtoMapper.toFolderDTO(folder);
        folderDTO.setNumberOfDocuments(folderDTO.getDocuments().stream().filter(Document::isDeleted).count());
        folderDTO.setFolders(childDTOs);

        return folderDTO;
    }



    }
