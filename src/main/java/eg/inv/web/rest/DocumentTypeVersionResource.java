package eg.inv.web.rest;

import eg.inv.domain.DocumentTypeVersion;
import eg.inv.repository.DocumentTypeVersionRepository;
import eg.inv.service.DocumentTypeVersionService;
import eg.inv.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link eg.inv.domain.DocumentTypeVersion}.
 */
@RestController
@RequestMapping("/api")
public class DocumentTypeVersionResource {

    private final Logger log = LoggerFactory.getLogger(DocumentTypeVersionResource.class);

    private static final String ENTITY_NAME = "documentTypeVersion";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DocumentTypeVersionService documentTypeVersionService;

    private final DocumentTypeVersionRepository documentTypeVersionRepository;

    public DocumentTypeVersionResource(
        DocumentTypeVersionService documentTypeVersionService,
        DocumentTypeVersionRepository documentTypeVersionRepository
    ) {
        this.documentTypeVersionService = documentTypeVersionService;
        this.documentTypeVersionRepository = documentTypeVersionRepository;
    }

    /**
     * {@code POST  /document-type-versions} : Create a new documentTypeVersion.
     *
     * @param documentTypeVersion the documentTypeVersion to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new documentTypeVersion, or with status {@code 400 (Bad Request)} if the documentTypeVersion has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/document-type-versions")
    public ResponseEntity<DocumentTypeVersion> createDocumentTypeVersion(@RequestBody DocumentTypeVersion documentTypeVersion)
        throws URISyntaxException {
        log.debug("REST request to save DocumentTypeVersion : {}", documentTypeVersion);
        if (documentTypeVersion.getId() != null) {
            throw new BadRequestAlertException("A new documentTypeVersion cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DocumentTypeVersion result = documentTypeVersionService.save(documentTypeVersion);
        return ResponseEntity
            .created(new URI("/api/document-type-versions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /document-type-versions/:id} : Updates an existing documentTypeVersion.
     *
     * @param id the id of the documentTypeVersion to save.
     * @param documentTypeVersion the documentTypeVersion to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated documentTypeVersion,
     * or with status {@code 400 (Bad Request)} if the documentTypeVersion is not valid,
     * or with status {@code 500 (Internal Server Error)} if the documentTypeVersion couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/document-type-versions/{id}")
    public ResponseEntity<DocumentTypeVersion> updateDocumentTypeVersion(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody DocumentTypeVersion documentTypeVersion
    ) throws URISyntaxException {
        log.debug("REST request to update DocumentTypeVersion : {}, {}", id, documentTypeVersion);
        if (documentTypeVersion.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, documentTypeVersion.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!documentTypeVersionRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        DocumentTypeVersion result = documentTypeVersionService.save(documentTypeVersion);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, documentTypeVersion.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /document-type-versions/:id} : Partial updates given fields of an existing documentTypeVersion, field will ignore if it is null
     *
     * @param id the id of the documentTypeVersion to save.
     * @param documentTypeVersion the documentTypeVersion to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated documentTypeVersion,
     * or with status {@code 400 (Bad Request)} if the documentTypeVersion is not valid,
     * or with status {@code 404 (Not Found)} if the documentTypeVersion is not found,
     * or with status {@code 500 (Internal Server Error)} if the documentTypeVersion couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/document-type-versions/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<DocumentTypeVersion> partialUpdateDocumentTypeVersion(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody DocumentTypeVersion documentTypeVersion
    ) throws URISyntaxException {
        log.debug("REST request to partial update DocumentTypeVersion partially : {}, {}", id, documentTypeVersion);
        if (documentTypeVersion.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, documentTypeVersion.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!documentTypeVersionRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<DocumentTypeVersion> result = documentTypeVersionService.partialUpdate(documentTypeVersion);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, documentTypeVersion.getId().toString())
        );
    }

    /**
     * {@code GET  /document-type-versions} : get all the documentTypeVersions.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of documentTypeVersions in body.
     */
    @GetMapping("/document-type-versions")
    public List<DocumentTypeVersion> getAllDocumentTypeVersions() {
        log.debug("REST request to get all DocumentTypeVersions");
        return documentTypeVersionService.findAll();
    }

    /**
     * {@code GET  /document-type-versions/:id} : get the "id" documentTypeVersion.
     *
     * @param id the id of the documentTypeVersion to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the documentTypeVersion, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/document-type-versions/{id}")
    public ResponseEntity<DocumentTypeVersion> getDocumentTypeVersion(@PathVariable Long id) {
        log.debug("REST request to get DocumentTypeVersion : {}", id);
        Optional<DocumentTypeVersion> documentTypeVersion = documentTypeVersionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(documentTypeVersion);
    }

    /**
     * {@code DELETE  /document-type-versions/:id} : delete the "id" documentTypeVersion.
     *
     * @param id the id of the documentTypeVersion to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/document-type-versions/{id}")
    public ResponseEntity<Void> deleteDocumentTypeVersion(@PathVariable Long id) {
        log.debug("REST request to delete DocumentTypeVersion : {}", id);
        documentTypeVersionService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
