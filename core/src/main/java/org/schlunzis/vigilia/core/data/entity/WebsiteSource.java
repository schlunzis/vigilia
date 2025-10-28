package org.schlunzis.vigilia.core.data.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.net.URI;
import java.time.OffsetDateTime;
import java.util.Map;
import java.util.UUID;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class WebsiteSource extends Source {

    @Column(nullable = false, columnDefinition = "TEXT")
    private URI url;

    public WebsiteSource(UUID id, String title, OffsetDateTime addedDate, OffsetDateTime lastUpdatedDate, Map<String, Object> additionalMetadata, UUID indexedWithReaderId, URI url) {
        super(id, title, addedDate, lastUpdatedDate, additionalMetadata, indexedWithReaderId);
        this.url = url;
    }

}
