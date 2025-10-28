package org.schlunzis.vigilia.core.state;

import java.util.UUID;

public record ApplicationState(
        UUID id,
        IndexingState indexingState
) {

    public ApplicationState(IndexingState indexingState) {
        this(UUID.randomUUID(), indexingState);
    }

    public ApplicationState withIndexingState(IndexingState newIndexingState) {
        return new ApplicationState(newIndexingState);
    }

}
