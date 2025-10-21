package org.schlunzis.vigilia.core.data.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;
import java.util.Map;
import java.util.UUID;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class FileSource extends Source {

    @Column(nullable = false, columnDefinition = "TEXT")
    private String path;

    public FileSource(UUID id, String title, OffsetDateTime addedDate, OffsetDateTime lastUpdatedDate, Map<String, Object> additionalMetadata, UUID indexedWithReaderId, String path) {
        super(id, title, addedDate, lastUpdatedDate, additionalMetadata, indexedWithReaderId);
        this.path = path;
    }

}
