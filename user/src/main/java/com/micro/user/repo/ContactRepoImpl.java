package com.micro.user.repo;

import com.micro.user.domain.user.Contact;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ContactRepoImpl {
    @Autowired
    private ContactRepo contactRepo;

    public Contact addContact(Contact contact){
        return contactRepo.save(contact);
    }
}
