package org.schlunzis.vigilia.core.data.mapper;

import org.schlunzis.vigilia.core.model.SearchResultDTO;
import org.schlunzis.vigilia.core.model.SourceMetadataDTO;
import org.springframework.ai.document.Document;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.UUID;

@Component
public class SearchResultMapper {

    public SearchResultDTO toDTO(Document document) {
        SearchResultDTO searchResultDTO = new SearchResultDTO()
                .score(document.getScore())
                .sourceId(UUID.fromString(document.getId()))
                .metadata(toSourceMetadataDTO(document, document.getMetadata()))
                .text(document.getText());
        return searchResultDTO;
    }

    public SourceMetadataDTO toSourceMetadataDTO(Document document, Map<String, Object> metadata) {
        return new SourceMetadataDTO();
    }

}
