package com.example.article.entity.template;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.UUID;

@Data
@MappedSuperclass
@EntityListeners({AuditingEntityListener.class})
public abstract class AbsEntity {
//    @Id
//    @Type(type = "org.hibernate.type.PostgresUUIDType")
//    @GenericGenerator(name = "uuid2",strategy ="org.hibernate.id.UUIDGenerator" )
    @Id
    @Type(type = "org.hibernate.type.PostgresUUIDType")
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID id;

    @JsonIgnore
    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private Timestamp createdAt;

    @JsonIgnore
    @UpdateTimestamp
    @Column(nullable = false)
    private Timestamp updatedAt;

    @JsonIgnore
    @CreatedBy
    private UUID createdBy;

    @JsonIgnore
    @LastModifiedBy
    private UUID updatedBy;



}
