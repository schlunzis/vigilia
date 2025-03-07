package org.schlunzis.vigilia.core.io;

import dev.langchain4j.data.document.Metadata;
import dev.langchain4j.data.segment.TextSegment;
import org.schlunzis.vigilia.core.embedding.MetadataKeys;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

final class MarkdownSegmenter implements FileSegmenter {

    @Override
    public List<TextSegment> readTextSegments(File file) throws IOException {
        List<TextSegment> result = new ArrayList<>();
        List<String> lines = Files.readAllLines(file.toPath());

        StringBuilder sb = new StringBuilder();
        for (String line : lines) {
            if (isHeader(line)) {
                if (!sb.isEmpty()) {
                    Metadata metadata = createMetadataForFile(file);
                    metadata.put(MetadataKeys.HEADER, sb.toString());
                    result.add(new TextSegment(sb.toString(), metadata));
                    sb = new StringBuilder();
                }
            } else {
                sb.append(line).append("\n");
            }
        }

        return result;
    }

    private boolean isHeader(String line) {
        return line.matches("^#+ .*");
    }

}
