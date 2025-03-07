package org.schlunzis.vigilia.core.embedding;

import dev.langchain4j.data.segment.TextSegment;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.schlunzis.vigilia.core.io.FilesReader;
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

    private final List<EmbeddingWrapper> db = new ArrayList<>();

    public void index(List<String> paths) {
        log.info("Indexing paths: {}", paths);

        List<File> files = filesReader.readFilesFromPaths(paths);
        List<File> failedFiles = new ArrayList<>();
        List<TextSegment> textSegments = filesReader.readTextSegments(files, failedFiles);
        List<EmbeddingWrapper> embeddings = new ArrayList<>(model.embed(textSegments));

        db.addAll(embeddings);
    }

    public List<Result> query(String query) {
        log.info("Searching for: {}", query);

        return model.query(db, query);
    }


}
