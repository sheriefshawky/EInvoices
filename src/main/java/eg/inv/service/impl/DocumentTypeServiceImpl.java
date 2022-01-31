package eg.inv.service.impl;

import eg.inv.domain.DocumentType;
import eg.inv.repository.DocumentTypeRepository;
import eg.inv.service.DocumentTypeService;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link DocumentType}.
 */
@Service
@Transactional
public class DocumentTypeServiceImpl implements DocumentTypeService {

    private final Logger log = LoggerFactory.getLogger(DocumentTypeServiceImpl.class);

    private final DocumentTypeRepository documentTypeRepository;

    public DocumentTypeServiceImpl(DocumentTypeRepository documentTypeRepository) {
        this.documentTypeRepository = documentTypeRepository;
    }

    @Override
    public DocumentType save(DocumentType documentType) {
        log.debug("Request to save DocumentType : {}", documentType);
        return documentTypeRepository.save(documentType);
    }

    @Override
    public Optional<DocumentType> partialUpdate(DocumentType documentType) {
        log.debug("Request to partially update DocumentType : {}", documentType);

        return documentTypeRepository
            .findById(documentType.getId())
            .map(existingDocumentType -> {
                if (documentType.getName() != null) {
                    existingDocumentType.setName(documentType.getName());
                }
                if (documentType.getDescription() != null) {
                    existingDocumentType.setDescription(documentType.getDescription());
                }
                if (documentType.getActiveFrom() != null) {
                    existingDocumentType.setActiveFrom(documentType.getActiveFrom());
                }
                if (documentType.getActiveTo() != null) {
                    existingDocumentType.setActiveTo(documentType.getActiveTo());
                }

                return existingDocumentType;
            })
            .map(documentTypeRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<DocumentType> findAll() {
        log.debug("Request to get all DocumentTypes");
        return documentTypeRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<DocumentType> findOne(Long id) {
        log.debug("Request to get DocumentType : {}", id);
        return documentTypeRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete DocumentType : {}", id);
        documentTypeRepository.deleteById(id);
    }
}
