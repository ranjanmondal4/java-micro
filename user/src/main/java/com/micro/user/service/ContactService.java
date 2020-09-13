package com.micro.user.service;

import com.micro.user.domain.user.Contact;
import com.micro.user.domain.user.User;
import com.micro.user.dto.ContactDto;
import com.micro.user.dto.ContactDtoMapper;
import com.micro.user.repo.ContactRepoImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ContactService {

    @Autowired
    private ContactRepoImpl contactRepo;
    @Autowired
    private UserService userService;
    @Autowired
    private ContactDtoMapper contactDtoMapper;

    public Contact addContact(ContactDto dto, String userId){
        userService.findUserById(userId);
        Contact contact = contactDtoMapper.toContact(dto);
        contact.setUserId(userId);
        return contactRepo.addContact(contact);
    }
}
