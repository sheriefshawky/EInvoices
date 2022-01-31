package eg.inv.service.impl;

import eg.inv.domain.DocumentTypeVersion;
import eg.inv.repository.DocumentTypeVersionRepository;
import eg.inv.service.DocumentTypeVersionService;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link DocumentTypeVersion}.
 */
@Service
@Transactional
public class DocumentTypeVersionServiceImpl implements DocumentTypeVersionService {

    private final Logger log = LoggerFactory.getLogger(DocumentTypeVersionServiceImpl.class);

    private final DocumentTypeVersionRepository documentTypeVersionRepository;

    public DocumentTypeVersionServiceImpl(DocumentTypeVersionRepository documentTypeVersionRepository) {
        this.documentTypeVersionRepository = documentTypeVersionRepository;
    }

    @Override
    public DocumentTypeVersion save(DocumentTypeVersion documentTypeVersion) {
        log.debug("Request to save DocumentTypeVersion : {}", documentTypeVersion);
        return documentTypeVersionRepository.save(documentTypeVersion);
    }

    @Override
    public Optional<DocumentTypeVersion> partialUpdate(DocumentTypeVersion documentTypeVersion) {
        log.debug("Request to partially update DocumentTypeVersion : {}", documentTypeVersion);

        return documentTypeVersionRepository
            .findById(documentTypeVersion.getId())
            .map(existingDocumentTypeVersion -> {
                if (documentTypeVersion.getName() != null) {
                    existingDocumentTypeVersion.setName(documentTypeVersion.getName());
                }
                if (documentTypeVersion.getDescription() != null) {
                    existingDocumentTypeVersion.setDescription(documentTypeVersion.getDescription());
                }
                if (documentTypeVersion.getVersionNumber() != null) {
                    existingDocumentTypeVersion.setVersionNumber(documentTypeVersion.getVersionNumber());
                }
                if (documentTypeVersion.getStatus() != null) {
                    existingDocumentTypeVersion.setStatus(documentTypeVersion.getStatus());
                }
                if (documentTypeVersion.getActiveFrom() != null) {
                    existingDocumentTypeVersion.setActiveFrom(documentTypeVersion.getActiveFrom());
                }
                if (documentTypeVersion.getActiveTo() != null) {
                    existingDocumentTypeVersion.setActiveTo(documentTypeVersion.getActiveTo());
                }

                return existingDocumentTypeVersion;
            })
            .map(documentTypeVersionRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<DocumentTypeVersion> findAll() {
        log.debug("Request to get all DocumentTypeVersions");
        return documentTypeVersionRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<DocumentTypeVersion> findOne(Long id) {
        log.debug("Request to get DocumentTypeVersion : {}", id);
        return documentTypeVersionRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete DocumentTypeVersion : {}", id);
        documentTypeVersionRepository.deleteById(id);
    }
}
