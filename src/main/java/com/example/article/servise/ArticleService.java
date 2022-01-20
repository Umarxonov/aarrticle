package com.example.article.servise;

import com.example.article.entity.*;
import com.example.article.entity.enums.ArticleStatusName;
import com.example.article.entity.enums.RoleName;
import com.example.article.entity.enums.Watdou;
import com.example.article.payload.*;
import com.example.article.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.sql.Timestamp;
import java.util.*;

@Service
public class ArticleService {
    @Autowired
    ArticleRepository articleRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    AttachmentRepository attachmentRepository;

    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;


    @Autowired
    EditorArticleRepository editorArticleRepository;


    @Autowired
    InformationArticleRepository informationArticleRepository;


    public ApiResponse addArticle(ArticleDto articleDto, User user) {

//        if (articleDto.getId() != null) {
//            Optional<Article> byId = articleRepository.findById(articleDto.getId());
//            if (byId.isPresent()) {
//                article = byId.get();
//                if (article.isConfirm() && article.isRejected() == false) {
//                    return new ApiResponse("o'zgartirish mumkin emas bu tasdiqlangan ", false);
//                }
////                if (!article.getAdmin().getCreatedBy().equals(user.getId())) {
////                    return new ApiResponse("bu ma'qola sizniki emas", false);
////
////                }
//                return new ApiResponse(" Edit article ", true);
//            } else {
//                return new ApiResponse(" sizniki emas", false);
//            }
//        }
        Article article = new Article();
        Optional<Category> category = categoryRepository.findById(articleDto.getCategoryId());
        if (category.isPresent()) {
            article.setAuthor(articleDto.getAuthor());
            article.setFirstName(articleDto.getFirstName());
            article.setLastName(articleDto.getLastName());
            article.setDescription(articleDto.getDescription());
            article.setTitleArticle(articleDto.getTitleArticle());
            article.setCategory(category.get());
            article.setAdmin(user);
//            article.setPrice();
            article.setFile(attachmentRepository.getById(articleDto.getFileId()));
            articleRepository.save(article);
            return new ApiResponse("Saved", true);
        }
        return new ApiResponse("This subject not found", false);
    }


    //    public ApiResponse editArticle(ArticleDto articleDto, User user ){
//        Optional<Article> optionalArticle = articleRepository.findById(articleDto.getId());
//        if (optionalArticle.isEmpty())
//            return new ApiResponse("Article not found");
//
//        if (article.isConfirm() && article.isRejected() == false) {
////                    return new ApiResponse("o'zgartirish mumkin emas bu tasdiqlangan ", false);
////                }
//
//
//        return new ApiResponse();
//    }
//
//
    public ApiResponse getMyArticle(User user) {
        UUID id = user.getId();
        return new ApiResponse("all", true, articleRepository.findAllByCreatedBy(id));

    }

    public ApiResponse deleteArticle(UUID id) {
        articleRepository.deleteById(id);
        return new ApiResponse("delete", true);

    }

    //    public List<Article> newMyArticle(User user) {
//
//        return articleRepository.findAllByConfirmTrueAndAdmin(user.getId());
//
//    }
//




    public void sendMessage(User user, PushNotificationRequest pushNotificationRequest) {


    }


    //// bu articli redactorsAndReviewer larga biriktiradi va notifikatsiya cho'natadi
    public ApiResponse articleAddEditors(User user, NotificationForRedacktors redacktors) {


        List<User> allByIdIn = userRepository.findAllByEnabledTrueAndIdIn(redacktors.getRedactorsAndReviewer());

        Article byId = articleRepository.getById(
                redacktors.getArticle());
//        if (user.getId().equals(informationArticleRepository.findFirstByArticleIdOrderByCreatedAtDesc(byId.getId()).getChekUser().getId())){

        for (User user1 : allByIdIn) {

//            editorArticleRepository.save(new EditorsArticle(user, user1, byId, user1.getRoles()));

            sendMessage(user1, new PushNotificationRequest());

        }

        return new ApiResponse("Reviewer And Redactors", true);
//        }
//        return new ApiResponse("Bu maqolani siz tasdiqlamagansiz va buni redaktirlashga bera olmaysiz", true );

    }

