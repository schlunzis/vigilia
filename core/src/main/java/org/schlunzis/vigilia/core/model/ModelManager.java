package org.schlunzis.vigilia.core.model;

import dev.langchain4j.model.embedding.onnx.PoolingMode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.schlunzis.vigilia.core.entity.model.ModelEntity;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class ModelManager {

    private final ModelRepository modelRepository;

    private final Map<String, Model> loadedModels = new HashMap<>();

    public static final String DEFAULT_MODEL = "default";

    public Model getModel(String name) {
        return loadedModels.computeIfAbsent(name, _ -> {
            if (name.equals(DEFAULT_MODEL)) {
                return new PrepackagedModel();
            } else {
                return loadModel(name);
            }
        });
    }

    private Model loadModel(String name) {
        ModelEntity modelEntity = modelRepository.findById(name).orElseThrow();
        return new OnnxModel(modelEntity.getModelPath(), modelEntity.getTokenizerPath(), modelEntity.getPoolingMode());
    }

    public List<ModelEntity> getAllModels() {
        return modelRepository.findAll();
    }


    public boolean addModel(String name, String modelPath, String tokenizerPath, PoolingMode poolingMode) {
        modelRepository.save(new ModelEntity(name, modelPath, tokenizerPath, poolingMode));
        return true;
    }

    public boolean removeModel(String name) {
        modelRepository.deleteById(name);
        return true;
    }
}
