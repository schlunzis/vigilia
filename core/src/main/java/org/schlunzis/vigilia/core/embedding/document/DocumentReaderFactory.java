package org.schlunzis.vigilia.core.embedding.document;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.schlunzis.vigilia.core.embedding.document.exception.ReadFileException;
import org.schlunzis.vigilia.core.embedding.document.reader.PdfDocumentReader;
import org.schlunzis.vigilia.core.embedding.document.reader.WebsiteDocumentReader;
import org.springframework.ai.document.DocumentReader;
import org.springframework.ai.reader.TextReader;
import org.springframework.ai.reader.markdown.MarkdownDocumentReader;
import org.springframework.ai.reader.markdown.config.MarkdownDocumentReaderConfig;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.net.URI;

import static org.schlunzis.vigilia.core.embedding.document.MetadataKeys.FILE_NAME;
import static org.schlunzis.vigilia.core.embedding.document.MetadataKeys.URL;

@Slf4j
@Component
public class DocumentReaderFactory {

    public DocumentReader create(@NonNull Resource file, @NonNull String fileEnding, @NonNull String fileName) {
        return switch (fileEnding) {
            case "txt" -> {
                log.info("Indexing file as plain text file");
                TextReader reader = new TextReader(file);
                reader.getCustomMetadata().put(FILE_NAME.getValue(), fileName);
                yield reader;
            }
            case "md", "markdown" -> {
                log.info("Indexing file as Markdown file");
                MarkdownDocumentReaderConfig config = MarkdownDocumentReaderConfig.defaultConfig();
                config.additionalMetadata.put(FILE_NAME.getValue(), fileName);
                yield new MarkdownDocumentReader(file, config);
            }
            case "pdf" -> {
                log.info("Indexing file as PDF file");
                yield new PdfDocumentReader(file);
            }
            default -> throw new ReadFileException("Unsupported file type: " + fileEnding);
        };
    }

    public DocumentReader createForURL(@NonNull URI url, @NonNull Resource resource) {
        WebsiteDocumentReader reader = new WebsiteDocumentReader(resource);
        reader.getAdditionalMetadata().put(URL.getValue(), url);
        return reader;
    }

}
