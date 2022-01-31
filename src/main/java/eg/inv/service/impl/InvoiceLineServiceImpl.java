package eg.inv.service.impl;

import eg.inv.domain.InvoiceLine;
import eg.inv.repository.InvoiceLineRepository;
import eg.inv.service.InvoiceLineService;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link InvoiceLine}.
 */
@Service
@Transactional
public class InvoiceLineServiceImpl implements InvoiceLineService {

    private final Logger log = LoggerFactory.getLogger(InvoiceLineServiceImpl.class);

    private final InvoiceLineRepository invoiceLineRepository;

    public InvoiceLineServiceImpl(InvoiceLineRepository invoiceLineRepository) {
        this.invoiceLineRepository = invoiceLineRepository;
    }

    @Override
    public InvoiceLine save(InvoiceLine invoiceLine) {
        log.debug("Request to save InvoiceLine : {}", invoiceLine);
        return invoiceLineRepository.save(invoiceLine);
    }

    @Override
    public Optional<InvoiceLine> partialUpdate(InvoiceLine invoiceLine) {
        log.debug("Request to partially update InvoiceLine : {}", invoiceLine);

        return invoiceLineRepository
            .findById(invoiceLine.getId())
            .map(existingInvoiceLine -> {
                if (invoiceLine.getDescription() != null) {
                    existingInvoiceLine.setDescription(invoiceLine.getDescription());
                }
                if (invoiceLine.getItemType() != null) {
                    existingInvoiceLine.setItemType(invoiceLine.getItemType());
                }
                if (invoiceLine.getItemCode() != null) {
                    existingInvoiceLine.setItemCode(invoiceLine.getItemCode());
                }
                if (invoiceLine.getUnitType() != null) {
                    existingInvoiceLine.setUnitType(invoiceLine.getUnitType());
                }
                if (invoiceLine.getQuantity() != null) {
                    existingInvoiceLine.setQuantity(invoiceLine.getQuantity());
                }
                if (invoiceLine.getSalesTotal() != null) {
                    existingInvoiceLine.setSalesTotal(invoiceLine.getSalesTotal());
                }
                if (invoiceLine.getTotal() != null) {
                    existingInvoiceLine.setTotal(invoiceLine.getTotal());
                }
                if (invoiceLine.getValueDifference() != null) {
                    existingInvoiceLine.setValueDifference(invoiceLine.getValueDifference());
                }
                if (invoiceLine.getTotalTaxableFees() != null) {
                    existingInvoiceLine.setTotalTaxableFees(invoiceLine.getTotalTaxableFees());
                }
                if (invoiceLine.getNetTotal() != null) {
                    existingInvoiceLine.setNetTotal(invoiceLine.getNetTotal());
                }
                if (invoiceLine.getItemsDiscount() != null) {
                    existingInvoiceLine.setItemsDiscount(invoiceLine.getItemsDiscount());
                }
                if (invoiceLine.getInternalCode() != null) {
                    existingInvoiceLine.setInternalCode(invoiceLine.getInternalCode());
                }

                return existingInvoiceLine;
            })
            .map(invoiceLineRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<InvoiceLine> findAll() {
        log.debug("Request to get all InvoiceLines");
        return invoiceLineRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<InvoiceLine> findOne(Long id) {
        log.debug("Request to get InvoiceLine : {}", id);
        return invoiceLineRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete InvoiceLine : {}", id);
        invoiceLineRepository.deleteById(id);
    }
}
