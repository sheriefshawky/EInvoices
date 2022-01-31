package eg.inv.web.rest;

import eg.inv.domain.ReceiverAddress;
import eg.inv.repository.ReceiverAddressRepository;
import eg.inv.service.ReceiverAddressService;
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
 * REST controller for managing {@link eg.inv.domain.ReceiverAddress}.
 */
@RestController
@RequestMapping("/api")
public class ReceiverAddressResource {

    private final Logger log = LoggerFactory.getLogger(ReceiverAddressResource.class);

    private static final String ENTITY_NAME = "receiverAddress";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ReceiverAddressService receiverAddressService;

    private final ReceiverAddressRepository receiverAddressRepository;

    public ReceiverAddressResource(ReceiverAddressService receiverAddressService, ReceiverAddressRepository receiverAddressRepository) {
        this.receiverAddressService = receiverAddressService;
        this.receiverAddressRepository = receiverAddressRepository;
    }

    /**
     * {@code POST  /receiver-addresses} : Create a new receiverAddress.
     *
     * @param receiverAddress the receiverAddress to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new receiverAddress, or with status {@code 400 (Bad Request)} if the receiverAddress has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/receiver-addresses")
    public ResponseEntity<ReceiverAddress> createReceiverAddress(@RequestBody ReceiverAddress receiverAddress) throws URISyntaxException {
        log.debug("REST request to save ReceiverAddress : {}", receiverAddress);
        if (receiverAddress.getId() != null) {
            throw new BadRequestAlertException("A new receiverAddress cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ReceiverAddress result = receiverAddressService.save(receiverAddress);
        return ResponseEntity
            .created(new URI("/api/receiver-addresses/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /receiver-addresses/:id} : Updates an existing receiverAddress.
     *
     * @param id the id of the receiverAddress to save.
     * @param receiverAddress the receiverAddress to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated receiverAddress,
     * or with status {@code 400 (Bad Request)} if the receiverAddress is not valid,
     * or with status {@code 500 (Internal Server Error)} if the receiverAddress couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/receiver-addresses/{id}")
    public ResponseEntity<ReceiverAddress> updateReceiverAddress(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ReceiverAddress receiverAddress
    ) throws URISyntaxException {
        log.debug("REST request to update ReceiverAddress : {}, {}", id, receiverAddress);
        if (receiverAddress.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, receiverAddress.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!receiverAddressRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ReceiverAddress result = receiverAddressService.save(receiverAddress);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, receiverAddress.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /receiver-addresses/:id} : Partial updates given fields of an existing receiverAddress, field will ignore if it is null
     *
     * @param id the id of the receiverAddress to save.
     * @param receiverAddress the receiverAddress to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated receiverAddress,
     * or with status {@code 400 (Bad Request)} if the receiverAddress is not valid,
     * or with status {@code 404 (Not Found)} if the receiverAddress is not found,
     * or with status {@code 500 (Internal Server Error)} if the receiverAddress couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/receiver-addresses/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ReceiverAddress> partialUpdateReceiverAddress(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ReceiverAddress receiverAddress
    ) throws URISyntaxException {
        log.debug("REST request to partial update ReceiverAddress partially : {}, {}", id, receiverAddress);
        if (receiverAddress.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, receiverAddress.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!receiverAddressRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ReceiverAddress> result = receiverAddressService.partialUpdate(receiverAddress);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, receiverAddress.getId().toString())
        );
    }

    /**
     * {@code GET  /receiver-addresses} : get all the receiverAddresses.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of receiverAddresses in body.
     */
    @GetMapping("/receiver-addresses")
    public List<ReceiverAddress> getAllReceiverAddresses() {
        log.debug("REST request to get all ReceiverAddresses");
        return receiverAddressService.findAll();
    }

    /**
     * {@code GET  /receiver-addresses/:id} : get the "id" receiverAddress.
     *
     * @param id the id of the receiverAddress to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the receiverAddress, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/receiver-addresses/{id}")
    public ResponseEntity<ReceiverAddress> getReceiverAddress(@PathVariable Long id) {
        log.debug("REST request to get ReceiverAddress : {}", id);
        Optional<ReceiverAddress> receiverAddress = receiverAddressService.findOne(id);
        return ResponseUtil.wrapOrNotFound(receiverAddress);
    }

    /**
     * {@code DELETE  /receiver-addresses/:id} : delete the "id" receiverAddress.
     *
     * @param id the id of the receiverAddress to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/receiver-addresses/{id}")
    public ResponseEntity<Void> deleteReceiverAddress(@PathVariable Long id) {
        log.debug("REST request to delete ReceiverAddress : {}", id);
        receiverAddressService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
