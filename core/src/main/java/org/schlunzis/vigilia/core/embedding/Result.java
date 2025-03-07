package org.schlunzis.vigilia.core.embedding;

import dev.langchain4j.data.segment.TextSegment;

public record Result(double similarityScore, TextSegment textSegment) implements Comparable<Result> {

    @Override
    public int compareTo(Result o) {
        return Double.compare(o.similarityScore(), similarityScore);
    }
}
