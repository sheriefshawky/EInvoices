package eg.inv.web.rest;

import eg.inv.domain.IssuerAddress;
import eg.inv.repository.IssuerAddressRepository;
import eg.inv.service.IssuerAddressService;
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
 * REST controller for managing {@link eg.inv.domain.IssuerAddress}.
 */
@RestController
@RequestMapping("/api")
public class IssuerAddressResource {

    private final Logger log = LoggerFactory.getLogger(IssuerAddressResource.class);

    private static final String ENTITY_NAME = "issuerAddress";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final IssuerAddressService issuerAddressService;

    private final IssuerAddressRepository issuerAddressRepository;

    public IssuerAddressResource(IssuerAddressService issuerAddressService, IssuerAddressRepository issuerAddressRepository) {
        this.issuerAddressService = issuerAddressService;
        this.issuerAddressRepository = issuerAddressRepository;
    }

    /**
     * {@code POST  /issuer-addresses} : Create a new issuerAddress.
     *
     * @param issuerAddress the issuerAddress to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new issuerAddress, or with status {@code 400 (Bad Request)} if the issuerAddress has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/issuer-addresses")
    public ResponseEntity<IssuerAddress> createIssuerAddress(@RequestBody IssuerAddress issuerAddress) throws URISyntaxException {
        log.debug("REST request to save IssuerAddress : {}", issuerAddress);
        if (issuerAddress.getId() != null) {
            throw new BadRequestAlertException("A new issuerAddress cannot already have an ID", ENTITY_NAME, "idexists");
        }
        IssuerAddress result = issuerAddressService.save(issuerAddress);
        return ResponseEntity
            .created(new URI("/api/issuer-addresses/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /issuer-addresses/:id} : Updates an existing issuerAddress.
     *
     * @param id the id of the issuerAddress to save.
     * @param issuerAddress the issuerAddress to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated issuerAddress,
     * or with status {@code 400 (Bad Request)} if the issuerAddress is not valid,
     * or with status {@code 500 (Internal Server Error)} if the issuerAddress couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/issuer-addresses/{id}")
    public ResponseEntity<IssuerAddress> updateIssuerAddress(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody IssuerAddress issuerAddress
    ) throws URISyntaxException {
        log.debug("REST request to update IssuerAddress : {}, {}", id, issuerAddress);
        if (issuerAddress.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, issuerAddress.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!issuerAddressRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        IssuerAddress result = issuerAddressService.save(issuerAddress);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, issuerAddress.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /issuer-addresses/:id} : Partial updates given fields of an existing issuerAddress, field will ignore if it is null
     *
     * @param id the id of the issuerAddress to save.
     * @param issuerAddress the issuerAddress to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated issuerAddress,
     * or with status {@code 400 (Bad Request)} if the issuerAddress is not valid,
     * or with status {@code 404 (Not Found)} if the issuerAddress is not found,
     * or with status {@code 500 (Internal Server Error)} if the issuerAddress couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/issuer-addresses/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<IssuerAddress> partialUpdateIssuerAddress(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody IssuerAddress issuerAddress
    ) throws URISyntaxException {
        log.debug("REST request to partial update IssuerAddress partially : {}, {}", id, issuerAddress);
        if (issuerAddress.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, issuerAddress.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!issuerAddressRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<IssuerAddress> result = issuerAddressService.partialUpdate(issuerAddress);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, issuerAddress.getId().toString())
        );
    }

    /**
     * {@code GET  /issuer-addresses} : get all the issuerAddresses.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of issuerAddresses in body.
     */
    @GetMapping("/issuer-addresses")
    public List<IssuerAddress> getAllIssuerAddresses() {
        log.debug("REST request to get all IssuerAddresses");
        return issuerAddressService.findAll();
    }

    /**
     * {@code GET  /issuer-addresses/:id} : get the "id" issuerAddress.
     *
     * @param id the id of the issuerAddress to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the issuerAddress, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/issuer-addresses/{id}")
    public ResponseEntity<IssuerAddress> getIssuerAddress(@PathVariable Long id) {
        log.debug("REST request to get IssuerAddress : {}", id);
        Optional<IssuerAddress> issuerAddress = issuerAddressService.findOne(id);
        return ResponseUtil.wrapOrNotFound(issuerAddress);
    }

    /**
     * {@code DELETE  /issuer-addresses/:id} : delete the "id" issuerAddress.
     *
     * @param id the id of the issuerAddress to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/issuer-addresses/{id}")
    public ResponseEntity<Void> deleteIssuerAddress(@PathVariable Long id) {
        log.debug("REST request to delete IssuerAddress : {}", id);
        issuerAddressService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
