package com.arch.services.impl;

import com.arch.exceptions.customs.ModularException;
import com.arch.mappers._Mapper;
import com.arch.models.dto.request._Request;
import com.arch.models.dto.response._Response;
import com.arch.models.entities._Entity;
import com.arch.services.spec._Service;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.Optional;

/**
 * Generic service implementation with common CRUD operations.
 *
 * @param <Req>        The request DTO type.
 * @param <Res>        The response DTO type.
 * @param <Entity>     The entity type.
 * @param <Repository> The repository type extending JpaRepository<Entity, UUID>.
 * @param <Mapper>     The mapper type implementing _Mapper<Req, Res, Entity>.
 */
@Slf4j
@Validated
@AllArgsConstructor
@NoArgsConstructor(force = true)
public abstract class _AbstractService<ID, Req extends _Request<ID>, Res extends _Response<ID>, Entity extends _Entity<ID>, Repository extends JpaRepository<Entity, ID>, Mapper extends _Mapper<ID, Req, Res, Entity>> implements _Service<ID, Req, Res> {

    Mapper mapper;
    Repository repository;

    @Autowired
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    public final void setRepository(Repository repository) {
        this.repository = repository;
    }

    @Autowired
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    public final void setMapper(Mapper mapper) {
        this.mapper = mapper;
    }

    /**
     * Retrieves a list of all entities.
     *
     * @return List of response DTOs representing all entities.
     */
    @Transactional
    public List<Res> getAll() {
        assertMapperAndRepositoryAreNotNull();
        return mapper.toResponse(
                repository.findAll()
        );
    }

    /**
     * Retrieves all entities in a paginated form.
     *
     * @param pageable Pagination information.
     * @return Page of response DTOs.
     */
    @Transactional
    public Page<Res> getAll(Pageable pageable) {
        assertMapperAndRepositoryAreNotNull();
        return repository.findAll(pageable)
                .map(mapper::toResponse);
    }

    /**
     * Creates a new entity based on the provided request DTO.
     *
     * @param request DTO containing data for entity creation.
     * @return Optional containing the response DTO of the created entity.
     */
    @Transactional
    public Optional<Res> create(@Valid Req request) {
        assertMapperAndRepositoryAreNotNull();
        Entity entityToCreate = mapper.toEntityFromRequest(request);
        try {
            Entity createdEntity = repository.saveAndFlush(entityToCreate);
            return Optional.of(mapper.toResponse(createdEntity));
        } catch (Exception e) {
            String entityToCreateName = entityToCreate.getClass().getSimpleName();
            throw new ModularException("an error occurred while creating " + entityToCreateName + ", " + e.getMessage());
        }
    }

    /**
     * Updates an existing entity based on the provided response DTO.
     *
     * @param response DTO containing updated data.
     * @return Optional containing the response DTO of the updated entity.
     * @apiNote This method have a vulnerability, if the `entity to update's primary key` is not present in the database, it will create a new row
     */
    @Transactional
    public Optional<Res> update(@Valid Res response) {
        assertMapperAndRepositoryAreNotNull();
        Entity entityToUpdate = mapper.toEntityFromResponse(response);
        try {
            Entity updatedEntity = repository.saveAndFlush(entityToUpdate);
            return Optional.of(mapper.toResponse(updatedEntity));
        } catch (Exception e) {
            String entityToUpdateName = entityToUpdate.getClass().getSimpleName();
            throw new ModularException("an error occurred while updating " + entityToUpdateName + ", " + e.getMessage());
        }
    }

    /**
     * Retrieves an entity by its unique identifier.
     *
     * @param id Unique identifier of the entity.
     * @return Optional containing the response DTO of the found entity.
     */
    @Transactional
    public Optional<Res> getById(ID id) {
        assertMapperAndRepositoryAreNotNull();
        return repository.findById(id)
                .map(mapper::toResponse);
    }

    /**
     * Deletes an entity based on the provided response DTO.
     *
     * @param response DTO containing data for entity deletion.
     * @return Boolean indicating the success of the deletion operation.
     */
    @Transactional
    public Boolean delete(@Valid Res response) {
        assertMapperAndRepositoryAreNotNull();
        Entity entityToDelete = mapper.toEntityFromResponse(response);
        String entityToDeleteName = entityToDelete.getClass().getSimpleName();

        if (!repository.existsById(entityToDelete.getId())) {
            throw new ModularException(entityToDeleteName + " not found with id: " + entityToDelete.getId());
        }
        try {
            repository.delete(entityToDelete);
        } catch (Exception e) {
            throw new ModularException("an error occurred while deleting " + entityToDeleteName + ", " + e.getMessage());
        }
        return true;
    }

    private void assertMapperAndRepositoryAreNotNull() {
        assert this.mapper != null;
        assert this.repository != null;
    }
}