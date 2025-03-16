package org.schlunzis.vigilia.core.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Repository
public interface EmbeddingsRepository extends JpaRepository<EmbeddingEntity, UUID> {

    @Query("SELECT metadata FROM EmbeddingEntity")
    List<Map<String, Object>> findAllMetadata();
}
