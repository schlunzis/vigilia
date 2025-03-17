package org.schlunzis.vigilia.core.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.schlunzis.vigilia.core.embedding.EmbeddingsManager;
import org.schlunzis.vigilia.core.embedding.MetadataKeys;
import org.schlunzis.vigilia.core.embedding.Result;
import org.schlunzis.vigilia.core.model.SearchResultDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class SearchService implements SearchApiDelegate {

    private final EmbeddingsManager embeddingsManager;

    @Override
    public ResponseEntity<List<SearchResultDTO>> searchFiles(String query, Optional<Integer> pageNumber, Optional<Integer> pageSize) {
        log.info("Searching files: {}", query);

        long startTime = System.currentTimeMillis();
        int pn = pageNumber.orElseThrow();
        int ps = pageSize.orElseThrow();
        List<Result> results = embeddingsManager.query(query, pn, ps);

        log.info("Search took {} ms", System.currentTimeMillis() - startTime);
        return ResponseEntity.ok(results
                .stream()
                .map(r -> new SearchResultDTO()
                        .path(r.textSegment().metadata().getString(MetadataKeys.PATH))
                        .score(r.similarityScore())
                        .text(r.textSegment().text())
                )
                .toList());
    }

}
