package com.micro.user.domain;

import com.mongodb.lang.NonNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
public class Document {
    @NonNull
    private String name;
    @NonNull
    private String type;
    @NonNull
    private String url;
    @NonNull
    private LocalDate createdOn;
    @NonNull
    private boolean deleted;
    @NonNull
    private LocalDate updatedOn;
}