    public ApiResponse removeEditor(User user, NotificationForRedacktors notification) {

        List<User> allByIdIn = userRepository.findAllByEnabledTrueAndIdIn(notification.getRedactorsAndReviewer());

        Article byId = articleRepository.getById(notification.getArticle());
//        if (user.getId().equals(informationArticleRepository.findFirstByArticleIdOrderByCreatedAtDesc(byId.getId()).getChekUser().getId())){

        for (User user1 : allByIdIn) {

            editorArticleRepository.deleteByArticleIdAndRedactorId(byId.getId(), user1.getId());

            sendMessage(user1, new PushNotificationRequest());

        }

        return new ApiResponse("Reviewer And Redactors", true);
    }


    // bu bittalab userlarni articlga briktiradi
    public ApiResponse addAndRemoveRedactor(User user, AddRedactorDto addRedactorDto) {

        Article article = articleRepository.getById(addRedactorDto.getArticle());
        User userId = userRepository.findAllByEnabledTrueAndId(addRedactorDto.getRedactorsAndReviewer());

        Optional<EditorsArticle> editorsArticle = editorArticleRepository.findByArticleIdAndRedactorId(addRedactorDto.getArticle(), addRedactorDto
                .getRedactorsAndReviewer());
//        if (user.getId()
//                .equals(informationArticleRepository.findFirstByArticleIdOrderByCreatedAtDesc(article.getId())
//                                                    .getChekUser()
//                                                    .getId())) {
        if (!addRedactorDto.isAddAndRemove()) {
            informationArticleRepository.save(new InformationArticle(user, userId, article, new Date(), addRedactorDto
                    .isAddAndRemove() ? Watdou.ADD : Watdou.DELETE));
            editorArticleRepository.deleteByArticleIdAndRedactorId(article.getId(), userId.getId());
            return new ApiResponse("Articledan shu  user o'chirildi");
        }
        if (!editorsArticle.isPresent()) {
            if (addRedactorDto.isAddAndRemove()) {
                Integer roleId = userRepository.findByUserId(userId.getId());
                editorArticleRepository.save(new EditorsArticle(user, userId, article, roleId));
                informationArticleRepository.save(new InformationArticle(user, userId, article, new Date(), addRedactorDto
                        .isAddAndRemove() ? Watdou.ADD : Watdou.DELETE));


                return new ApiResponse("Articlga user biriktirildi", true);
            }
        }
        return new ApiResponse("bu user oldin biriktirilgan   ", false);


//        }
//
//        return new ApiResponse("sizga mumkin emas ", false);
    }


