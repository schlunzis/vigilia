package org.schlunzis.vigilia.core.embedding;

import java.util.List;

public interface Model {


    List<Result> query(List<EmbeddingWrapper> embeddingWrappers, String query, int maxResults);

    /**
     * query with default maxResults = 10
     */
    List<Result> query(List<EmbeddingWrapper> embeddingWrappers, String query);

    List<EmbeddingWrapper> embed(List<String> facts);

    List<Result> embedAndQuery(List<String> facts, String query);

}