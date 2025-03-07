package org.schlunzis.vigilia.core.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.schlunzis.vigilia.core.embedding.EmbeddingsManager;
import org.schlunzis.vigilia.core.embedding.Result;
import org.schlunzis.vigilia.core.model.SearchResultDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class SearchService implements SearchApiDelegate {

    private final EmbeddingsManager embeddingsManager;

    @Override
    public ResponseEntity<List<SearchResultDTO>> searchFiles(String query) {
        log.info("Searching files: {}", query);

        List<Result> results = embeddingsManager.query(query);

        return ResponseEntity.ok(results
                .stream()
                .map(r -> new SearchResultDTO()
                        .path(r.fact())
                        .score(r.score()))
                .toList());
    }

}
