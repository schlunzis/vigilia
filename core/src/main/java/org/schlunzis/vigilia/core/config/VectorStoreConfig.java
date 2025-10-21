package org.schlunzis.vigilia.core.config;

import lombok.RequiredArgsConstructor;
import org.schlunzis.vigilia.core.autoconfigure.VectorStoreProperties;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.ai.vectorstore.mariadb.MariaDBVectorStore;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

@Configuration
@RequiredArgsConstructor
public class VectorStoreConfig {

    private final VectorStoreProperties vsProperties;
    private final VectorStoreTableNameProvider vectorStoreTableNameProvider;
    private final EmbeddingModel embeddingModel;

    private final JdbcTemplate jdbcTemplate;

    @Bean
    public VectorStore vectorStore() {
        return createMariaDBVectorStore();
    }

    private MariaDBVectorStore createMariaDBVectorStore() {
        return MariaDBVectorStore.builder(jdbcTemplate, embeddingModel)
                .initializeSchema(vsProperties.isInitializeSchema())
                .removeExistingVectorStoreTable(vsProperties.isRemoveExistingVectorStoreTable())
                .schemaName(vsProperties.getSchemaName())
                .schemaValidation(vsProperties.isSchemaValidation())
                .vectorTableName(vectorStoreTableNameProvider.getTableName())
                .distanceType(createMariaDBDistanceType())
                .build();
    }

    private MariaDBVectorStore.MariaDBDistanceType createMariaDBDistanceType() {
        return switch (vsProperties.getDistance()) {
            case COSINE -> MariaDBVectorStore.MariaDBDistanceType.COSINE;
            case EUCLIDEAN -> MariaDBVectorStore.MariaDBDistanceType.EUCLIDEAN;
            case NEGATIVE_INNER_PRODUCT -> throw new IllegalStateException(new IllegalArgumentException(
                    "Negative inner product distance is not supported by MariaDBVectorStore."
            ));
        };
    }

}
