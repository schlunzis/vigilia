package org.schlunzis.vigilia.core.data.mapper;

import org.schlunzis.vigilia.core.model.SearchResultDTO;
import org.springframework.ai.document.Document;
import org.springframework.stereotype.Component;

@Component
public class SearchResultMapper {

    public SearchResultDTO toDTO(Document document) {
        SearchResultDTO searchResultDTO = new SearchResultDTO()
                .score(document.getScore())
                .text(document.getText());
        return searchResultDTO;
    }

}
