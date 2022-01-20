package com.example.article.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Role implements GrantedAuthority {
    @Id
    @GeneratedValue
    private Integer id;

    private String roleName;

    @Override
    public String getAuthority() {
        return roleName;
    }

//
//    public Role(String roleName, Set<Permission> permissions) {
//        this.roleName = roleName;
//        this.permissions=permissions;
//    }




}
