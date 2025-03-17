package org.schlunzis.vigilia.core.io;

import dev.langchain4j.data.document.Metadata;
import dev.langchain4j.data.segment.TextSegment;

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
                addSegment(result, sb.toString(), file);
                sb = new StringBuilder();
            }
            sb.append(line).append("\n");
        }
        addSegment(result, sb.toString(), file);

        return result;
    }

    private boolean isHeader(String line) {
        return line.matches("^#+ .*");
    }

    private void addSegment(List<TextSegment> result, String text, File file) {
        if (!text.isBlank()) {
            text = text.trim();
            Metadata metadata = createMetadataForFile(file);
            result.add(new TextSegment(text, metadata));
        }
    }

}
