package eg.inv.web.rest;

import eg.inv.domain.Receiver;
import eg.inv.repository.ReceiverRepository;
import eg.inv.service.ReceiverService;
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
 * REST controller for managing {@link eg.inv.domain.Receiver}.
 */
@RestController
@RequestMapping("/api")
public class ReceiverResource {

    private final Logger log = LoggerFactory.getLogger(ReceiverResource.class);

    private static final String ENTITY_NAME = "receiver";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ReceiverService receiverService;

    private final ReceiverRepository receiverRepository;

    public ReceiverResource(ReceiverService receiverService, ReceiverRepository receiverRepository) {
        this.receiverService = receiverService;
        this.receiverRepository = receiverRepository;
    }

    /**
     * {@code POST  /receivers} : Create a new receiver.
     *
     * @param receiver the receiver to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new receiver, or with status {@code 400 (Bad Request)} if the receiver has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/receivers")
    public ResponseEntity<Receiver> createReceiver(@RequestBody Receiver receiver) throws URISyntaxException {
        log.debug("REST request to save Receiver : {}", receiver);
        if (receiver.getId() != null) {
            throw new BadRequestAlertException("A new receiver cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Receiver result = receiverService.save(receiver);
        return ResponseEntity
            .created(new URI("/api/receivers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /receivers/:id} : Updates an existing receiver.
     *
     * @param id the id of the receiver to save.
     * @param receiver the receiver to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated receiver,
     * or with status {@code 400 (Bad Request)} if the receiver is not valid,
     * or with status {@code 500 (Internal Server Error)} if the receiver couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/receivers/{id}")
    public ResponseEntity<Receiver> updateReceiver(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Receiver receiver
    ) throws URISyntaxException {
        log.debug("REST request to update Receiver : {}, {}", id, receiver);
        if (receiver.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, receiver.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!receiverRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Receiver result = receiverService.save(receiver);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, receiver.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /receivers/:id} : Partial updates given fields of an existing receiver, field will ignore if it is null
     *
     * @param id the id of the receiver to save.
     * @param receiver the receiver to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated receiver,
     * or with status {@code 400 (Bad Request)} if the receiver is not valid,
     * or with status {@code 404 (Not Found)} if the receiver is not found,
     * or with status {@code 500 (Internal Server Error)} if the receiver couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/receivers/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Receiver> partialUpdateReceiver(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Receiver receiver
    ) throws URISyntaxException {
        log.debug("REST request to partial update Receiver partially : {}, {}", id, receiver);
        if (receiver.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, receiver.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!receiverRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Receiver> result = receiverService.partialUpdate(receiver);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, receiver.getId().toString())
        );
    }

    /**
     * {@code GET  /receivers} : get all the receivers.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of receivers in body.
     */
    @GetMapping("/receivers")
    public List<Receiver> getAllReceivers() {
        log.debug("REST request to get all Receivers");
        return receiverService.findAll();
    }

    /**
     * {@code GET  /receivers/:id} : get the "id" receiver.
     *
     * @param id the id of the receiver to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the receiver, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/receivers/{id}")
    public ResponseEntity<Receiver> getReceiver(@PathVariable Long id) {
        log.debug("REST request to get Receiver : {}", id);
        Optional<Receiver> receiver = receiverService.findOne(id);
        return ResponseUtil.wrapOrNotFound(receiver);
    }

    /**
     * {@code DELETE  /receivers/:id} : delete the "id" receiver.
     *
     * @param id the id of the receiver to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/receivers/{id}")
    public ResponseEntity<Void> deleteReceiver(@PathVariable Long id) {
        log.debug("REST request to delete Receiver : {}", id);
        receiverService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
