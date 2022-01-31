package eg.inv.service;

import eg.inv.domain.InvoiceLine;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link InvoiceLine}.
 */
public interface InvoiceLineService {
    /**
     * Save a invoiceLine.
     *
     * @param invoiceLine the entity to save.
     * @return the persisted entity.
     */
    InvoiceLine save(InvoiceLine invoiceLine);

    /**
     * Partially updates a invoiceLine.
     *
     * @param invoiceLine the entity to update partially.
     * @return the persisted entity.
     */
    Optional<InvoiceLine> partialUpdate(InvoiceLine invoiceLine);

    /**
     * Get all the invoiceLines.
     *
     * @return the list of entities.
     */
    List<InvoiceLine> findAll();

    /**
     * Get the "id" invoiceLine.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<InvoiceLine> findOne(Long id);

    /**
     * Delete the "id" invoiceLine.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
