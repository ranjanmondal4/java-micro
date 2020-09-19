package com.micro.user.controller;

import com.micro.user.configuration.LocaleService;
import com.micro.user.configuration.MessageConstants;
import com.micro.user.configuration.ResponseUtils;
import com.micro.user.domain.Folder;
import com.micro.user.domain.user.User;
import com.micro.user.dto.folder.AddFolderDTO;
import com.micro.user.dto.folder.FolderDTO;
import com.micro.user.service.FolderService;
import com.micro.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api/v1/user")
public class FolderController {
    @Autowired
    private FolderService folderService;
    @Autowired
    private LocaleService localeService;
    @Autowired
    private UserService userService;

    @PostMapping("/{userId}/folder")
    public ResponseUtils.Response<Folder> addFolder(@Valid @RequestBody AddFolderDTO folderDTO, @PathVariable String userId){
       Folder folder = folderService.addFolder(folderDTO, userId);
       boolean folderCreated = !Objects.isNull(folder);
       return ResponseUtils.generateResponse(folderCreated, folder, folderCreated ?
                localeService.getMessage(MessageConstants.SAVED_SUCCESSFULLY) : localeService.getMessage(MessageConstants.SOMETHING_WENT_WRONG));

    }

    @GetMapping("/{userId}/folder")
    public ResponseUtils.Response<List<Folder>> getBasicFolderByUser(@PathVariable String userId) {
        User user = userService.findUserById(userId);
        List<Folder> folders = folderService.getBasicFoldersByUser(user);
        return ResponseUtils.generateResponse(true, folders, localeService.getMessage(MessageConstants.DATA_FETCHED_SUCCESSFULLY));
    }

    @PutMapping("/{userId}/folder/{folderId}/move")
    public ResponseUtils.Response<Folder> moveFolder(@RequestParam String newParentFolderId, @PathVariable String userId,
                                                     @PathVariable String folderId){
        Folder folder = folderService.moveFolder(folderId, newParentFolderId, userId);
        boolean folderMoved = !Objects.isNull(folder);
        return ResponseUtils.generateResponse(folderMoved, folder, folderMoved ?
                localeService.getMessage(MessageConstants.SAVED_SUCCESSFULLY) : localeService.getMessage(MessageConstants.SOMETHING_WENT_WRONG));

    }

    @DeleteMapping("/{userId}/folder/{folderId}/delete")
    public ResponseUtils.Response<Folder> moveFolder(@PathVariable String userId, @PathVariable String folderId){
        Folder folder = folderService.deleteFolderByFolderIdAndUserId(folderId, userId);
        boolean folderDeleted = !Objects.isNull(folder);
        return ResponseUtils.generateResponse(folderDeleted, folder, folderDeleted ?
                localeService.getMessage(MessageConstants.SAVED_SUCCESSFULLY) : localeService.getMessage(MessageConstants.SOMETHING_WENT_WRONG));

    }

    @GetMapping("/{userId}/folder/{folderId}")
    public ResponseUtils.Response<FolderDTO> getFolderByFolderId(@PathVariable String userId, @PathVariable String folderId) {
        FolderDTO folderDTO = folderService.getAllContentsByFolderIdAndUserId(folderId, userId);
        return ResponseUtils.generateResponse(true, folderDTO, localeService.getMessage(MessageConstants.DATA_FETCHED_SUCCESSFULLY));
    }
}
