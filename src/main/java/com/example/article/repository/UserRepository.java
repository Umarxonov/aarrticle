package com.example.article.repository;

import com.example.article.entity.Role;
import com.example.article.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {

    Optional<User> findByPhoneNumber(String phoneNumber);

    List<User> findAllByEnabledTrueAndIdIn(Collection<UUID> id);

    User findAllByEnabledTrueAndId(UUID id);

    List<User> findAllByActiveTrueAndRolesIdAndCategoriesId(Integer roleId, Integer categories_id);

    @Query(value = "select ur.roles_id from users_roles ur where users_id=?1", nativeQuery = true)
    Integer findByUserId(UUID id);

    List<User> findAllByEnabledTrueAndRolesIdAndCategoriesId(Integer roleId, Integer categories_id);

//    List<User> findAllByRolesIdAndCategoriesId(Integer roles_id, Integer categories_id);

    Page<User> findAllByRolesIdAndEnabledAndFirstNameContainingIgnoringCaseOrLastNameContainingIgnoringCaseOrFatherNameContainingIgnoringCaseOrEmailContainingIgnoringCaseOrPhoneNumberContainingIgnoringCase(Integer roles_id, boolean enabled, String firstName, String lastName, String fatherName, String email, String phoneNumber, Pageable pageable);

    Page<User> findAllByEnabledAndFirstNameContainingIgnoringCaseOrLastNameContainingIgnoringCaseOrFatherNameContainingIgnoringCaseOrEmailContainingIgnoringCaseOrPhoneNumberContainingIgnoringCase(boolean enabled, String firstName, String lastName, String fatherName, String email, String phoneNumber, Pageable pageable);


}
