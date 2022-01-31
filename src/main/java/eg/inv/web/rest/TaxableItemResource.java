package eg.inv.web.rest;

import eg.inv.domain.TaxableItem;
import eg.inv.repository.TaxableItemRepository;
import eg.inv.service.TaxableItemService;
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
 * REST controller for managing {@link eg.inv.domain.TaxableItem}.
 */
@RestController
@RequestMapping("/api")
public class TaxableItemResource {

    private final Logger log = LoggerFactory.getLogger(TaxableItemResource.class);

    private static final String ENTITY_NAME = "taxableItem";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TaxableItemService taxableItemService;

    private final TaxableItemRepository taxableItemRepository;

    public TaxableItemResource(TaxableItemService taxableItemService, TaxableItemRepository taxableItemRepository) {
        this.taxableItemService = taxableItemService;
        this.taxableItemRepository = taxableItemRepository;
    }

    /**
     * {@code POST  /taxable-items} : Create a new taxableItem.
     *
     * @param taxableItem the taxableItem to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new taxableItem, or with status {@code 400 (Bad Request)} if the taxableItem has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/taxable-items")
    public ResponseEntity<TaxableItem> createTaxableItem(@RequestBody TaxableItem taxableItem) throws URISyntaxException {
        log.debug("REST request to save TaxableItem : {}", taxableItem);
        if (taxableItem.getId() != null) {
            throw new BadRequestAlertException("A new taxableItem cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TaxableItem result = taxableItemService.save(taxableItem);
        return ResponseEntity
            .created(new URI("/api/taxable-items/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /taxable-items/:id} : Updates an existing taxableItem.
     *
     * @param id the id of the taxableItem to save.
     * @param taxableItem the taxableItem to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated taxableItem,
     * or with status {@code 400 (Bad Request)} if the taxableItem is not valid,
     * or with status {@code 500 (Internal Server Error)} if the taxableItem couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/taxable-items/{id}")
    public ResponseEntity<TaxableItem> updateTaxableItem(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody TaxableItem taxableItem
    ) throws URISyntaxException {
        log.debug("REST request to update TaxableItem : {}, {}", id, taxableItem);
        if (taxableItem.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, taxableItem.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!taxableItemRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        TaxableItem result = taxableItemService.save(taxableItem);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, taxableItem.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /taxable-items/:id} : Partial updates given fields of an existing taxableItem, field will ignore if it is null
     *
     * @param id the id of the taxableItem to save.
     * @param taxableItem the taxableItem to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated taxableItem,
     * or with status {@code 400 (Bad Request)} if the taxableItem is not valid,
     * or with status {@code 404 (Not Found)} if the taxableItem is not found,
     * or with status {@code 500 (Internal Server Error)} if the taxableItem couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/taxable-items/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<TaxableItem> partialUpdateTaxableItem(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody TaxableItem taxableItem
    ) throws URISyntaxException {
        log.debug("REST request to partial update TaxableItem partially : {}, {}", id, taxableItem);
        if (taxableItem.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, taxableItem.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!taxableItemRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<TaxableItem> result = taxableItemService.partialUpdate(taxableItem);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, taxableItem.getId().toString())
        );
    }

    /**
     * {@code GET  /taxable-items} : get all the taxableItems.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of taxableItems in body.
     */
    @GetMapping("/taxable-items")
    public List<TaxableItem> getAllTaxableItems() {
        log.debug("REST request to get all TaxableItems");
        return taxableItemService.findAll();
    }

    /**
     * {@code GET  /taxable-items/:id} : get the "id" taxableItem.
     *
     * @param id the id of the taxableItem to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the taxableItem, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/taxable-items/{id}")
    public ResponseEntity<TaxableItem> getTaxableItem(@PathVariable Long id) {
        log.debug("REST request to get TaxableItem : {}", id);
        Optional<TaxableItem> taxableItem = taxableItemService.findOne(id);
        return ResponseUtil.wrapOrNotFound(taxableItem);
    }

    /**
     * {@code DELETE  /taxable-items/:id} : delete the "id" taxableItem.
     *
     * @param id the id of the taxableItem to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/taxable-items/{id}")
    public ResponseEntity<Void> deleteTaxableItem(@PathVariable Long id) {
        log.debug("REST request to delete TaxableItem : {}", id);
        taxableItemService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
