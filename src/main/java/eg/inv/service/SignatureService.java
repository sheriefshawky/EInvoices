package eg.inv.service;

import eg.inv.domain.Signature;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link Signature}.
 */
public interface SignatureService {
    /**
     * Save a signature.
     *
     * @param signature the entity to save.
     * @return the persisted entity.
     */
    Signature save(Signature signature);

    /**
     * Partially updates a signature.
     *
     * @param signature the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Signature> partialUpdate(Signature signature);

    /**
     * Get all the signatures.
     *
     * @return the list of entities.
     */
    List<Signature> findAll();

    /**
     * Get the "id" signature.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Signature> findOne(Long id);

    /**
     * Delete the "id" signature.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
