package eg.inv.service;

import eg.inv.domain.TaxableItem;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link TaxableItem}.
 */
public interface TaxableItemService {
    /**
     * Save a taxableItem.
     *
     * @param taxableItem the entity to save.
     * @return the persisted entity.
     */
    TaxableItem save(TaxableItem taxableItem);

    /**
     * Partially updates a taxableItem.
     *
     * @param taxableItem the entity to update partially.
     * @return the persisted entity.
     */
    Optional<TaxableItem> partialUpdate(TaxableItem taxableItem);

    /**
     * Get all the taxableItems.
     *
     * @return the list of entities.
     */
    List<TaxableItem> findAll();

    /**
     * Get the "id" taxableItem.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<TaxableItem> findOne(Long id);

    /**
     * Delete the "id" taxableItem.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
