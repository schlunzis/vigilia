package org.schlunzis.vigilia.core.embedding;

import dev.langchain4j.data.segment.TextSegment;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.schlunzis.vigilia.core.io.FilesReader;
import org.schlunzis.vigilia.core.model.EmbeddingEntity;
import org.schlunzis.vigilia.core.model.EmbeddingsRepository;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class EmbeddingsManager {

    private final Model model;
    private final FilesReader filesReader;
    private final EmbeddingsRepository embeddingsRepository;

    public void index(List<String> paths) {
        log.info("Indexing paths: {}", paths);

        List<File> files = filesReader.readFilesFromPaths(paths);
        List<File> failedFiles = new ArrayList<>();
        List<TextSegment> textSegments = filesReader.readTextSegments(files, failedFiles);
        List<EmbeddingWrapper> embeddings = new ArrayList<>(model.embed(textSegments));
        embeddingsRepository.saveAll(embeddings.stream().map(EmbeddingEntity::fromEmbeddingWrapper).toList());
    }

    public List<Result> query(String query) {
        log.info("Searching for: {}", query);

        List<EmbeddingWrapper> embeddings = embeddingsRepository.findAll().stream().map(EmbeddingEntity::toEmbeddingWrapper).toList();
        return model.query(embeddings, query);
    }


}
