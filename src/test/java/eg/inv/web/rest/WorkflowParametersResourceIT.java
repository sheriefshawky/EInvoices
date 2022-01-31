package eg.inv.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import eg.inv.IntegrationTest;
import eg.inv.domain.WorkflowParameters;
import eg.inv.repository.WorkflowParametersRepository;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link WorkflowParametersResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class WorkflowParametersResourceIT {

    private static final String DEFAULT_PARAMETER = "AAAAAAAAAA";
    private static final String UPDATED_PARAMETER = "BBBBBBBBBB";

    private static final Long DEFAULT_VALUE = 1L;
    private static final Long UPDATED_VALUE = 2L;

    private static final Instant DEFAULT_ACTIVE_FROM = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_ACTIVE_FROM = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_ACTIVE_TO = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_ACTIVE_TO = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/workflow-parameters";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private WorkflowParametersRepository workflowParametersRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restWorkflowParametersMockMvc;

    private WorkflowParameters workflowParameters;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static WorkflowParameters createEntity(EntityManager em) {
        WorkflowParameters workflowParameters = new WorkflowParameters()
            .parameter(DEFAULT_PARAMETER)
            .value(DEFAULT_VALUE)
            .activeFrom(DEFAULT_ACTIVE_FROM)
            .activeTo(DEFAULT_ACTIVE_TO);
        return workflowParameters;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static WorkflowParameters createUpdatedEntity(EntityManager em) {
        WorkflowParameters workflowParameters = new WorkflowParameters()
            .parameter(UPDATED_PARAMETER)
            .value(UPDATED_VALUE)
            .activeFrom(UPDATED_ACTIVE_FROM)
            .activeTo(UPDATED_ACTIVE_TO);
        return workflowParameters;
    }

    @BeforeEach
    public void initTest() {
        workflowParameters = createEntity(em);
    }

    @Test
    @Transactional
    void createWorkflowParameters() throws Exception {
        int databaseSizeBeforeCreate = workflowParametersRepository.findAll().size();
        // Create the WorkflowParameters
        restWorkflowParametersMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(workflowParameters))
            )
            .andExpect(status().isCreated());

        // Validate the WorkflowParameters in the database
        List<WorkflowParameters> workflowParametersList = workflowParametersRepository.findAll();
        assertThat(workflowParametersList).hasSize(databaseSizeBeforeCreate + 1);
        WorkflowParameters testWorkflowParameters = workflowParametersList.get(workflowParametersList.size() - 1);
        assertThat(testWorkflowParameters.getParameter()).isEqualTo(DEFAULT_PARAMETER);
        assertThat(testWorkflowParameters.getValue()).isEqualTo(DEFAULT_VALUE);
        assertThat(testWorkflowParameters.getActiveFrom()).isEqualTo(DEFAULT_ACTIVE_FROM);
        assertThat(testWorkflowParameters.getActiveTo()).isEqualTo(DEFAULT_ACTIVE_TO);
    }

    @Test
    @Transactional
    void createWorkflowParametersWithExistingId() throws Exception {
        // Create the WorkflowParameters with an existing ID
        workflowParameters.setId(1L);

        int databaseSizeBeforeCreate = workflowParametersRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restWorkflowParametersMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(workflowParameters))
            )
            .andExpect(status().isBadRequest());

        // Validate the WorkflowParameters in the database
        List<WorkflowParameters> workflowParametersList = workflowParametersRepository.findAll();
        assertThat(workflowParametersList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllWorkflowParameters() throws Exception {
        // Initialize the database
        workflowParametersRepository.saveAndFlush(workflowParameters);

        // Get all the workflowParametersList
        restWorkflowParametersMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(workflowParameters.getId().intValue())))
            .andExpect(jsonPath("$.[*].parameter").value(hasItem(DEFAULT_PARAMETER)))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE.intValue())))
            .andExpect(jsonPath("$.[*].activeFrom").value(hasItem(DEFAULT_ACTIVE_FROM.toString())))
            .andExpect(jsonPath("$.[*].activeTo").value(hasItem(DEFAULT_ACTIVE_TO.toString())));
    }

    @Test
    @Transactional
    void getWorkflowParameters() throws Exception {
        // Initialize the database
        workflowParametersRepository.saveAndFlush(workflowParameters);

        // Get the workflowParameters
        restWorkflowParametersMockMvc
            .perform(get(ENTITY_API_URL_ID, workflowParameters.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(workflowParameters.getId().intValue()))
            .andExpect(jsonPath("$.parameter").value(DEFAULT_PARAMETER))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE.intValue()))
            .andExpect(jsonPath("$.activeFrom").value(DEFAULT_ACTIVE_FROM.toString()))
            .andExpect(jsonPath("$.activeTo").value(DEFAULT_ACTIVE_TO.toString()));
    }

    @Test
    @Transactional
    void getNonExistingWorkflowParameters() throws Exception {
        // Get the workflowParameters
        restWorkflowParametersMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewWorkflowParameters() throws Exception {
        // Initialize the database
        workflowParametersRepository.saveAndFlush(workflowParameters);

        int databaseSizeBeforeUpdate = workflowParametersRepository.findAll().size();

        // Update the workflowParameters
        WorkflowParameters updatedWorkflowParameters = workflowParametersRepository.findById(workflowParameters.getId()).get();
        // Disconnect from session so that the updates on updatedWorkflowParameters are not directly saved in db
        em.detach(updatedWorkflowParameters);
        updatedWorkflowParameters
            .parameter(UPDATED_PARAMETER)
            .value(UPDATED_VALUE)
            .activeFrom(UPDATED_ACTIVE_FROM)
            .activeTo(UPDATED_ACTIVE_TO);

        restWorkflowParametersMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedWorkflowParameters.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedWorkflowParameters))
            )
            .andExpect(status().isOk());

        // Validate the WorkflowParameters in the database
        List<WorkflowParameters> workflowParametersList = workflowParametersRepository.findAll();
        assertThat(workflowParametersList).hasSize(databaseSizeBeforeUpdate);
        WorkflowParameters testWorkflowParameters = workflowParametersList.get(workflowParametersList.size() - 1);
        assertThat(testWorkflowParameters.getParameter()).isEqualTo(UPDATED_PARAMETER);
        assertThat(testWorkflowParameters.getValue()).isEqualTo(UPDATED_VALUE);
        assertThat(testWorkflowParameters.getActiveFrom()).isEqualTo(UPDATED_ACTIVE_FROM);
        assertThat(testWorkflowParameters.getActiveTo()).isEqualTo(UPDATED_ACTIVE_TO);
    }

    @Test
    @Transactional
    void putNonExistingWorkflowParameters() throws Exception {
        int databaseSizeBeforeUpdate = workflowParametersRepository.findAll().size();
        workflowParameters.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restWorkflowParametersMockMvc
            .perform(
                put(ENTITY_API_URL_ID, workflowParameters.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(workflowParameters))
            )
            .andExpect(status().isBadRequest());

        // Validate the WorkflowParameters in the database
        List<WorkflowParameters> workflowParametersList = workflowParametersRepository.findAll();
        assertThat(workflowParametersList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchWorkflowParameters() throws Exception {
        int databaseSizeBeforeUpdate = workflowParametersRepository.findAll().size();
        workflowParameters.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restWorkflowParametersMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(workflowParameters))
            )
            .andExpect(status().isBadRequest());

        // Validate the WorkflowParameters in the database
        List<WorkflowParameters> workflowParametersList = workflowParametersRepository.findAll();
        assertThat(workflowParametersList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamWorkflowParameters() throws Exception {
        int databaseSizeBeforeUpdate = workflowParametersRepository.findAll().size();
        workflowParameters.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restWorkflowParametersMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(workflowParameters))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the WorkflowParameters in the database
        List<WorkflowParameters> workflowParametersList = workflowParametersRepository.findAll();
        assertThat(workflowParametersList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateWorkflowParametersWithPatch() throws Exception {
        // Initialize the database
        workflowParametersRepository.saveAndFlush(workflowParameters);

        int databaseSizeBeforeUpdate = workflowParametersRepository.findAll().size();

        // Update the workflowParameters using partial update
        WorkflowParameters partialUpdatedWorkflowParameters = new WorkflowParameters();
        partialUpdatedWorkflowParameters.setId(workflowParameters.getId());

        partialUpdatedWorkflowParameters.parameter(UPDATED_PARAMETER).value(UPDATED_VALUE).activeFrom(UPDATED_ACTIVE_FROM);

        restWorkflowParametersMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedWorkflowParameters.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedWorkflowParameters))
            )
            .andExpect(status().isOk());

        // Validate the WorkflowParameters in the database
        List<WorkflowParameters> workflowParametersList = workflowParametersRepository.findAll();
        assertThat(workflowParametersList).hasSize(databaseSizeBeforeUpdate);
        WorkflowParameters testWorkflowParameters = workflowParametersList.get(workflowParametersList.size() - 1);
        assertThat(testWorkflowParameters.getParameter()).isEqualTo(UPDATED_PARAMETER);
        assertThat(testWorkflowParameters.getValue()).isEqualTo(UPDATED_VALUE);
        assertThat(testWorkflowParameters.getActiveFrom()).isEqualTo(UPDATED_ACTIVE_FROM);
        assertThat(testWorkflowParameters.getActiveTo()).isEqualTo(DEFAULT_ACTIVE_TO);
    }

    @Test
    @Transactional
    void fullUpdateWorkflowParametersWithPatch() throws Exception {
        // Initialize the database
        workflowParametersRepository.saveAndFlush(workflowParameters);

        int databaseSizeBeforeUpdate = workflowParametersRepository.findAll().size();

        // Update the workflowParameters using partial update
        WorkflowParameters partialUpdatedWorkflowParameters = new WorkflowParameters();
        partialUpdatedWorkflowParameters.setId(workflowParameters.getId());

        partialUpdatedWorkflowParameters
            .parameter(UPDATED_PARAMETER)
            .value(UPDATED_VALUE)
            .activeFrom(UPDATED_ACTIVE_FROM)
            .activeTo(UPDATED_ACTIVE_TO);

        restWorkflowParametersMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedWorkflowParameters.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedWorkflowParameters))
            )
            .andExpect(status().isOk());

        // Validate the WorkflowParameters in the database
        List<WorkflowParameters> workflowParametersList = workflowParametersRepository.findAll();
        assertThat(workflowParametersList).hasSize(databaseSizeBeforeUpdate);
        WorkflowParameters testWorkflowParameters = workflowParametersList.get(workflowParametersList.size() - 1);
        assertThat(testWorkflowParameters.getParameter()).isEqualTo(UPDATED_PARAMETER);
        assertThat(testWorkflowParameters.getValue()).isEqualTo(UPDATED_VALUE);
        assertThat(testWorkflowParameters.getActiveFrom()).isEqualTo(UPDATED_ACTIVE_FROM);
        assertThat(testWorkflowParameters.getActiveTo()).isEqualTo(UPDATED_ACTIVE_TO);
    }

    @Test
    @Transactional
    void patchNonExistingWorkflowParameters() throws Exception {
        int databaseSizeBeforeUpdate = workflowParametersRepository.findAll().size();
        workflowParameters.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restWorkflowParametersMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, workflowParameters.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(workflowParameters))
            )
            .andExpect(status().isBadRequest());

        // Validate the WorkflowParameters in the database
        List<WorkflowParameters> workflowParametersList = workflowParametersRepository.findAll();
        assertThat(workflowParametersList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchWorkflowParameters() throws Exception {
        int databaseSizeBeforeUpdate = workflowParametersRepository.findAll().size();
        workflowParameters.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restWorkflowParametersMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(workflowParameters))
            )
            .andExpect(status().isBadRequest());

        // Validate the WorkflowParameters in the database
        List<WorkflowParameters> workflowParametersList = workflowParametersRepository.findAll();
        assertThat(workflowParametersList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamWorkflowParameters() throws Exception {
        int databaseSizeBeforeUpdate = workflowParametersRepository.findAll().size();
        workflowParameters.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restWorkflowParametersMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(workflowParameters))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the WorkflowParameters in the database
        List<WorkflowParameters> workflowParametersList = workflowParametersRepository.findAll();
        assertThat(workflowParametersList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteWorkflowParameters() throws Exception {
        // Initialize the database
        workflowParametersRepository.saveAndFlush(workflowParameters);

        int databaseSizeBeforeDelete = workflowParametersRepository.findAll().size();

        // Delete the workflowParameters
        restWorkflowParametersMockMvc
            .perform(delete(ENTITY_API_URL_ID, workflowParameters.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<WorkflowParameters> workflowParametersList = workflowParametersRepository.findAll();
        assertThat(workflowParametersList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
