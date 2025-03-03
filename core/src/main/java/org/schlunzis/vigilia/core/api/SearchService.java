package org.schlunzis.vigilia.core.api;

import lombok.extern.slf4j.Slf4j;
import org.schlunzis.vigilia.core.model.SearchResultDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class SearchService implements SearchApiDelegate {

    @Override
    public ResponseEntity<List<SearchResultDTO>> searchFiles(String body) {
        log.info("Searching files: {}", body);

        SearchResultDTO searchResultDTO = new SearchResultDTO();
        searchResultDTO.setPath("/path/to/file");
        searchResultDTO.setScore(0.5f);
        SearchResultDTO searchResultDTO2 = new SearchResultDTO();
        searchResultDTO2.setPath("/path/to/another/file");
        searchResultDTO2.setScore(0.3f);

        return ResponseEntity.ok(List.of(searchResultDTO, searchResultDTO2));
    }

}
