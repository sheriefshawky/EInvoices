package eg.inv.service;

import eg.inv.domain.Value;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link Value}.
 */
public interface ValueService {
    /**
     * Save a value.
     *
     * @param value the entity to save.
     * @return the persisted entity.
     */
    Value save(Value value);

    /**
     * Partially updates a value.
     *
     * @param value the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Value> partialUpdate(Value value);

    /**
     * Get all the values.
     *
     * @return the list of entities.
     */
    List<Value> findAll();

    /**
     * Get the "id" value.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Value> findOne(Long id);

    /**
     * Delete the "id" value.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