    // articlni qabul qilish yo qilmaslik
    public ApiResponse reviewerAcceptTheArticle(User user, ReviewerAndRedactorResponseDto redactorResponseDto) {
        boolean exists = informationArticleRepository.existsByArticleIdAndRedactorIdAndArticleStatusName(redactorResponseDto
                .getArticleId(), user
                .getId(), ArticleStatusName.I_ACCEPTED);
        if (exists) {
            return new ApiResponse("Siz bu article ni avval qabul qilgansiz", false);
        }
        InformationArticle information = informationArticleRepository.findByArticleIdAndRedactorIdAndWatdou(redactorResponseDto
                .getArticleId(), user
                .getId(), Watdou.ADD);
        long deadline = information.getDeadline() + System.currentTimeMillis();

        String roleAdmin = "";

        for (Role role1 : information.getChekUser().getRoles()) {
            roleAdmin = role1.getRoleName();
        }

        if (information == null) {
            return new ApiResponse("bu article sizga biriktirilmagan ");
        }
        Article article = articleRepository.getById(information.getArticle().getId());
        if (redactorResponseDto.getArticleStatus().equalsIgnoreCase(ArticleStatusName.I_ACCEPTED.name())) {

            String role = "";
            for (Role roles : user.getRoles()) {
                role = roles.getRoleName();

            }
            if (role.equals(RoleName.ROLE_REVIEWER.name())) {

                article.setArticleStatusName(ArticleStatusName.BEGIN_CHECK);
                articleRepository.save(article);
            } else if (role.equals(RoleName.ROLE_REDACTOR.name())) {

                article.setArticleStatusName(ArticleStatusName.PREPARING_FOR_PUBLICATION);
                articleRepository.save(article);
            }
            informationArticleRepository.save(new InformationArticle(user, article, new Date(), ArticleStatusName.I_ACCEPTED,
                    roleAdmin + ": " + information.getChekUser()
                            .getLastName() + " " + information
                            .getChekUser()
                            .getFirstName() + " tomonidan " + user.getLastName() + " " + user
                            .getFirstName() + "ga taqrizlash vazifasi berildi"));
            return new ApiResponse("Qabul qilindi  ");

        } else if (redactorResponseDto.getArticleStatus().equals(ArticleStatusName.I_DID_NOT_ACCEPT.name())) {

            informationArticleRepository.save(new InformationArticle(user, article, new Date(), ArticleStatusName.I_DID_NOT_ACCEPT));
            return new ApiResponse("Ok mayli bu ishing yaxshi emas sani reytinging tushib ketadi");
        }
        return new ApiResponse("eeeeeeeeeeeee ");
    }


    // redactor bn reviewrlarga message  va dedline jonatish
    public ApiResponse sendMassageDeadlineRedactorReviewer(User user, AddRedactorDto addRedactorDto) {

        List<InformationArticle> informationArticles = informationArticleRepository.findAllByRedactorRol(addRedactorDto.getArticle(), addRedactorDto
                .getRole());

        for (InformationArticle information : informationArticles) {
            information.setDeadline((24 * 60 * 60 * 1000 * addRedactorDto.getDeadline()) + information.getCreatedAt().getTime());
            information.setMassage(addRedactorDto.getMassage());
            informationArticleRepository.save(information);



        }


        return new ApiResponse("habar yuborildi", true);
    }


    // Articlaga redactor yo reviewrlar tomondan beriladigon statuslar
// Articlaga redactor yo reviewrlar tomondan beriladigon statuslar
    public ApiResponse statusesGivenToTheArticleByTheEditors(User user, ReviewerAndRedactorResponseDto redactorResponseDto) {
        InformationArticle informationArticle = informationArticleRepository.findByArticleIdAndRedactorIdAndArticleStatusName(redactorResponseDto
                .getArticleId(), user
                .getId(), ArticleStatusName.I_ACCEPTED);
        Article article = articleRepository.getById(informationArticle.getArticle().getId());

        if (informationArticle.getDeadline() == System.currentTimeMillis() || informationArticle.getDeadline() <= System
                .currentTimeMillis()) {

            return new ApiResponse("sizga berilgan vaqt tugati ", false);
        } else {

            if (redactorResponseDto.getArticleStatus().equalsIgnoreCase(ArticleStatusName.CHECK_AND_ACCEPT.name())) {

                informationArticleRepository.save(new InformationArticle(user, article, new Date(), ArticleStatusName.CHECK_AND_ACCEPT));

                return new ApiResponse("siz maqolani tasdiqladiz ");

            } else if (redactorResponseDto.getArticleStatus()
                    .equalsIgnoreCase(ArticleStatusName.CHECK_AND_CANCEL.name())) {

                informationArticleRepository.save(new InformationArticle(user, article, new Date(), ArticleStatusName.CHECK_AND_CANCEL));

                return new ApiResponse("siz maqolani tasdiqlamadiz  radetildi ");
            } else if (redactorResponseDto.getArticleStatus()
                    .equalsIgnoreCase(ArticleStatusName.CHECK_AND_RECYCLE.name())) {

                informationArticleRepository.save(new InformationArticle(user, article, new Date(), ArticleStatusName.CHECK_AND_RECYCLE));

                return new ApiResponse("siz maqolani tasdiqlamadiz  ");
            }


        }


        return new ApiResponse("ok", true);
    }

