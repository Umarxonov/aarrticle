package com.example.article.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

    private UUID id;
    private String lastName;
    private String phoneNumber;
    private String email;
    private String password;
    private List<Integer> role;
    private boolean active;
    private UUID photoId;
    private List<Integer> categoryId;

}
