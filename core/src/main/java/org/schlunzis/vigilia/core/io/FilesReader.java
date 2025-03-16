package org.schlunzis.vigilia.core.io;

import dev.langchain4j.data.segment.TextSegment;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.schlunzis.vigilia.core.embedding.MetadataKeys;
import org.schlunzis.vigilia.core.model.EmbeddingEntity;
import org.schlunzis.vigilia.core.model.EmbeddingsRepository;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class FilesReader {

    private final EmbeddingsRepository embeddingsRepository;

    public List<File> readFilesFromPaths(List<String> paths) {
        log.info("Reading files from paths: {}", paths);

        List<Map<String, Object>> embeddedMetadata = embeddingsRepository
                .findAll()
                .stream()
                .map(EmbeddingEntity::getMetadata)
                .toList();

        List<File> result = new ArrayList<>();
        for (String path : paths) {
            log.info("Reading files from path: {}", path);
            File file = new File(path);
            if (!file.isDirectory() && needsIndexing(file, embeddedMetadata)) {
                result.add(file);
            } else {
                result.addAll(readFilesFromPath(path)
                        .stream()
                        .filter(f -> needsIndexing(f, embeddedMetadata))
                        .toList());
            }
        }
        return result;
    }

    private List<File> readFilesFromPath(String path) {
        File file = new File(path);
        File[] files = file.listFiles();
        if (files == null)
            throw new IllegalArgumentException("Path is not a directory: " + path); // should never happen

        List<File> result = new ArrayList<>();
        for (File f : files) {
            log.info("Found file: {}", f);
            if (f.isDirectory()) {
                result.addAll(readFilesFromPath(f.getAbsolutePath()));
            } else {
                result.add(f);
            }
        }
        return result;
    }

    public List<TextSegment> readTextSegments(List<File> files, List<File> failedFiles) {
        log.info("Reading text segments from files: {}", files);

        List<TextSegment> result = new ArrayList<>();
        for (File file : files) {
            log.info("Reading text segments from file: {}", file);
            Optional<List<TextSegment>> optionalSegments = readTextSegmentsFromFile(file);
            if (optionalSegments.isPresent())
                result.addAll(optionalSegments.get());
            else
                failedFiles.add(file);
        }
        return result;
    }

    private Optional<List<TextSegment>> readTextSegmentsFromFile(File file) {
        String extension = file.getName().substring(file.getName().lastIndexOf(".") + 1);
        try {
            switch (extension) {
                case "md":
                    return Optional.of(new MarkdownSegmenter().readTextSegments(file));
                default:
                    return Optional.empty();
            }
        } catch (IOException e) {
            log.error("Error reading text segments from file: {}", file);
            return Optional.empty();
        }
    }

    private boolean needsIndexing(File f, List<Map<String, Object>> metadata) {
        return metadata
                .stream()
                .noneMatch(m -> m.get(MetadataKeys.PATH).equals(f.getAbsolutePath()) &&
                        (Long) (m.get(MetadataKeys.LAST_MODIFIED)) == f.lastModified());
    }

}
