package com.a702.hup.domain.documents.mongodb;

import com.a702.hup.domain.documents.mongodb.entity.DocumentsMongo;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface DocumentsMongoRepository extends MongoRepository<DocumentsMongo, String> {
}
