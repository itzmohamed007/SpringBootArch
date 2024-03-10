package com.arch.controllers;

import com.arch.models.dto.request._Request;
import com.arch.models.dto.response._Response;
import com.arch.services.spec._Service;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * Generic controller with CRUD operations for DTOs.
 *
 * @param <ID> The type of the identifier.
 * @param <Request>> The request DTO type.
 * @param <Response> The response DTO type.
 * @param <Service> The service type implementing _service.
 */
@Slf4j
@Getter
@RestController
@AllArgsConstructor
@NoArgsConstructor(force = true)
public abstract class _Controller<ID, Request extends _Request<ID>, Response extends _Response<ID>, Service extends _Service<ID, Request, Response>> {
    protected Service service;

    @Autowired
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    public final void setService(Service service) {
        this.service = service;
    }

    /**
     * Creates a new entity based on the provided request.
     *
     * @param request The request DTO.
     * @return ResponseEntity containing the created entity or a bad request if creation fails.
     */
    @PostMapping
    public ResponseEntity<Response> create(
            @Valid @RequestBody Request request
    ) {
        assert service != null;
        Optional<Response> response = service.create(request);

        return response.map(ResponseEntity::ok).orElseGet(() ->
                ResponseEntity.badRequest().build());
    }

    /**
     * Retrieves all entities with pagination.
     *
     * @param pageable The pagination information.
     * @return ResponseEntity containing a page of entities.
     */
    @GetMapping("/paged")
    public ResponseEntity<Page<Response>> getAll(Pageable pageable) {
        assert service != null;
        return ResponseEntity.ok(service.getAll(pageable));
    }

    /**
     * Retrieves all entities.
     *
     * @return ResponseEntity containing a list of entities.
     */
    @GetMapping
    public ResponseEntity<List<Response>> getAll() {
        assert service != null;
        return ResponseEntity.ok(service.getAll());
    }

    /**
     * Retrieves an entity by its identifier.
     *
     * @param id The identifier of the entity.
     * @return ResponseEntity containing the retrieved entity or not found response if the entity does not exist.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Response> getById(@Valid @PathVariable("id") ID id) {
        assert service != null;
        return service.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Updates an existing entity based on the provided request.
     *
     * @param request The request DTO.
     * @return ResponseEntity containing the updated entity or not found response if the update fails.
     */
    @PutMapping
    public ResponseEntity<Response> update(
            @Valid @RequestBody Response request
    ) {
        assert service != null;
        Optional<Response> updated = service.update(request);

        return updated.map(ResponseEntity::ok).orElseGet(() ->
                ResponseEntity.notFound().build());
    }

    /**
     * Deletes an entity based on the provided request.
     *
     * @param request The request DTO.
     * @return ResponseEntity with no content if deletion is successful, or not found response if deletion fails.
     */
    @DeleteMapping
    public ResponseEntity<Void> delete(
            @Valid @RequestBody Response request
    ) {
        assert service != null;
        if (service.delete(request)) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