    /**
     * Taqsimlangan maqoloalar
     *
     * @param user
     * @return
     */
    public List<Article> getDistributeds(User user) {
        List<Article> articleList = new ArrayList<>();
        List<InformationArticle> informationArticleList = informationArticleRepository.findAllByChekUserIdAndWatdou(user.getId(), Watdou.ADD);
        for (InformationArticle article : informationArticleList) {
            UUID id = article.getArticle().getId();
            List<UUID> articleList1 = informationArticleRepository.findAllByArticleIdSAndArticleStatusName(id, ArticleStatusName.I_ACCEPTED.toString());
            for (UUID informationArticle : articleList1) {
                articleList.add(articleRepository.getById(informationArticle));
            }
        }
        return articleList;
    }
// bu  Reviewer  va Redactor qidirish uchun categorya boyicha

    public ApiResponse getReviewerAndRedactorRandom(GetUsersRoleId getUsersRoleId) {
//        Article article = articleRepository.findByConfirmTrueAndId(getUsersRoleId.getArticleId());

        Article article = articleRepository.getById(getUsersRoleId.getArticleId());
        System.out.println("asaswdswdwa     " + article.getArticleStatusName());
        System.out.println("aaaaaaaaaa     " + getUsersRoleId.getRoleId());
        Integer roleId = getUsersRoleId.getRoleId();

        System.out.println("rooooooolllll   " + roleId);

        if (roleId == 777) {
            if (article.getArticleStatusName().equals(ArticleStatusName.START)) {
                roleId = 3;
            } else if ((article.getArticleStatusName().equals(ArticleStatusName.BEGIN_CHECK))) {
                roleId = 2;
            } else if (article.getArticleStatusName().toString().equals("null")) {
                roleId = getUsersRoleId.getRoleId();
            }
        }

        List<User> users = userRepository.findAllByEnabledTrueAndRolesIdAndCategoriesId(roleId, article.getCategory()
                .getId());
        if (users == null) {
            return new ApiResponse("There is no user with this category");
        }
        return new ApiResponse("Users", true, users);
    }



    // adminstratorlar uchun articlarni statusiga qarab get qilish
    public ApiResponse newMyArticle(User user, ArticleStatusInAdmins articleStatusInAdmins) {
        System.out.println("statsus berish kerak    "+articleStatusInAdmins.getStatus());
        if (articleStatusInAdmins.getStatus()==null){
            return new ApiResponse(" status bo'sh kelyapti Akbar !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! ");
        }
        if (user == null) {
            return new ApiResponse(" bunday user yo'q ");
        }



        List<Article> articles = articleRepository.findAllByConfirmTrueAndAdmin(user.getId(), articleStatusInAdmins.getStatus());

        if (articles == null) {
            return new ApiResponse(" bunda userdi maqollari yo'q ");
        }

        System.out.println(articles);
        return new ApiResponse("Ok", true, articles);



    }


    // mening vazifalarim     userlarning vazifalari
//    public ApiResponse myDuties(User user) {
//        String roleName = null;
//        for (Role role : user.getRoles()) {
//
//            roleName = role.getRoleName();
//        }
//        System.out.println(roleName);
//
//        if (roleName.equalsIgnoreCase("ROLE_ADMINISTRATOR")) {
//
//            List<Article> articles=articleRepository.
//
//            return new ApiResponse("ok", true,articles );
//
//        } else if (roleName.equalsIgnoreCase("ROLE_REDACTOR")) {
//
//
//        } else if (roleName.equalsIgnoreCase("ROLE_REVIEWER")) {
//
//
//        }
//
//
//        return new ApiResponse("okkk");
//    }


}
