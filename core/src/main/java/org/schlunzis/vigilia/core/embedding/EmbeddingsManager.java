package org.schlunzis.vigilia.core.embedding;

import dev.langchain4j.data.segment.TextSegment;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.schlunzis.vigilia.core.io.FilesReader;
import org.schlunzis.vigilia.core.model.EmbeddingEntity;
import org.schlunzis.vigilia.core.model.EmbeddingsRepository;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.*;

@Slf4j
@Component
@RequiredArgsConstructor
public class EmbeddingsManager {

    private final Model model;
    private final FilesReader filesReader;
    private final EmbeddingsRepository embeddingsRepository;

    Map<String, SortedSet<Result>> queryCache = new HashMap<>();

    public void index(List<String> paths) {
        log.info("Indexing paths: {}", paths);

        List<File> files = filesReader.readFilesFromPaths(paths);
        List<File> failedFiles = new ArrayList<>();
        List<TextSegment> textSegments = filesReader.readTextSegments(files, failedFiles);
        List<EmbeddingWrapper> embeddings = new ArrayList<>(model.embed(textSegments));
        embeddingsRepository.saveAll(
                embeddings
                        .stream()
                        .map(EmbeddingEntity::fromEmbeddingWrapper)
                        .toList());
    }

    public List<Result> query(String query, int pageNumber, int pageSize) {
        log.info("Searching for: {}", query);

        SortedSet<Result> results = queryCache.computeIfAbsent(query, _ ->
                model.query(embeddingsRepository.findAll()
                                .stream()
                                .map(EmbeddingEntity::toEmbeddingWrapper)
                                .toList(),
                        query)
        );

        return results.stream()
                .skip((long) pageNumber * pageSize)
                .limit(pageSize)
                .toList();
    }


}
