package com.micro.zuul.service;

import com.micro.zuul.configuration.ResponseUtils;
import com.micro.zuul.dto.ContactDto;
import com.micro.zuul.dto.LoginDto;
import com.micro.zuul.dto.UserDto;
import com.micro.zuul.dto.UserRegisterDto;
import com.micro.zuul.dto.document.AddFolderDTO;
import com.micro.zuul.dto.document.Folder;
import com.micro.zuul.dto.document.FolderDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(value = "user-server")
public interface UserFeignService {

    @RequestMapping("/api/v1/user/login")
    ResponseUtils.Response<UserDto> login(@RequestBody LoginDto loginDto);


    @GetMapping("/api/v1/user/{userId}")
    ResponseUtils.Response<UserDto> getUserDetails(@PathVariable("userId") String userId);

    @PostMapping("/api/v1/user/register")
    ResponseUtils.Response<UserDto> register(@RequestBody UserRegisterDto dto);

    @PostMapping("/api/v1/user/{userId}/folder")
    ResponseUtils.Response<Folder> addUserFolder(@RequestBody AddFolderDTO dto, @PathVariable String userId);

    @GetMapping("/api/v1/user/contact")
    ResponseUtils.Response<ContactDto> addContact(@RequestBody ContactDto dto, @RequestParam String userId);

    @GetMapping("/api/v1/user/{userId}/folder")
    ResponseUtils.Response<List<Folder>> getBasicFolderByUser(@PathVariable String userId);

    @PutMapping("/api/v1/user/{userId}/folder/{folderId}/move")
    ResponseUtils.Response<Folder> moveFolder(@PathVariable String userId, @PathVariable String folderId,
                                              @RequestParam String newParentFolderId);

    @DeleteMapping("/api/v1/user/{userId}/folder/{folderId}/delete")
    ResponseUtils.Response<Folder> deleteFolder(@PathVariable String userId, @PathVariable String folderId);

    @GetMapping("/api/v1/user/{userId}/folder/{folderId}")
    ResponseUtils.Response<FolderDTO> getFolderByFolderId(@PathVariable String userId, @PathVariable String folderId);
//
//    @PostMapping("/api/v1/admin/register")
//    User registerAdmin(@RequestBody UserRegisterDto dto);
//
//    @RequestMapping("/api/v1/admin/login")
//    User loginAdmin(@RequestBody LoginDto loginDto);
//
//    @GetMapping("/api/v1/admin/{adminId}")
//    User getAdminDetails(@PathVariable("adminId") String adminId);
}

