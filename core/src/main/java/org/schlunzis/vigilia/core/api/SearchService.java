package org.schlunzis.vigilia.core.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.schlunzis.vigilia.core.controller.SearchApiDelegate;
import org.schlunzis.vigilia.core.data.mapper.SearchResultMapper;
import org.schlunzis.vigilia.core.embedding.EmbeddingService;
import org.schlunzis.vigilia.core.model.SearchRequestDTO;
import org.schlunzis.vigilia.core.model.SearchResultDTO;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class SearchService implements SearchApiDelegate {

    private final EmbeddingService embeddingService;
    private final SearchResultMapper searchResultMapper;

    @Override
    public ResponseEntity<List<SearchResultDTO>> searchFiles(SearchRequestDTO searchRequestDTO, Optional<Integer> pageNumber, Optional<Integer> pageSize) {
        SearchRequest searchRequest = SearchRequest.builder()
                .query(searchRequestDTO.getQuery())
                .build();

        List<Document> documents = embeddingService.search(searchRequest);
        List<SearchResultDTO> results = documents.stream()
                .map(searchResultMapper::toDTO)
                .toList();
        return ResponseEntity.ok(results);
    }

}
