package org.schlunzis.vigilia.core.embedding;

public record Result(double score, String fact) implements Comparable<Result> {

    @Override
    public int compareTo(Result o) {
        return Double.compare(o.score(), score);
    }
}
