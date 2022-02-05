package eg.inv.service;

import eg.inv.domain.ItemValue;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link ItemValue}.
 */
public interface ItemValueService {
    /**
     * Save a itemValue.
     *
     * @param itemValue the entity to save.
     * @return the persisted entity.
     */
    ItemValue save(ItemValue itemValue);

    /**
     * Partially updates a itemValue.
     *
     * @param itemValue the entity to update partially.
     * @return the persisted entity.
     */
    Optional<ItemValue> partialUpdate(ItemValue itemValue);

    /**
     * Get all the itemValues.
     *
     * @return the list of entities.
     */
    List<ItemValue> findAll();

    /**
     * Get the "id" itemValue.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ItemValue> findOne(Long id);

    /**
     * Delete the "id" itemValue.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
