package org.schlunzis.vigilia.core.model;

import dev.langchain4j.data.document.Metadata;
import dev.langchain4j.data.embedding.Embedding;
import dev.langchain4j.data.segment.TextSegment;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.schlunzis.vigilia.core.embedding.EmbeddingWrapper;

import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Since {@link org.schlunzis.vigilia.core.embedding.EmbeddingWrapper} holds fields that are not directly supported by JPA (aka Objects that are not annotated with
 * {@link jakarta.persistence.Entity}), we need to convert it to a JPA entity.
 */
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class EmbeddingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @ElementCollection
    private List<Float> vector;

    private String text;

    @Convert(converter = MetadataConverter.class)
    private Map<String, Object> metadata;

    public EmbeddingWrapper toEmbeddingWrapper() {
        Embedding embedding = Embedding.from(vector);
        TextSegment textSegment = new TextSegment(text, new Metadata(metadata));
        return new EmbeddingWrapper(embedding, textSegment);
    }

    public static EmbeddingEntity fromEmbeddingWrapper(EmbeddingWrapper embeddingWrapper) {
        Embedding embedding = embeddingWrapper.embedding();
        TextSegment textSegment = embeddingWrapper.textSegment();
        return new EmbeddingEntity(null, embedding.vectorAsList(), textSegment.text(), textSegment.metadata().toMap());
    }


}
