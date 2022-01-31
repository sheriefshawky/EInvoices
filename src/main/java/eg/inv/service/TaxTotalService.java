package eg.inv.service;

import eg.inv.domain.TaxTotal;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link TaxTotal}.
 */
public interface TaxTotalService {
    /**
     * Save a taxTotal.
     *
     * @param taxTotal the entity to save.
     * @return the persisted entity.
     */
    TaxTotal save(TaxTotal taxTotal);

    /**
     * Partially updates a taxTotal.
     *
     * @param taxTotal the entity to update partially.
     * @return the persisted entity.
     */
    Optional<TaxTotal> partialUpdate(TaxTotal taxTotal);

    /**
     * Get all the taxTotals.
     *
     * @return the list of entities.
     */
    List<TaxTotal> findAll();

    /**
     * Get the "id" taxTotal.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<TaxTotal> findOne(Long id);

    /**
     * Delete the "id" taxTotal.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
