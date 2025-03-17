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
import java.util.Map;

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

        files = filterFiles(files);

        List<File> failedFiles = new ArrayList<>();
        List<TextSegment> textSegments = filesReader.readTextSegments(files, failedFiles);
        List<EmbeddingWrapper> embeddings = new ArrayList<>(model.embed(textSegments));
        embeddingsRepository.saveAll(
                embeddings
                        .stream()
                        .map(EmbeddingEntity::fromEmbeddingWrapper)
                        .toList());
    }

    public List<Result> query(String query) {
        log.info("Searching for: {}", query);

        List<EmbeddingWrapper> embeddings = embeddingsRepository.findAll()
                .stream()
                .map(EmbeddingEntity::toEmbeddingWrapper)
                .toList();
        return model.query(embeddings, query);
    }

    private List<File> filterFiles(List<File> files) {
        log.debug("Files before filtering: {}", files.size());
        List<Map<String, Object>> embeddedMetadata = embeddingsRepository.findAllMetadata();
        // filter files to only index new or modified files
        files = files
                .stream()
                .filter(f -> needsIndexing(f, embeddedMetadata))
                .toList();
        log.debug("Files after filtering: {}", files.size());
        return files;
    }

    private boolean needsIndexing(File f, List<Map<String, Object>> metadata) {
        return metadata
                .stream()
                .noneMatch(m -> m.get(MetadataKeys.PATH).equals(f.getAbsolutePath()) &&
                        (Long) (m.get(MetadataKeys.LAST_MODIFIED)) == f.lastModified());
    }

}
