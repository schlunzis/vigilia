package org.schlunzis.vigilia.core.api;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.schlunzis.vigilia.core.api.exception.SplittingException;
import org.schlunzis.vigilia.core.controller.SourcesApiDelegate;
import org.schlunzis.vigilia.core.data.SourceRepository;
import org.schlunzis.vigilia.core.data.entity.Source;
import org.schlunzis.vigilia.core.data.mapper.SourceMapper;
import org.schlunzis.vigilia.core.embedding.backlog.EmbeddingBacklog;
import org.schlunzis.vigilia.core.embedding.backlog.EmbeddingPriority;
import org.schlunzis.vigilia.core.embedding.backlog.EmbeddingTask;
import org.schlunzis.vigilia.core.embedding.document.DocumentReaderService;
import org.schlunzis.vigilia.core.embedding.document.exception.ReadFileException;
import org.schlunzis.vigilia.core.embedding.document.exception.ReadUrlException;
import org.schlunzis.vigilia.core.model.FileSourceDTO;
import org.schlunzis.vigilia.core.model.SourceDTO;
import org.schlunzis.vigilia.core.model.SourceReaderDTO;
import org.schlunzis.vigilia.core.model.WebsiteSourceDTO;
import org.schlunzis.vigilia.core.util.VigiliaUrlResource;
import org.springframework.ai.document.Document;
import org.springframework.ai.transformer.splitter.TextSplitter;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class SourcesService implements SourcesApiDelegate {

    private final DocumentReaderService documentReaderService;
    private final EmbeddingBacklog embeddingBacklog;
    private final SourceMapper sourceMapper;
    private final SourceRepository sourceRepository;
    private final TextSplitter textSplitter;

    @SneakyThrows
    @Override
    public ResponseEntity<SourceDTO> createSource(SourceDTO sourceDTO) {
        List<Document> documents = switch (sourceDTO) {
            case FileSourceDTO fileSource -> {
                Resource fileResource = new FileSystemResource(fileSource.getPath());
                yield documentReaderService.read(fileResource, fileSource.getPath());
            }
            case WebsiteSourceDTO websiteSource ->
                    documentReaderService.readURL(websiteSource.getUrl(), new VigiliaUrlResource(websiteSource.getUrl()));
        };
        documents = postProcessDocuments(documents);

        Source source = sourceMapper.toEntity(sourceDTO);
        source = sourceRepository.save(source);

        EmbeddingTask task = new EmbeddingTask(source.getId(), documents);
        embeddingBacklog.add(task, EmbeddingPriority.LOW);

        return ResponseEntity.ok(sourceMapper.toDto(source));
    }

    private List<Document> postProcessDocuments(List<Document> documents) {
        final int numDocumentsBeforeSplitting = documents.size();
        documents = documents.stream()
                .flatMap(d -> textSplitter.split(d).stream())
                .toList();
        log.debug("Split into {} chunks to fit context window", documents.size());

        if (documents.isEmpty())
            throw new SplittingException("Source does not have enough content to be indexed");
        if (documents.size() < numDocumentsBeforeSplitting)
            log.warn("There are less documents after splitting than before.");

        return documents;
    }

    @ExceptionHandler
    public ResponseEntity<?> handleException(ReadFileException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @ExceptionHandler
    public ResponseEntity<?> handleException(ReadUrlException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @Override
    public ResponseEntity<Void> deleteSource(UUID id) {
        return SourcesApiDelegate.super.deleteSource(id);
    }

    @Override
    public ResponseEntity<List<SourceDTO>> listSources(Optional<String> filterExpression) {
        Iterable<Source> sources = sourceRepository.findAll();
        List<SourceDTO> sourceDTOs = new ArrayList<>();
        for (Source source : sources) {
            sourceDTOs.add(sourceMapper.toDto(source));
        }
        return ResponseEntity.ok(sourceDTOs);
    }

    @Override
    public ResponseEntity<List<SourceReaderDTO>> listSourcesReaders(Optional<UUID> readerId) {
        return SourcesApiDelegate.super.listSourcesReaders(readerId);
    }

}
