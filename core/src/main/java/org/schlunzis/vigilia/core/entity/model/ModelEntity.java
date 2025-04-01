package org.schlunzis.vigilia.core.entity.model;

import dev.langchain4j.model.embedding.onnx.PoolingMode;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "modelEntity")
public class ModelEntity {

    @Id
    @Column(length = 50)
    private String name;

    @Column(length = 4096)
    private String modelPath;

    @Column(length = 4096)
    private String tokenizerPath;

    private PoolingMode poolingMode;

}



