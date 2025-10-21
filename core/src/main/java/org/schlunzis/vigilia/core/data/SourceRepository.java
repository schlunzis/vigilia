package org.schlunzis.vigilia.core.data;

import org.schlunzis.vigilia.core.data.entity.Source;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface SourceRepository extends JpaRepository<Source, UUID> {
}
