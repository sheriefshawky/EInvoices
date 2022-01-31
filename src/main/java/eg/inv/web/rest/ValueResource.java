package eg.inv.web.rest;

import eg.inv.domain.Value;
import eg.inv.repository.ValueRepository;
import eg.inv.service.ValueService;
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
 * REST controller for managing {@link eg.inv.domain.Value}.
 */
@RestController
@RequestMapping("/api")
public class ValueResource {

    private final Logger log = LoggerFactory.getLogger(ValueResource.class);

    private static final String ENTITY_NAME = "value";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ValueService valueService;

    private final ValueRepository valueRepository;

    public ValueResource(ValueService valueService, ValueRepository valueRepository) {
        this.valueService = valueService;
        this.valueRepository = valueRepository;
    }

    /**
     * {@code POST  /values} : Create a new value.
     *
     * @param value the value to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new value, or with status {@code 400 (Bad Request)} if the value has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/values")
    public ResponseEntity<Value> createValue(@RequestBody Value value) throws URISyntaxException {
        log.debug("REST request to save Value : {}", value);
        if (value.getId() != null) {
            throw new BadRequestAlertException("A new value cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Value result = valueService.save(value);
        return ResponseEntity
            .created(new URI("/api/values/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /values/:id} : Updates an existing value.
     *
     * @param id the id of the value to save.
     * @param value the value to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated value,
     * or with status {@code 400 (Bad Request)} if the value is not valid,
     * or with status {@code 500 (Internal Server Error)} if the value couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/values/{id}")
    public ResponseEntity<Value> updateValue(@PathVariable(value = "id", required = false) final Long id, @RequestBody Value value)
        throws URISyntaxException {
        log.debug("REST request to update Value : {}, {}", id, value);
        if (value.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, value.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!valueRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Value result = valueService.save(value);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, value.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /values/:id} : Partial updates given fields of an existing value, field will ignore if it is null
     *
     * @param id the id of the value to save.
     * @param value the value to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated value,
     * or with status {@code 400 (Bad Request)} if the value is not valid,
     * or with status {@code 404 (Not Found)} if the value is not found,
     * or with status {@code 500 (Internal Server Error)} if the value couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/values/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Value> partialUpdateValue(@PathVariable(value = "id", required = false) final Long id, @RequestBody Value value)
        throws URISyntaxException {
        log.debug("REST request to partial update Value partially : {}, {}", id, value);
        if (value.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, value.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!valueRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Value> result = valueService.partialUpdate(value);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, value.getId().toString())
        );
    }

    /**
     * {@code GET  /values} : get all the values.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of values in body.
     */
    @GetMapping("/values")
    public List<Value> getAllValues() {
        log.debug("REST request to get all Values");
        return valueService.findAll();
    }

    /**
     * {@code GET  /values/:id} : get the "id" value.
     *
     * @param id the id of the value to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the value, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/values/{id}")
    public ResponseEntity<Value> getValue(@PathVariable Long id) {
        log.debug("REST request to get Value : {}", id);
        Optional<Value> value = valueService.findOne(id);
        return ResponseUtil.wrapOrNotFound(value);
    }

    /**
     * {@code DELETE  /values/:id} : delete the "id" value.
     *
     * @param id the id of the value to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/values/{id}")
    public ResponseEntity<Void> deleteValue(@PathVariable Long id) {
        log.debug("REST request to delete Value : {}", id);
        valueService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
