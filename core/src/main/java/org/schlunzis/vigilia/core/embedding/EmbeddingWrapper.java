package org.schlunzis.vigilia.core.embedding;

import dev.langchain4j.data.embedding.Embedding;
import dev.langchain4j.data.segment.TextSegment;

public record EmbeddingWrapper(Embedding embedding, TextSegment textSegment) {
}
