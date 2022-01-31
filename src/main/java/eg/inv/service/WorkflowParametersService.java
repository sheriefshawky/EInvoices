package eg.inv.service;

import eg.inv.domain.WorkflowParameters;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link WorkflowParameters}.
 */
public interface WorkflowParametersService {
    /**
     * Save a workflowParameters.
     *
     * @param workflowParameters the entity to save.
     * @return the persisted entity.
     */
    WorkflowParameters save(WorkflowParameters workflowParameters);

    /**
     * Partially updates a workflowParameters.
     *
     * @param workflowParameters the entity to update partially.
     * @return the persisted entity.
     */
    Optional<WorkflowParameters> partialUpdate(WorkflowParameters workflowParameters);

    /**
     * Get all the workflowParameters.
     *
     * @return the list of entities.
     */
    List<WorkflowParameters> findAll();

    /**
     * Get the "id" workflowParameters.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<WorkflowParameters> findOne(Long id);

    /**
     * Delete the "id" workflowParameters.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
