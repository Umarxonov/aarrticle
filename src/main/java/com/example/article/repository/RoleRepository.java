package com.example.article.repository;

import com.example.article.entity.Role;
import com.example.article.entity.enums.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

public interface RoleRepository extends JpaRepository<Role, Integer> {


    //    Role findByRoleName(RoleName roleName);
    List<Role> findAllByRoleName(RoleName roleName);

    List<Role> findAllByRoleNameIn(Collection<String> roleName);

    List<Role> findAllByIdIn(Collection<Integer> id);




//
//    List<Role>findAllByRoleNameIn();
//
//
//    Role findByRoleName(String name);
}
