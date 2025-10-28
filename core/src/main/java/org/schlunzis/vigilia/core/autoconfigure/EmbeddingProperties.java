package org.schlunzis.vigilia.core.autoconfigure;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.time.Duration;

@Data
@ConfigurationProperties(prefix = "vigilia.embedding")
public class EmbeddingProperties {
    private Model model = new Model();
    /**
     * The delay before retrying an embedding task if it fails with Spring AI's retry mechanism.
     */
    private Duration retryDelay = Duration.ofMinutes(1);

    /**
     * The maximum length of documents to embed.
     * Must be less than or equal to the context length of the model.
     */
    private int documentContextLength = 256;

    @Data
    public static class Model {
        /**
         * The context length for the embedding model.
         */
        private Integer contextLength = 256;
    }
}
