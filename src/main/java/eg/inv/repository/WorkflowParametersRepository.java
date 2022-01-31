package eg.inv.repository;

import eg.inv.domain.WorkflowParameters;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the WorkflowParameters entity.
 */
@SuppressWarnings("unused")
@Repository
public interface WorkflowParametersRepository extends JpaRepository<WorkflowParameters, Long> {}
