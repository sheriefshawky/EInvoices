package eg.inv.web.rest;

import eg.inv.domain.Signature;
import eg.inv.repository.SignatureRepository;
import eg.inv.service.SignatureService;
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
 * REST controller for managing {@link eg.inv.domain.Signature}.
 */
@RestController
@RequestMapping("/api")
public class SignatureResource {

    private final Logger log = LoggerFactory.getLogger(SignatureResource.class);

    private static final String ENTITY_NAME = "signature";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SignatureService signatureService;

    private final SignatureRepository signatureRepository;

    public SignatureResource(SignatureService signatureService, SignatureRepository signatureRepository) {
        this.signatureService = signatureService;
        this.signatureRepository = signatureRepository;
    }

    /**
     * {@code POST  /signatures} : Create a new signature.
     *
     * @param signature the signature to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new signature, or with status {@code 400 (Bad Request)} if the signature has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/signatures")
    public ResponseEntity<Signature> createSignature(@RequestBody Signature signature) throws URISyntaxException {
        log.debug("REST request to save Signature : {}", signature);
        if (signature.getId() != null) {
            throw new BadRequestAlertException("A new signature cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Signature result = signatureService.save(signature);
        return ResponseEntity
            .created(new URI("/api/signatures/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /signatures/:id} : Updates an existing signature.
     *
     * @param id the id of the signature to save.
     * @param signature the signature to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated signature,
     * or with status {@code 400 (Bad Request)} if the signature is not valid,
     * or with status {@code 500 (Internal Server Error)} if the signature couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/signatures/{id}")
    public ResponseEntity<Signature> updateSignature(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Signature signature
    ) throws URISyntaxException {
        log.debug("REST request to update Signature : {}, {}", id, signature);
        if (signature.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, signature.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!signatureRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Signature result = signatureService.save(signature);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, signature.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /signatures/:id} : Partial updates given fields of an existing signature, field will ignore if it is null
     *
     * @param id the id of the signature to save.
     * @param signature the signature to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated signature,
     * or with status {@code 400 (Bad Request)} if the signature is not valid,
     * or with status {@code 404 (Not Found)} if the signature is not found,
     * or with status {@code 500 (Internal Server Error)} if the signature couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/signatures/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Signature> partialUpdateSignature(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Signature signature
    ) throws URISyntaxException {
        log.debug("REST request to partial update Signature partially : {}, {}", id, signature);
        if (signature.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, signature.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!signatureRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Signature> result = signatureService.partialUpdate(signature);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, signature.getId().toString())
        );
    }

    /**
     * {@code GET  /signatures} : get all the signatures.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of signatures in body.
     */
    @GetMapping("/signatures")
    public List<Signature> getAllSignatures() {
        log.debug("REST request to get all Signatures");
        return signatureService.findAll();
    }

    /**
     * {@code GET  /signatures/:id} : get the "id" signature.
     *
     * @param id the id of the signature to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the signature, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/signatures/{id}")
    public ResponseEntity<Signature> getSignature(@PathVariable Long id) {
        log.debug("REST request to get Signature : {}", id);
        Optional<Signature> signature = signatureService.findOne(id);
        return ResponseUtil.wrapOrNotFound(signature);
    }

    /**
     * {@code DELETE  /signatures/:id} : delete the "id" signature.
     *
     * @param id the id of the signature to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/signatures/{id}")
    public ResponseEntity<Void> deleteSignature(@PathVariable Long id) {
        log.debug("REST request to delete Signature : {}", id);
        signatureService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
