package eg.inv.service;

import eg.inv.domain.DocumentTypeVersion;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link DocumentTypeVersion}.
 */
public interface DocumentTypeVersionService {
    /**
     * Save a documentTypeVersion.
     *
     * @param documentTypeVersion the entity to save.
     * @return the persisted entity.
     */
    DocumentTypeVersion save(DocumentTypeVersion documentTypeVersion);

    /**
     * Partially updates a documentTypeVersion.
     *
     * @param documentTypeVersion the entity to update partially.
     * @return the persisted entity.
     */
    Optional<DocumentTypeVersion> partialUpdate(DocumentTypeVersion documentTypeVersion);

    /**
     * Get all the documentTypeVersions.
     *
     * @return the list of entities.
     */
    List<DocumentTypeVersion> findAll();

    /**
     * Get the "id" documentTypeVersion.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<DocumentTypeVersion> findOne(Long id);

    /**
     * Delete the "id" documentTypeVersion.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
