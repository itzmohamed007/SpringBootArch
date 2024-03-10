package com.arch.models.dto.response;

import com.arch.models.entities.AbstractEntity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.sql.Timestamp;
import java.util.UUID;

/**
 * Base class for response DTOs representing {@link AbstractEntity}.
 * AbstractResponse includes common fields such as id, createdAt, updatedAt, and version.
 * This class is intended to be extended by specific response DTOs in the system.
 *
 * @version 1.0
 */
@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public abstract class AbstractResponse implements _Response<UUID> {
    /**
     * The unique identifier of the entity.
     */
    private UUID id;

    /**
     * The timestamp when the entity was created.
     */
     private Timestamp createdAt;

    /**
     * The timestamp when the entity was last updated.
     */
     private Timestamp updatedAt;
}
