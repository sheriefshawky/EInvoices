package eg.inv.service.impl;

import eg.inv.domain.WorkflowParameters;
import eg.inv.repository.WorkflowParametersRepository;
import eg.inv.service.WorkflowParametersService;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link WorkflowParameters}.
 */
@Service
@Transactional
public class WorkflowParametersServiceImpl implements WorkflowParametersService {

    private final Logger log = LoggerFactory.getLogger(WorkflowParametersServiceImpl.class);

    private final WorkflowParametersRepository workflowParametersRepository;

    public WorkflowParametersServiceImpl(WorkflowParametersRepository workflowParametersRepository) {
        this.workflowParametersRepository = workflowParametersRepository;
    }

    @Override
    public WorkflowParameters save(WorkflowParameters workflowParameters) {
        log.debug("Request to save WorkflowParameters : {}", workflowParameters);
        return workflowParametersRepository.save(workflowParameters);
    }

    @Override
    public Optional<WorkflowParameters> partialUpdate(WorkflowParameters workflowParameters) {
        log.debug("Request to partially update WorkflowParameters : {}", workflowParameters);

        return workflowParametersRepository
            .findById(workflowParameters.getId())
            .map(existingWorkflowParameters -> {
                if (workflowParameters.getParameter() != null) {
                    existingWorkflowParameters.setParameter(workflowParameters.getParameter());
                }
                if (workflowParameters.getValue() != null) {
                    existingWorkflowParameters.setValue(workflowParameters.getValue());
                }
                if (workflowParameters.getActiveFrom() != null) {
                    existingWorkflowParameters.setActiveFrom(workflowParameters.getActiveFrom());
                }
                if (workflowParameters.getActiveTo() != null) {
                    existingWorkflowParameters.setActiveTo(workflowParameters.getActiveTo());
                }

                return existingWorkflowParameters;
            })
            .map(workflowParametersRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<WorkflowParameters> findAll() {
        log.debug("Request to get all WorkflowParameters");
        return workflowParametersRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<WorkflowParameters> findOne(Long id) {
        log.debug("Request to get WorkflowParameters : {}", id);
        return workflowParametersRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete WorkflowParameters : {}", id);
        workflowParametersRepository.deleteById(id);
    }
}
