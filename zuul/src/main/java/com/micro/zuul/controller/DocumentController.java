package com.micro.zuul.controller;

import com.micro.zuul.configuration.AppUtils;
import com.micro.zuul.configuration.ResponseUtils;
import com.micro.zuul.domain.RedisUserRole;
import com.micro.zuul.dto.ContactDto;
import com.micro.zuul.dto.document.AddFolderDTO;
import com.micro.zuul.dto.document.Folder;
import com.micro.zuul.service.UserFeignService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user")
public class DocumentController {

    @Autowired
    private UserFeignService userFeignService;

    @ApiOperation("Add folder")
    @PostMapping("/folder")
    ResponseEntity<Object> addFolder(@RequestBody AddFolderDTO folderDTO){
       RedisUserRole user = AppUtils.getLoggedInUser();
       return ResponseUtils.generate(userFeignService.addUserFolder(folderDTO, user.getUserId()));
    }

    @ApiOperation("Get basic folders")
    @PostMapping("/root-folders")
    public ResponseEntity<Object> getAllBasicFolders(){
        RedisUserRole user = AppUtils.getLoggedInUser();
        return ResponseUtils.generate(userFeignService.getBasicFolderByUser(user.getUserId()));
    }

    @ApiOperation("Move folder")
    @PutMapping("/folder/{folderId}/move")
    public ResponseEntity<Object> moveFolder(@PathVariable String folderId, @RequestParam String newParentFolderId){
        RedisUserRole user = AppUtils.getLoggedInUser();
        return ResponseUtils.generate(userFeignService.moveFolder(user.getUserId(), folderId, newParentFolderId));
    }

    @ApiOperation("Delete folder")
    @DeleteMapping("/folder/{folderId}/delete")
    public ResponseEntity<Object> deleteFolder(@PathVariable String folderId){
        RedisUserRole user = AppUtils.getLoggedInUser();
        return ResponseUtils.generate(userFeignService.deleteFolder(user.getUserId(), folderId));
    }

    @ApiOperation("Get folder details")
    @GetMapping("/folder/{folderId}")
    public ResponseEntity<Object> getFolderByFolderId(@PathVariable String folderId){
        RedisUserRole user = AppUtils.getLoggedInUser();
        return ResponseUtils.generate(userFeignService.getFolderByFolderId(user.getUserId(), folderId));
    }


}
