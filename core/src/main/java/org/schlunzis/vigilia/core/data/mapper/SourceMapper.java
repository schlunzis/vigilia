package org.schlunzis.vigilia.core.data.mapper;

import org.schlunzis.vigilia.core.data.entity.FileSource;
import org.schlunzis.vigilia.core.data.entity.Source;
import org.schlunzis.vigilia.core.data.entity.WebsiteSource;
import org.schlunzis.vigilia.core.model.*;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.time.OffsetDateTime;
import java.util.Map;
import java.util.UUID;

@Component
public class SourceMapper {

    public Source toEntity(SourceDTO sourceDTO) {
        return switch (sourceDTO) {
            case FileSourceDTO dto -> {
                SourceMetadataDTO metadata = dto.getMetadata().orElse(new SourceMetadataDTO());
                UUID id = dto.getId().orElse(null);
                String title = metadata.getTitle().orElse("Unnamed File Source");
                OffsetDateTime addedDate = metadata.getAddedDate().orElse(OffsetDateTime.now());
                OffsetDateTime lastUpdatedDate = metadata.getLastUpdatedDate().orElse(OffsetDateTime.now());
                UUID readerId = dto.getIndexedWith().orElse(UUID.randomUUID()); // FIXME what if reader id does not exist?
                yield new FileSource(id,
                        title,
                        addedDate,
                        lastUpdatedDate,
                        Map.of(),// FIXME: replace with proper additional metadata
                        readerId,
                        dto.getPath()
                );
            }
            case WebsiteSourceDTO dto -> {
                SourceMetadataDTO metadata = dto.getMetadata().orElse(new SourceMetadataDTO());
                UUID id = dto.getId().orElse(null);
                String title = metadata.getTitle().orElse("Unnamed Website Source");
                OffsetDateTime addedDate = metadata.getAddedDate().orElse(OffsetDateTime.now());
                OffsetDateTime lastUpdatedDate = metadata.getLastUpdatedDate().orElse(OffsetDateTime.now());
                UUID readerId = dto.getIndexedWith().orElse(UUID.randomUUID()); // FIXME what if reader id does not exist?
                yield new WebsiteSource(id,
                        title,
                        addedDate,
                        lastUpdatedDate,
                        Map.of(), // FIXME: replace with proper additional metadata
                        readerId,
                        dto.getUrl());
            }
        };
    }

    public SourceDTO toDto(Source source) {
        UUID id = source.getId();
        String title = source.getTitle();
        OffsetDateTime addedDate = source.getAddedDate();
        OffsetDateTime lastUpdatedDate = source.getLastUpdatedDate();
        UUID readerId = source.getIndexedWithReaderId();
        SourceMetadataDTO metadata = new SourceMetadataDTO()
                .title(title)
                .addedDate(addedDate)
                .lastUpdatedDate(lastUpdatedDate);
        return switch (source) {
            case FileSource fileSource -> {
                String path = fileSource.getPath();
                yield new FileSourceDTO()
                        .sourceType(SourceTypeDTO.FILE)
                        .id(id)
                        .metadata(metadata)
                        .path(path)
                        .indexedWith(readerId);
            }
            case WebsiteSource websiteSource -> {
                URI url = websiteSource.getUrl();
                yield new WebsiteSourceDTO()
                        .sourceType(SourceTypeDTO.WEBSITE)
                        .id(id)
                        .metadata(metadata)
                        .url(url)
                        .indexedWith(readerId);
            }
            default -> throw new IllegalStateException("Unexpected value: " + source);
        };
    }

}
