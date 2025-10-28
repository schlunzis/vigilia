package org.schlunzis.vigilia.core.config;

import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.schlunzis.vigilia.core.autoconfigure.VectorStoreProperties;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class VectorStoreTableNameProvider {

    private final VectorStoreProperties vsProperties;

    @Value("${spring.ai.openai.embedding.options.model}")
    private String modelName;

    @Getter
    private String tableName;

    @PostConstruct
    public void init() {
        this.tableName = constructTableName(vsProperties.getTableNamePrefix());
    }

    /**
     * This method builds a table name for a database based on the model name.
     * It is assumed that the model is in the format of a docker <a href="https://docs.docker.com/engine/manage-resources/labels/">label</a>.
     *
     * @return a string which can be used as a database table name
     */
    private String constructTableName(String prefix) {
        String cleanModelName = modelName;

        // replace "/" "-" and "."
        cleanModelName = cleanModelName.replaceAll("[/\\-.:]", "_");

        return prefix + cleanModelName;
    }

}
