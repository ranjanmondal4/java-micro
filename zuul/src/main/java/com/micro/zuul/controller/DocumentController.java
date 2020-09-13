package com.micro.zuul.controller;

import com.micro.zuul.configuration.AppUtils;
import com.micro.zuul.configuration.ResponseUtils;
import com.micro.zuul.domain.RedisUserRole;
import com.micro.zuul.dto.ContactDto;
import com.micro.zuul.dto.document.AddFolderDTO;
import com.micro.zuul.service.UserFeignService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/user")
public class DocumentController {

    @Autowired
    private UserFeignService userFeignService;

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
}
