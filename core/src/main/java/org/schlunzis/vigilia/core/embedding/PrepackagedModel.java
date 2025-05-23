package org.schlunzis.vigilia.core.embedding;

import dev.langchain4j.data.embedding.Embedding;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.model.embedding.onnx.allminilml6v2.AllMiniLmL6V2EmbeddingModel;
import dev.langchain4j.store.embedding.CosineSimilarity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

@Slf4j
@Component
public class PrepackagedModel implements Model {

    private final EmbeddingModel embeddingModel = new AllMiniLmL6V2EmbeddingModel();

    @Override
    public SortedSet<Result> query(List<EmbeddingWrapper> embeddingWrappers, String query) {
        log.debug("Embedding query");
        Embedding queryEmbedding = embeddingModel.embed(query).content();

        log.debug("Calculating similarity");

        SortedSet<Result> results = new TreeSet<>();
        for (EmbeddingWrapper entry : embeddingWrappers) {
            double similarity = CosineSimilarity.between(queryEmbedding, entry.embedding());
            results.add(new Result(similarity, entry.textSegment()));
        }
        return results;
    }

    @Override
    public List<EmbeddingWrapper> embed(List<TextSegment> textSegments) {
        List<EmbeddingWrapper> embeddingWrappers = new ArrayList<>();

        for (TextSegment textSegment : textSegments) {
            Embedding factEmbedding = embeddingModel.embed(textSegment).content();
            embeddingWrappers.add(new EmbeddingWrapper(factEmbedding, textSegment));
        }
        return embeddingWrappers;
    }

    @Override
    public SortedSet<Result> embedAndQuery(List<TextSegment> textSegments, String query) {
        return query(embed(textSegments), query);
    }

}