package org.schlunzis.vigilia.core.embedding;

import dev.langchain4j.data.segment.TextSegment;

import java.util.List;

public interface Model {


    List<Result> query(List<EmbeddingWrapper> embeddingWrappers, String query, int maxResults);

    /**
     * query with default maxResults = 10
     */
    List<Result> query(List<EmbeddingWrapper> embeddingWrappers, String query);

    List<EmbeddingWrapper> embed(List<TextSegment> textSegments);

    List<Result> embedAndQuery(List<TextSegment> textSegments, String query);

}