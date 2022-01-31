package eg.inv.web.rest;

import eg.inv.domain.WorkflowParameters;
import eg.inv.repository.WorkflowParametersRepository;
import eg.inv.service.WorkflowParametersService;
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
 * REST controller for managing {@link eg.inv.domain.WorkflowParameters}.
 */
@RestController
@RequestMapping("/api")
public class WorkflowParametersResource {

    private final Logger log = LoggerFactory.getLogger(WorkflowParametersResource.class);

    private static final String ENTITY_NAME = "workflowParameters";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final WorkflowParametersService workflowParametersService;

    private final WorkflowParametersRepository workflowParametersRepository;

    public WorkflowParametersResource(
        WorkflowParametersService workflowParametersService,
        WorkflowParametersRepository workflowParametersRepository
    ) {
        this.workflowParametersService = workflowParametersService;
        this.workflowParametersRepository = workflowParametersRepository;
    }

    /**
     * {@code POST  /workflow-parameters} : Create a new workflowParameters.
     *
     * @param workflowParameters the workflowParameters to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new workflowParameters, or with status {@code 400 (Bad Request)} if the workflowParameters has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/workflow-parameters")
    public ResponseEntity<WorkflowParameters> createWorkflowParameters(@RequestBody WorkflowParameters workflowParameters)
        throws URISyntaxException {
        log.debug("REST request to save WorkflowParameters : {}", workflowParameters);
        if (workflowParameters.getId() != null) {
            throw new BadRequestAlertException("A new workflowParameters cannot already have an ID", ENTITY_NAME, "idexists");
        }
        WorkflowParameters result = workflowParametersService.save(workflowParameters);
        return ResponseEntity
            .created(new URI("/api/workflow-parameters/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /workflow-parameters/:id} : Updates an existing workflowParameters.
     *
     * @param id the id of the workflowParameters to save.
     * @param workflowParameters the workflowParameters to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated workflowParameters,
     * or with status {@code 400 (Bad Request)} if the workflowParameters is not valid,
     * or with status {@code 500 (Internal Server Error)} if the workflowParameters couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/workflow-parameters/{id}")
    public ResponseEntity<WorkflowParameters> updateWorkflowParameters(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody WorkflowParameters workflowParameters
    ) throws URISyntaxException {
        log.debug("REST request to update WorkflowParameters : {}, {}", id, workflowParameters);
        if (workflowParameters.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, workflowParameters.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!workflowParametersRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        WorkflowParameters result = workflowParametersService.save(workflowParameters);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, workflowParameters.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /workflow-parameters/:id} : Partial updates given fields of an existing workflowParameters, field will ignore if it is null
     *
     * @param id the id of the workflowParameters to save.
     * @param workflowParameters the workflowParameters to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated workflowParameters,
     * or with status {@code 400 (Bad Request)} if the workflowParameters is not valid,
     * or with status {@code 404 (Not Found)} if the workflowParameters is not found,
     * or with status {@code 500 (Internal Server Error)} if the workflowParameters couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/workflow-parameters/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<WorkflowParameters> partialUpdateWorkflowParameters(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody WorkflowParameters workflowParameters
    ) throws URISyntaxException {
        log.debug("REST request to partial update WorkflowParameters partially : {}, {}", id, workflowParameters);
        if (workflowParameters.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, workflowParameters.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!workflowParametersRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<WorkflowParameters> result = workflowParametersService.partialUpdate(workflowParameters);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, workflowParameters.getId().toString())
        );
    }

    /**
     * {@code GET  /workflow-parameters} : get all the workflowParameters.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of workflowParameters in body.
     */
    @GetMapping("/workflow-parameters")
    public List<WorkflowParameters> getAllWorkflowParameters() {
        log.debug("REST request to get all WorkflowParameters");
        return workflowParametersService.findAll();
    }

    /**
     * {@code GET  /workflow-parameters/:id} : get the "id" workflowParameters.
     *
     * @param id the id of the workflowParameters to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the workflowParameters, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/workflow-parameters/{id}")
    public ResponseEntity<WorkflowParameters> getWorkflowParameters(@PathVariable Long id) {
        log.debug("REST request to get WorkflowParameters : {}", id);
        Optional<WorkflowParameters> workflowParameters = workflowParametersService.findOne(id);
        return ResponseUtil.wrapOrNotFound(workflowParameters);
    }

    /**
     * {@code DELETE  /workflow-parameters/:id} : delete the "id" workflowParameters.
     *
     * @param id the id of the workflowParameters to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/workflow-parameters/{id}")
    public ResponseEntity<Void> deleteWorkflowParameters(@PathVariable Long id) {
        log.debug("REST request to delete WorkflowParameters : {}", id);
        workflowParametersService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
