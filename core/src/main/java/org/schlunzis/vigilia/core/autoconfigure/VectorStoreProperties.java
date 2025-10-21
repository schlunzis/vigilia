package org.schlunzis.vigilia.core.autoconfigure;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "vigilia.vector-store")
public class VectorStoreProperties {

    /**
     * Whether to initialize the required schema
     */
    private boolean initializeSchema = false;
    /**
     * Whether to remove the existing vector store table if it exists.
     */
    private boolean removeExistingVectorStoreTable = false;
    /**
     * The name of the schema to use for the vector store.
     * This is used to create a unique table name based on the model name.
     */
    private String schemaName = null;
    /**
     * Enables schema and table name validation to ensure they are valid and existing objects.
     */
    private boolean schemaValidation = false;
    /**
     * The prefix for the table names in the vector store.
     * This is used to create a unique table name based on the model name.
     */
    private String tableNamePrefix = "vector_store_";
    /**
     * The distance metric to use for the vector store.
     * See options for more details.
     */
    private Distance distance = Distance.COSINE;

    public enum Distance {
        /**
         * Cosine distance metric.
         * <p>
         * MariaDB: {@link org.springframework.ai.vectorstore.mariadb.MariaDBVectorStore.MariaDBDistanceType#COSINE}
         */
        COSINE,
        /**
         * Euclidean distance metric.
         * <p>
         * MariaDB: {@link org.springframework.ai.vectorstore.mariadb.MariaDBVectorStore.MariaDBDistanceType#EUCLIDEAN}
         */
        EUCLIDEAN,
        /**
         * Inner product distance metric.
         * <p>
         * MariaDB: N/A
         */
        NEGATIVE_INNER_PRODUCT
    }
}
