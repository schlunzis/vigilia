package org.schlunzis.vigilia.core.api;

import dev.langchain4j.model.embedding.onnx.PoolingMode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.schlunzis.vigilia.core.dto.AddModelRequestDTO;
import org.schlunzis.vigilia.core.dto.ModelDTO;
import org.schlunzis.vigilia.core.model.ModelManager;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ModelService implements ModelsApiDelegate {

    private final ModelManager modelManager;

    @Override
    public ResponseEntity<Void> addModel(AddModelRequestDTO addModelRequestDTO) {
        ModelDTO model = addModelRequestDTO.getModel();
        String modelPath = model.getModelPath();
        String tokenizerPath = model.getTokenizerPath();
        String name = model.getName();
        PoolingMode poolingMode = PoolingMode.valueOf(model.getPoolingMode().orElseThrow().getValue());
        log.info("Adding new model {}", name);

        boolean success = modelManager.addModel(name, modelPath, tokenizerPath, poolingMode);

        if (success) {
            return ResponseEntity.status(201).build();
        }
        return ResponseEntity.internalServerError().build();

    }

    @Override
    public ResponseEntity<List<ModelDTO>> listModels() {
        return ResponseEntity.ok(
                modelManager.getAllModels()
                        .stream()
                        .map(e ->
                                new ModelDTO()
                                        .name(e.getName())
                                        .modelPath(e.getModelPath())
                                        .tokenizerPath(e.getTokenizerPath()))
                        .toList()
        );
    }

    @Override
    public ResponseEntity<Void> removeModel(String modelName) {
        boolean success = modelManager.removeModel(modelName);
        if (success)
            return ResponseEntity.ok().build();
        return ResponseEntity.internalServerError().build();
    }
}
