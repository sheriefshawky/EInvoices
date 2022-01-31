package eg.inv.web.rest;

import eg.inv.domain.TaxTotal;
import eg.inv.repository.TaxTotalRepository;
import eg.inv.service.TaxTotalService;
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
 * REST controller for managing {@link eg.inv.domain.TaxTotal}.
 */
@RestController
@RequestMapping("/api")
public class TaxTotalResource {

    private final Logger log = LoggerFactory.getLogger(TaxTotalResource.class);

    private static final String ENTITY_NAME = "taxTotal";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TaxTotalService taxTotalService;

    private final TaxTotalRepository taxTotalRepository;

    public TaxTotalResource(TaxTotalService taxTotalService, TaxTotalRepository taxTotalRepository) {
        this.taxTotalService = taxTotalService;
        this.taxTotalRepository = taxTotalRepository;
    }

    /**
     * {@code POST  /tax-totals} : Create a new taxTotal.
     *
     * @param taxTotal the taxTotal to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new taxTotal, or with status {@code 400 (Bad Request)} if the taxTotal has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/tax-totals")
    public ResponseEntity<TaxTotal> createTaxTotal(@RequestBody TaxTotal taxTotal) throws URISyntaxException {
        log.debug("REST request to save TaxTotal : {}", taxTotal);
        if (taxTotal.getId() != null) {
            throw new BadRequestAlertException("A new taxTotal cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TaxTotal result = taxTotalService.save(taxTotal);
        return ResponseEntity
            .created(new URI("/api/tax-totals/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /tax-totals/:id} : Updates an existing taxTotal.
     *
     * @param id the id of the taxTotal to save.
     * @param taxTotal the taxTotal to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated taxTotal,
     * or with status {@code 400 (Bad Request)} if the taxTotal is not valid,
     * or with status {@code 500 (Internal Server Error)} if the taxTotal couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/tax-totals/{id}")
    public ResponseEntity<TaxTotal> updateTaxTotal(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody TaxTotal taxTotal
    ) throws URISyntaxException {
        log.debug("REST request to update TaxTotal : {}, {}", id, taxTotal);
        if (taxTotal.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, taxTotal.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!taxTotalRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        TaxTotal result = taxTotalService.save(taxTotal);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, taxTotal.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /tax-totals/:id} : Partial updates given fields of an existing taxTotal, field will ignore if it is null
     *
     * @param id the id of the taxTotal to save.
     * @param taxTotal the taxTotal to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated taxTotal,
     * or with status {@code 400 (Bad Request)} if the taxTotal is not valid,
     * or with status {@code 404 (Not Found)} if the taxTotal is not found,
     * or with status {@code 500 (Internal Server Error)} if the taxTotal couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/tax-totals/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<TaxTotal> partialUpdateTaxTotal(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody TaxTotal taxTotal
    ) throws URISyntaxException {
        log.debug("REST request to partial update TaxTotal partially : {}, {}", id, taxTotal);
        if (taxTotal.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, taxTotal.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!taxTotalRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<TaxTotal> result = taxTotalService.partialUpdate(taxTotal);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, taxTotal.getId().toString())
        );
    }

    /**
     * {@code GET  /tax-totals} : get all the taxTotals.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of taxTotals in body.
     */
    @GetMapping("/tax-totals")
    public List<TaxTotal> getAllTaxTotals() {
        log.debug("REST request to get all TaxTotals");
        return taxTotalService.findAll();
    }

    /**
     * {@code GET  /tax-totals/:id} : get the "id" taxTotal.
     *
     * @param id the id of the taxTotal to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the taxTotal, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/tax-totals/{id}")
    public ResponseEntity<TaxTotal> getTaxTotal(@PathVariable Long id) {
        log.debug("REST request to get TaxTotal : {}", id);
        Optional<TaxTotal> taxTotal = taxTotalService.findOne(id);
        return ResponseUtil.wrapOrNotFound(taxTotal);
    }

    /**
     * {@code DELETE  /tax-totals/:id} : delete the "id" taxTotal.
     *
     * @param id the id of the taxTotal to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/tax-totals/{id}")
    public ResponseEntity<Void> deleteTaxTotal(@PathVariable Long id) {
        log.debug("REST request to delete TaxTotal : {}", id);
        taxTotalService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
