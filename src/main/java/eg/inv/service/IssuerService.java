package eg.inv.service;

import eg.inv.domain.Issuer;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link Issuer}.
 */
public interface IssuerService {
    /**
     * Save a issuer.
     *
     * @param issuer the entity to save.
     * @return the persisted entity.
     */
    Issuer save(Issuer issuer);

    /**
     * Partially updates a issuer.
     *
     * @param issuer the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Issuer> partialUpdate(Issuer issuer);

    /**
     * Get all the issuers.
     *
     * @return the list of entities.
     */
    List<Issuer> findAll();

    /**
     * Get the "id" issuer.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Issuer> findOne(Long id);

    /**
     * Delete the "id" issuer.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
