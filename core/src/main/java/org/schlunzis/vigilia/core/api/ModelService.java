package org.schlunzis.vigilia.core.api;

import lombok.RequiredArgsConstructor;
import org.schlunzis.vigilia.core.dto.AddModelRequestDTO;
import org.schlunzis.vigilia.core.dto.ModelDTO;
import org.schlunzis.vigilia.core.model.ModelManager;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

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

        boolean success = modelManager.addModel(name, modelPath, tokenizerPath);

        if (success) {
            return ResponseEntity.ok().build();
        }
        // TODO return proper error code
        return ResponseEntity.badRequest().build();

    }

    @Override
    public ResponseEntity<List<ModelDTO>> listModels() {
        return ModelsApiDelegate.super.listModels();
    }

    @Override
    public ResponseEntity<Void> removeModel(String modelName) {
        return ModelsApiDelegate.super.removeModel(modelName);
    }
}
