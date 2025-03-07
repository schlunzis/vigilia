package org.schlunzis.vigilia.core.embedding;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class EmbeddingsManager {

    private final Model model;

    private final List<EmbeddingWrapper> db = new ArrayList<>();

    public void index(List<String> paths) {
        log.info("Indexing paths: {}", paths);

        // FIXME: read files

        db.addAll(model.embed(List.of("Hello World!", "Dolphins are evil")));
    }

    public List<Result> query(String query) {
        log.info("Searching for: {}", query);

        return model.query(db, query);
    }


}
