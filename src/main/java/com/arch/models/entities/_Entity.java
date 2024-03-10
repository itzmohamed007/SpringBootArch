package com.arch.models.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.annotation.ReadOnlyProperty;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;

/**
 * This interface represents the base entity with common fields like ID, creation timestamp,
 * update timestamp, and version. Entities in the application should implement this interface.
 *  @author <a href="mailto:ouharri.outman@gmail.com">ouharri</a>
 *
 * @param <ID> The type of the entity's identifier.
 */
public interface _Entity<ID> extends Serializable {

    /**
     * Gets the unique identifier of the entity.
     *
     * @return The entity's identifier.
     */
    ID getId();

    /**
     * Gets the timestamp when the entity was created.
     *
     * @return The timestamp of creation.
     */
    @CreationTimestamp
    @ReadOnlyProperty
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "created_at", nullable = false, updatable = false)
    java.sql.Timestamp getCreatedAt();

    /**
     * Gets the timestamp when the entity was last updated.
     *
     * @return The timestamp of the last update.
     */
    @UpdateTimestamp
    @ReadOnlyProperty
    @LastModifiedDate
    @Column(name = "updated_at")
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    java.sql.Timestamp getUpdatedAt();
}