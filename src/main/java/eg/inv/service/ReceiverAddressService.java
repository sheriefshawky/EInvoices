package eg.inv.service;

import eg.inv.domain.ReceiverAddress;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link ReceiverAddress}.
 */
public interface ReceiverAddressService {
    /**
     * Save a receiverAddress.
     *
     * @param receiverAddress the entity to save.
     * @return the persisted entity.
     */
    ReceiverAddress save(ReceiverAddress receiverAddress);

    /**
     * Partially updates a receiverAddress.
     *
     * @param receiverAddress the entity to update partially.
     * @return the persisted entity.
     */
    Optional<ReceiverAddress> partialUpdate(ReceiverAddress receiverAddress);

    /**
     * Get all the receiverAddresses.
     *
     * @return the list of entities.
     */
    List<ReceiverAddress> findAll();

    /**
     * Get the "id" receiverAddress.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ReceiverAddress> findOne(Long id);

    /**
     * Delete the "id" receiverAddress.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
