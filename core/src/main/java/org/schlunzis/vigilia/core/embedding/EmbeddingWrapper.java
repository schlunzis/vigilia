package org.schlunzis.vigilia.core.embedding;

import dev.langchain4j.data.embedding.Embedding;

public record EmbeddingWrapper(Embedding embedding, String fact, String path) {


}
