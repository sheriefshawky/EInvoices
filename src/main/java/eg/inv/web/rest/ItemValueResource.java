package eg.inv.web.rest;

import eg.inv.domain.ItemValue;
import eg.inv.repository.ItemValueRepository;
import eg.inv.service.ItemValueService;
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
 * REST controller for managing {@link eg.inv.domain.ItemValue}.
 */
@RestController
@RequestMapping("/api")
public class ItemValueResource {

    private final Logger log = LoggerFactory.getLogger(ItemValueResource.class);

    private static final String ENTITY_NAME = "itemValue";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ItemValueService itemValueService;

    private final ItemValueRepository itemValueRepository;

    public ItemValueResource(ItemValueService itemValueService, ItemValueRepository itemValueRepository) {
        this.itemValueService = itemValueService;
        this.itemValueRepository = itemValueRepository;
    }

    /**
     * {@code POST  /item-values} : Create a new itemValue.
     *
     * @param itemValue the itemValue to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new itemValue, or with status {@code 400 (Bad Request)} if the itemValue has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/item-values")
    public ResponseEntity<ItemValue> createItemValue(@RequestBody ItemValue itemValue) throws URISyntaxException {
        log.debug("REST request to save ItemValue : {}", itemValue);
        if (itemValue.getId() != null) {
            throw new BadRequestAlertException("A new itemValue cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ItemValue result = itemValueService.save(itemValue);
        return ResponseEntity
            .created(new URI("/api/item-values/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /item-values/:id} : Updates an existing itemValue.
     *
     * @param id the id of the itemValue to save.
     * @param itemValue the itemValue to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated itemValue,
     * or with status {@code 400 (Bad Request)} if the itemValue is not valid,
     * or with status {@code 500 (Internal Server Error)} if the itemValue couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/item-values/{id}")
    public ResponseEntity<ItemValue> updateItemValue(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ItemValue itemValue
    ) throws URISyntaxException {
        log.debug("REST request to update ItemValue : {}, {}", id, itemValue);
        if (itemValue.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, itemValue.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!itemValueRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ItemValue result = itemValueService.save(itemValue);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, itemValue.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /item-values/:id} : Partial updates given fields of an existing itemValue, field will ignore if it is null
     *
     * @param id the id of the itemValue to save.
     * @param itemValue the itemValue to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated itemValue,
     * or with status {@code 400 (Bad Request)} if the itemValue is not valid,
     * or with status {@code 404 (Not Found)} if the itemValue is not found,
     * or with status {@code 500 (Internal Server Error)} if the itemValue couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/item-values/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ItemValue> partialUpdateItemValue(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ItemValue itemValue
    ) throws URISyntaxException {
        log.debug("REST request to partial update ItemValue partially : {}, {}", id, itemValue);
        if (itemValue.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, itemValue.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!itemValueRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ItemValue> result = itemValueService.partialUpdate(itemValue);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, itemValue.getId().toString())
        );
    }

    /**
     * {@code GET  /item-values} : get all the itemValues.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of itemValues in body.
     */
    @GetMapping("/item-values")
    public List<ItemValue> getAllItemValues() {
        log.debug("REST request to get all ItemValues");
        return itemValueService.findAll();
    }

    /**
     * {@code GET  /item-values/:id} : get the "id" itemValue.
     *
     * @param id the id of the itemValue to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the itemValue, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/item-values/{id}")
    public ResponseEntity<ItemValue> getItemValue(@PathVariable Long id) {
        log.debug("REST request to get ItemValue : {}", id);
        Optional<ItemValue> itemValue = itemValueService.findOne(id);
        return ResponseUtil.wrapOrNotFound(itemValue);
    }

    /**
     * {@code DELETE  /item-values/:id} : delete the "id" itemValue.
     *
     * @param id the id of the itemValue to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/item-values/{id}")
    public ResponseEntity<Void> deleteItemValue(@PathVariable Long id) {
        log.debug("REST request to delete ItemValue : {}", id);
        itemValueService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
