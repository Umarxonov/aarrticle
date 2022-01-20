package com.example.article.repository;

import com.example.article.entity.Attachment;
import com.example.article.entity.AttachmentContent;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AttachmentContentRepository  extends JpaRepository<AttachmentContent, UUID> {
    AttachmentContent findByAttachment(Attachment attechment);
}
