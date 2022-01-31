package eg.inv.service;

import eg.inv.domain.IssuerAddress;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link IssuerAddress}.
 */
public interface IssuerAddressService {
    /**
     * Save a issuerAddress.
     *
     * @param issuerAddress the entity to save.
     * @return the persisted entity.
     */
    IssuerAddress save(IssuerAddress issuerAddress);

    /**
     * Partially updates a issuerAddress.
     *
     * @param issuerAddress the entity to update partially.
     * @return the persisted entity.
     */
    Optional<IssuerAddress> partialUpdate(IssuerAddress issuerAddress);

    /**
     * Get all the issuerAddresses.
     *
     * @return the list of entities.
     */
    List<IssuerAddress> findAll();

    /**
     * Get the "id" issuerAddress.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<IssuerAddress> findOne(Long id);

    /**
     * Delete the "id" issuerAddress.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
