package com.example.article.payload;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GetUsersRoleId {
    private Integer roleId;
    private UUID articleId;

}
