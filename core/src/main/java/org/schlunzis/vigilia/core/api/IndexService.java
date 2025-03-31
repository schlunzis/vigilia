package org.schlunzis.vigilia.core.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.schlunzis.vigilia.core.dto.IndexFilesRequestDTO;
import org.schlunzis.vigilia.core.embedding.EmbeddingsManager;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class IndexService implements IndexApiDelegate {

    private final EmbeddingsManager embeddingsManager;

    @Override
    public ResponseEntity<Void> indexFiles(IndexFilesRequestDTO indexFilesRequestDTO) {
        List<String> paths = indexFilesRequestDTO.getPaths();
        log.info("Indexing files: {}", paths);
        embeddingsManager.index(paths);
        return ResponseEntity.ok().build();
    }
}
