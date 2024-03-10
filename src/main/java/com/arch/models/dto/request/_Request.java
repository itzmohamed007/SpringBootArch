package com.arch.models.dto.request;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Marker interface for request data transfer objects (DTOs),
 * This interface serves as a common type for all request DTOs.
 */
public interface _Request<ID> extends Serializable {
    ID getId();

    /**
     * Gets the timestamp when the DTO was created.
     *
     * @return The creation timestamp of the DTO.
     */
    Timestamp getCreatedAt();

    /**
     * Gets the timestamp when the DTO was last updated.
     *
     * @return The last update timestamp of the DTO.
     */
    Timestamp getUpdatedAt();
}
