package org.schlunzis.vigilia.core.model;

import org.schlunzis.vigilia.core.entity.model.ModelEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ModelRepository extends JpaRepository<ModelEntity, String> {


}
