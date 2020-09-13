package com.micro.zuul.dto;

import com.mongodb.lang.NonNull;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Data
public class ContactDto {
    private String id;
    @NotBlank
    private String firstName;
    private String lastName;
    private String primaryEmail;
    private List<String> phoneNumbers;
    @NonNull
    private String relation;
    @NonNull
    private boolean fiduciary;
}
