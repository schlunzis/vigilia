package org.schlunzis.vigilia.core.model;

import dev.langchain4j.model.embedding.onnx.OnnxEmbeddingModel;
import dev.langchain4j.model.embedding.onnx.PoolingMode;

public class OnnxModel extends AbstractModel {

    OnnxModel(String modelPath, String tokenizerPath, PoolingMode poolingMode) {
        super(new OnnxEmbeddingModel(modelPath, tokenizerPath, poolingMode));
    }

}
