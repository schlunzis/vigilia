package org.schlunzis.vigilia.core.model;

import dev.langchain4j.model.embedding.onnx.allminilml6v2.AllMiniLmL6V2EmbeddingModel;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PrepackagedModel extends AbstractModel {

    PrepackagedModel() {
        super(new AllMiniLmL6V2EmbeddingModel());
    }

}