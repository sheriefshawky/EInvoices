package eg.inv.service.impl;

import eg.inv.domain.Document;
import eg.inv.repository.DocumentRepository;
import eg.inv.service.DocumentService;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Document}.
 */
@Service
@Transactional
public class DocumentServiceImpl implements DocumentService {

    private final Logger log = LoggerFactory.getLogger(DocumentServiceImpl.class);

    private final DocumentRepository documentRepository;

    public DocumentServiceImpl(DocumentRepository documentRepository) {
        this.documentRepository = documentRepository;
    }

    @Override
    public Document save(Document document) {
        log.debug("Request to save Document : {}", document);
        return documentRepository.save(document);
    }

    @Override
    public Optional<Document> partialUpdate(Document document) {
        log.debug("Request to partially update Document : {}", document);

        return documentRepository
            .findById(document.getId())
            .map(existingDocument -> {
                if (document.getDocumentType() != null) {
                    existingDocument.setDocumentType(document.getDocumentType());
                }
                if (document.getDocumentTypeVersion() != null) {
                    existingDocument.setDocumentTypeVersion(document.getDocumentTypeVersion());
                }
                if (document.getDateTimeIssued() != null) {
                    existingDocument.setDateTimeIssued(document.getDateTimeIssued());
                }
                if (document.getTaxpayerActivityCode() != null) {
                    existingDocument.setTaxpayerActivityCode(document.getTaxpayerActivityCode());
                }
                if (document.getInternalId() != null) {
                    existingDocument.setInternalId(document.getInternalId());
                }
                if (document.getPurchaseOrderReference() != null) {
                    existingDocument.setPurchaseOrderReference(document.getPurchaseOrderReference());
                }
                if (document.getPurchaseOrderDescription() != null) {
                    existingDocument.setPurchaseOrderDescription(document.getPurchaseOrderDescription());
                }
                if (document.getSalesOrderReference() != null) {
                    existingDocument.setSalesOrderReference(document.getSalesOrderReference());
                }
                if (document.getSalesOrderDescription() != null) {
                    existingDocument.setSalesOrderDescription(document.getSalesOrderDescription());
                }
                if (document.getProformaInvoiceNumber() != null) {
                    existingDocument.setProformaInvoiceNumber(document.getProformaInvoiceNumber());
                }
                if (document.getTotalSalesAmount() != null) {
                    existingDocument.setTotalSalesAmount(document.getTotalSalesAmount());
                }
                if (document.getTotalDiscountAmount() != null) {
                    existingDocument.setTotalDiscountAmount(document.getTotalDiscountAmount());
                }
                if (document.getNetAmount() != null) {
                    existingDocument.setNetAmount(document.getNetAmount());
                }
                if (document.getExtraDiscountAmount() != null) {
                    existingDocument.setExtraDiscountAmount(document.getExtraDiscountAmount());
                }
                if (document.getTotalItemsDiscountAmount() != null) {
                    existingDocument.setTotalItemsDiscountAmount(document.getTotalItemsDiscountAmount());
                }
                if (document.getTotalAmount() != null) {
                    existingDocument.setTotalAmount(document.getTotalAmount());
                }

                return existingDocument;
            })
            .map(documentRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Document> findAll() {
        log.debug("Request to get all Documents");
        return documentRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Document> findOne(Long id) {
        log.debug("Request to get Document : {}", id);
        return documentRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Document : {}", id);
        documentRepository.deleteById(id);
    }
}
