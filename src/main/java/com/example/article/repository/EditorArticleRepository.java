package com.example.article.repository;

import com.example.article.entity.EditorsArticle;
import com.example.article.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface EditorArticleRepository extends JpaRepository<EditorsArticle, UUID> {
    @Transactional
    @Modifying
    void deleteByArticleIdAndRedactorId(UUID article_id, UUID redactor_id);


    Optional<EditorsArticle> findByArticleIdAndRedactorId(UUID article_id, UUID redactor_id);


    List<EditorsArticle> findAllByArticleIdAndRoleIdAndRedactorIdIn(UUID article_id, Integer roleId, List<UUID> redactor_id);


    List<EditorsArticle>findAllByArticleIdAndRoleId(UUID article_id, Integer roleId);






    @Query(value = "select ed.redactor_id from editors_article  ed where ed.article_id=?1 ", nativeQuery = true)
    List<UUID> findAllByArticleId(UUID articleId);




//@Query(value = "select * from  editors_article e where e.article_id=?1 and e.redactor_id=(select u.users_id from  users_roles u where  u.roles_id=(select r.id from role r where r.role_name=?2 limit 1))", nativeQuery = true)
//List<EditorsArticle> findAllByArticleIdAndRoleName(UUID articleId, String roleName);


//    @Query(value = "select * from editors_article e\n" +
//            "             inner join role r on r.role_name = ?2\n" +
//            "             inner join users_roles ur on ur.roles_id=r.id\n" +
//            "             inner join users_roles ur2 on ur.users_id=e.redactor_id\n" +
//            "            where e.article_id=?1",nativeQuery = true)
//    List<EditorsArticle> findAllByArticleIdAndRoleName(UUID articleId, String roleName);


}
