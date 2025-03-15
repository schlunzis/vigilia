package org.schlunzis.vigilia.core.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface EmbeddingsRepository extends JpaRepository<EmbeddingEntity, UUID> {

    // FIXME: write query to get all metadata from the database
    // List<Map<String, Object>> findAllMetadata();
}
