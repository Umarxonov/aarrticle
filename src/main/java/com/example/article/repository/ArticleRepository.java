package com.example.article.repository;

import com.example.article.entity.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface ArticleRepository extends JpaRepository<Article, UUID> {
    List<Article> findAllByCreatedBy(UUID id);


    Page<Article> findByPayTrue(Pageable simplePageable);


//    Page<Article>findAllByChecked(String checked, Pageable pageable);

    Page<Article> findByActiveTrue(Pageable simplePageable);



List<Article>findAllByPayTrueAndConfirmFalse();

    Article findByConfirmTrueAndId(UUID id);


    @Query(value = "select  * from  article a  inner join information_article ia on ia.watdou='CONFIRM' and ia.chek_user_id=?1 and a.id=ia.article_id  and a.article_status_name=?2 ",nativeQuery = true)
    List<Article> findAllByConfirmTrueAndAdmin(UUID id, String articleStatusName);





}
