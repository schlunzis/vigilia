package org.schlunzis.vigilia.core.embedding;

import dev.langchain4j.data.segment.TextSegment;

import java.util.List;
import java.util.SortedSet;

public interface Model {


    SortedSet<Result> query(List<EmbeddingWrapper> embeddingWrappers, String query);

    List<EmbeddingWrapper> embed(List<TextSegment> textSegments);

    SortedSet<Result> embedAndQuery(List<TextSegment> textSegments, String query);

}