package com.a702.hup.domain.document.mongodb.entity;

import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Document(collection = "documents")
public class DocumentsMongo {
    @Id
    private String id;
    private String content;

    @Builder
    public DocumentsMongo(String documentId) {
        this.id = documentId;
        content = "";
    }

    public void updateContent(String content) {
        this.content = content;
    }
}
