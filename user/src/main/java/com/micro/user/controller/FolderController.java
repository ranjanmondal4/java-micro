package com.micro.user.controller;

import com.micro.user.configuration.LocaleService;
import com.micro.user.configuration.ResponseUtils;
import com.micro.user.domain.Folder;
import com.micro.user.dto.folder.AddFolderDTO;
import com.micro.user.service.FolderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Objects;

@RestController
@RequestMapping("/api/v1/user/folder")
public class FolderController {
    @Autowired
    private FolderService folderService;

    @Autowired
    private LocaleService localeService;

    @PostMapping()
    public ResponseEntity<Object> addFolder(@Valid @RequestBody AddFolderDTO folderDTO, @RequestParam String userId){

       Folder folder = folderService.addFolder(folderDTO, userId);
       if(Objects.isNull(folder)){
           return ResponseUtils.generate(false, null, localeService.getMessage("error.occured"));
       }else {
           return ResponseUtils.generate(true, folder, localeService.getMessage("data.found"));
       }
    }

}
