package eg.inv.service;

import eg.inv.domain.Delivery;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link Delivery}.
 */
public interface DeliveryService {
    /**
     * Save a delivery.
     *
     * @param delivery the entity to save.
     * @return the persisted entity.
     */
    Delivery save(Delivery delivery);

    /**
     * Partially updates a delivery.
     *
     * @param delivery the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Delivery> partialUpdate(Delivery delivery);

    /**
     * Get all the deliveries.
     *
     * @return the list of entities.
     */
    List<Delivery> findAll();

    /**
     * Get the "id" delivery.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Delivery> findOne(Long id);

    /**
     * Delete the "id" delivery.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
