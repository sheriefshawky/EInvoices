package eg.inv.domain;

import static org.assertj.core.api.Assertions.assertThat;

import eg.inv.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class WorkflowParametersTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(WorkflowParameters.class);
        WorkflowParameters workflowParameters1 = new WorkflowParameters();
        workflowParameters1.setId(1L);
        WorkflowParameters workflowParameters2 = new WorkflowParameters();
        workflowParameters2.setId(workflowParameters1.getId());
        assertThat(workflowParameters1).isEqualTo(workflowParameters2);
        workflowParameters2.setId(2L);
        assertThat(workflowParameters1).isNotEqualTo(workflowParameters2);
        workflowParameters1.setId(null);
        assertThat(workflowParameters1).isNotEqualTo(workflowParameters2);
    }
}
