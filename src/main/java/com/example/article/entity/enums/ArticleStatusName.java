package com.example.article.entity.enums;

public enum ArticleStatusName {
    START,
    BEGIN_CHECK,       // reviewrga berdi tekshirmoqda

    PREPARING_FOR_PUBLICATION,  // REDACTORGA biriktirilgan  nashirga tayorlanmoqda

    I_ACCEPTED,                // maqolanin tekshirishga qabul qildim
    I_DID_NOT_ACCEPT,                    // maqolanin tekshirishni rad etim


    CHECK_AND_ACCEPT,        // maqolani qabul qildim             rewivirlar tomondan beriladigon statuslar
    CHECK_AND_CANCEL,        // maqolani qabul qilmadim        rewivirlar tomondan beriladigon statuslar
    CHECK_AND_RECYCLE,       // maqolani qayta ishlashga berdim               rewivirlar tomondan beriladigon statuslar


    NONE
}