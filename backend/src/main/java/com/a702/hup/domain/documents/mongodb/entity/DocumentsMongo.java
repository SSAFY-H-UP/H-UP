package com.a702.hup.domain.documents.mongodb.entity;

import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Document(collection = "documents")
public class DocumentsMongo {
    @Id
    @NonNull
    @Indexed(unique=true)
    private String documentsId;
    private String content;

    @Builder
    public DocumentsMongo(@NonNull String documentsId) {
        this.documentsId = documentsId;
        content = "";
    }

    public void updateContent(String content) {
        this.content = content;
    }
}
