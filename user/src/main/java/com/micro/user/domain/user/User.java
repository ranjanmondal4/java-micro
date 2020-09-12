package com.micro.user.domain.user;

import com.micro.user.dto.UserRegisterDto;
import com.mongodb.lang.NonNull;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Document
@Getter
@Setter
@NoArgsConstructor
public class User {
    @Id
    private String id;
    private String firstName;
    private String lastName;
//    @NonNull
//    @Indexed(unique = true)
//    private String userName;
    @NonNull
    private String password;
    @Indexed(unique = true)
    private Email primaryEmail;
    private List<Email> secondaryEmails = new ArrayList<>();

    @NonNull
    private List<Role> roles;
    public enum Role {
        DIRECT_CLIENT,  DIRECT_AGENT, ADMIN, FIRM;
    }

    @Transient
    private String token;
    private List<PhoneNumber> phoneNumbers = new ArrayList<>();
    private LocalDateTime createdAt;
    @NonNull
    private UserState userState;
    public enum UserState {
        ACTIVE("Active"), NOT_VERIFIED("Not Verified"), SUSPENDED_TEMPORARY("Suspended Temporary"),
        DEACTIVATED("Deactivated By Admin");

        private String value;
        UserState(String value){
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }
    private List<ActiveHistory> activeHistories = new ArrayList<>();
    private List<String> accessRight;
    private List<AccessRightHistory> accessRights = new ArrayList<>();

    private User(String emailId, String password){
        Email email = Email.of(emailId);
        this.primaryEmail = email;
        this.password = password;
        this.userState = UserState.NOT_VERIFIED;
    }

    private User(String emailId, String password, Role role){
        Email email = Email.of(emailId);
        this.primaryEmail = email;
        this.password = password;
        this.roles = Arrays.asList(role);
        this.userState = UserState.NOT_VERIFIED;
    }

    public static User of(UserRegisterDto dto){
        return new User(dto.getEmail(), dto.getPassword());
    }

    public static User of(UserRegisterDto dto, Role role){
        return new User(dto.getEmail(), dto.getPassword(), role);
    }
}

