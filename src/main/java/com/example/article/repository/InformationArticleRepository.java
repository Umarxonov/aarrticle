package com.example.article.repository;

import com.example.article.collaction.InformationArticleCollection;
import com.example.article.entity.InformationArticle;
import com.example.article.entity.enums.ArticleStatusName;
import com.example.article.entity.enums.Watdou;
import io.swagger.models.auth.In;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface InformationArticleRepository extends JpaRepository<InformationArticle, UUID> {

    InformationArticle findFirstByArticleIdOrderByCreatedAtDesc(UUID article_id);

    List<InformationArticle> findAllByArticleId(UUID article_id);

    Optional<InformationArticle> findByArticleId(UUID article_id);

//    InformationArticle findByArticleIdAndRedactorReviewerArticleStatusIdAndArticleStatusName(UUID article_id, UUID redactorReviewerArticleStatus_id, ArticleStatusName articleStatusName);

    boolean existsByArticleIdAndRedactorIdAndArticleStatusName(UUID article_id, UUID redactor_id, ArticleStatusName articleStatusName);

    InformationArticle findByArticleIdAndRedactorIdAndWatdou(UUID article_id, UUID redactor_id, Watdou watdou);

   InformationArticle findByArticleIdAndRedactorIdAndArticleStatusName(UUID article_id, UUID redactor_id,ArticleStatusName articleStatusName);




   List<InformationArticle> findAllByArticleIdAndArticleStatusName(UUID article_id, ArticleStatusName articleStatusName);

    List<InformationArticle> findAllByArticleIdAndMassageNotNullOrderByCreatedAt(UUID articleId);

    @Query(value = "select redactor_id   from information_article   where article_id=?1 and article_status_name=?2 ", nativeQuery = true)
    List <UUID> findAllByArticleIds(UUID articleId, String articleStatusName);




    @Query(value = "select created_at from  information_article where article_id=?1 and redactor_id=?2 and article_status_name=?3",nativeQuery = true)
    Timestamp findByReturnCreateAt(UUID articleId, UUID redactorId, ArticleStatusName articleStatusName);



//    @Query(value = "select * from editors_article e\n" +
//            "             inner join role r on r.role_name = ?2\n" +
//            "             inner join users_roles ur on ur.roles_id=r.id\n" +
//            "             inner join users_roles ur2 on ur.users_id=e.redactor_id\n" +
//            "            where e.article_id=?1",nativeQuery = true)
//    List<EditorsArticle> findAllByArticleIdAndRoleName(UUID articleId, String roleName);



@Query(value = "select  * from  information_article ia\n" +
        "    inner join editors_article ea  on ea.article_id=?1 and  ea.role_id=?2 and ia.redactor_id=ea.redactor_id\n" +
        "and  ia.article_status_name='I_ACCEPTED'   ",nativeQuery = true)
    List<InformationArticle> findAllByRedactorRol(UUID articleId, Integer roleId );

    List<InformationArticle> findAllByArticleIdAndRedactorIdAndArticleStatusNameOrArticleIdAndRedactorIdAndArticleStatusNameOrArticleIdAndRedactorIdAndArticleStatusName(UUID article_id, UUID redactor_id, ArticleStatusName articleStatusName, UUID article_id2, UUID redactor_id2, ArticleStatusName articleStatusName2, UUID article_id3, UUID redactor_id3, ArticleStatusName articleStatusName3);

    List<InformationArticle> findAllByArticleStatusName(ArticleStatusName statusName);

    List<InformationArticle> findAllByCreatedAtBetween(Timestamp createdAt, Timestamp createdAt2);

    List<InformationArticle> findAllByRedactorIdAndCreatedAtIsBetweenOrderByCreatedAtDesc(UUID redactor_id, Timestamp createdAt, Timestamp createdAt2);

    List<InformationArticle> findAllByChekUserIdAndWatdou(UUID chekUser_id, Watdou watdou);

    @Query(value = "select ia.article_id from information_article ia where ia.article_id = ?1 and ia.article_status_name=?2",nativeQuery = true)
    List<UUID> findAllByArticleIdSAndArticleStatusName(UUID articleId, String statusName);


    @Query(value = "select * from information_article  where article_id in ?1 and article_status_name = ?2 order by created_at desc", nativeQuery = true)
    List<InformationArticle> findAllByArticleIdAndArticleStatusNameOrderByCreatedAtDesc(List<UUID> article, String articleStatusName);

}
