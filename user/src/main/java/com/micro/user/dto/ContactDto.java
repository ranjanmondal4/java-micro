package com.micro.user.dto;

import com.micro.user.domain.user.Contact;
import com.micro.user.domain.user.Email;
import com.micro.user.domain.user.PhoneNumber;
import com.mongodb.lang.NonNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Data
@NoArgsConstructor
public class ContactDto {
    private String id;
    @NotBlank
    private String firstName;
    private String lastName;
    private String primaryEmail;
    private List<String> phoneNumbers;
    @NonNull
    private Contact.Relation relation;
    @NonNull
    private boolean fiduciary;
}
