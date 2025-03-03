package org.schlunzis.vigilia.core.api;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class IndexService implements IndexApiDelegate {

    @Override
    public ResponseEntity<Void> indexFiles(String body) {
        log.info("Indexing files: {}", body);
        return ResponseEntity.ok().build();
    }
}
