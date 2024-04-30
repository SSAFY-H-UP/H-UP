package com.a702.hup.domain.document.mongodb;

import com.a702.hup.domain.document.mongodb.entity.DocumentsMongo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class DocumentsMongoService {
    private final DocumentsMongoRepository documentsMongoRepository;

    public void save(String documentId, String content) {
        DocumentsMongo documentsMongo = findOrCreateDocuments(documentId);
        documentsMongo.updateContent(content);
        documentsMongoRepository.save(documentsMongo);
    }

    private DocumentsMongo findOrCreateDocuments(String documentId) {
        return documentsMongoRepository.findById(documentId).orElseGet(
                () -> DocumentsMongo.builder().documentId(documentId).build()
        );
    }
}
