package org.schlunzis.vigilia.core.data.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.TimeZoneStorage;
import org.hibernate.annotations.TimeZoneStorageType;
import org.hibernate.type.SqlTypes;

import java.time.OffsetDateTime;
import java.util.Map;
import java.util.UUID;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Source {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    @TimeZoneStorage(TimeZoneStorageType.COLUMN)
    private OffsetDateTime addedDate;

    @Column(nullable = false)
    @TimeZoneStorage(TimeZoneStorageType.COLUMN)
    private OffsetDateTime lastUpdatedDate;

    // https://www.baeldung.com/hibernate-persist-json-object#the-jdbctypecode-annotation
    @JdbcTypeCode(SqlTypes.JSON)
    private Map<String, Object> additionalMetadata;

    @Column(nullable = false)
    private UUID indexedWithReaderId;

}
