package com.micro.user.dto;

import com.micro.user.domain.user.Contact;
import org.mapstruct.Mapper;

@Mapper
public interface ContactDtoMapper {
    ContactDto toContactDto(Contact contact);
    Contact toContact(ContactDto contactDto);
}
