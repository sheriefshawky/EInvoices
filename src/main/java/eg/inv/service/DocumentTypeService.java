package eg.inv.service;

import eg.inv.domain.DocumentType;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link DocumentType}.
 */
public interface DocumentTypeService {
    /**
     * Save a documentType.
     *
     * @param documentType the entity to save.
     * @return the persisted entity.
     */
    DocumentType save(DocumentType documentType);

    /**
     * Partially updates a documentType.
     *
     * @param documentType the entity to update partially.
     * @return the persisted entity.
     */
    Optional<DocumentType> partialUpdate(DocumentType documentType);

    /**
     * Get all the documentTypes.
     *
     * @return the list of entities.
     */
    List<DocumentType> findAll();

    /**
     * Get the "id" documentType.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<DocumentType> findOne(Long id);

    /**
     * Delete the "id" documentType.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
