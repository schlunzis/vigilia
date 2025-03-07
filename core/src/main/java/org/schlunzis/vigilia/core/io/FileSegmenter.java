package org.schlunzis.vigilia.core.io;

import dev.langchain4j.data.document.Metadata;
import dev.langchain4j.data.segment.TextSegment;
import org.schlunzis.vigilia.core.embedding.MetadataKeys;

import java.io.File;
import java.io.IOException;
import java.util.List;

sealed interface FileSegmenter permits MarkdownSegmenter {

    List<TextSegment> readTextSegments(File file) throws IOException;

    default Metadata createMetadataForFile(File file) {
        Metadata metadata = new Metadata();
        metadata.put(MetadataKeys.PATH, file.getAbsolutePath());
        return metadata;
    }

}
