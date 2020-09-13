package com.micro.user.domain.user;

import com.mongodb.lang.NonNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Document
@Data
@NoArgsConstructor
public class Contact {
    @Id
    private String id;
    @NonNull
    @Indexed
    private String userId;
    @NonNull
    private String firstName;
    private String lastName;
    private String primaryEmail;
    private List<String> phoneNumbers;
    @NonNull
    private Relation relation;
    public enum Relation {
        WIFE, HUSBAND, MOTHER, FATHER, ATTORNEY, DOCTOR;
    }
    private LocalDateTime createdAt = LocalDateTime.now();
    private boolean fiduciary;
}
