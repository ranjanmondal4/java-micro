package com.micro.user.controller;

import com.micro.user.configuration.LocaleService;
import com.micro.user.configuration.MessageConstants;
import com.micro.user.configuration.ResponseUtils;
import com.micro.user.domain.user.Contact;
import com.micro.user.domain.user.User;
import com.micro.user.dto.ContactDto;
import com.micro.user.dto.ContactDtoMapper;
import com.micro.user.dto.LoginDto;
import com.micro.user.dto.UserDto;
import com.micro.user.service.ContactService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1")
@Slf4j
public class ContactController {
    @Autowired
    private ContactDtoMapper contactDtoMapper;
    @Autowired
    private LocaleService localeService;
    @Autowired
    private ContactService contactService;

    @PostMapping("/user/contact")
    public ResponseUtils.Response<ContactDto> addContact(@Valid @RequestBody ContactDto dto, @RequestParam String userId){
        Contact contact = contactService.addContact(dto, userId);
        dto = contactDtoMapper.toContactDto(contact);
        return ResponseUtils.generateResponse(true, dto, localeService.getMessage(MessageConstants.SAVED_SUCCESSFULLY));
    }

}
