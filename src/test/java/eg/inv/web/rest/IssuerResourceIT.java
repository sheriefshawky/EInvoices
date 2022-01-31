package eg.inv.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import eg.inv.IntegrationTest;
import eg.inv.domain.Issuer;
import eg.inv.repository.IssuerRepository;
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
 * Integration tests for the {@link IssuerResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class IssuerResourceIT {

    private static final String DEFAULT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/issuers";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private IssuerRepository issuerRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restIssuerMockMvc;

    private Issuer issuer;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Issuer createEntity(EntityManager em) {
        Issuer issuer = new Issuer().type(DEFAULT_TYPE).name(DEFAULT_NAME);
        return issuer;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Issuer createUpdatedEntity(EntityManager em) {
        Issuer issuer = new Issuer().type(UPDATED_TYPE).name(UPDATED_NAME);
        return issuer;
    }

    @BeforeEach
    public void initTest() {
        issuer = createEntity(em);
    }

    @Test
    @Transactional
    void createIssuer() throws Exception {
        int databaseSizeBeforeCreate = issuerRepository.findAll().size();
        // Create the Issuer
        restIssuerMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(issuer)))
            .andExpect(status().isCreated());

        // Validate the Issuer in the database
        List<Issuer> issuerList = issuerRepository.findAll();
        assertThat(issuerList).hasSize(databaseSizeBeforeCreate + 1);
        Issuer testIssuer = issuerList.get(issuerList.size() - 1);
        assertThat(testIssuer.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testIssuer.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    void createIssuerWithExistingId() throws Exception {
        // Create the Issuer with an existing ID
        issuer.setId(1L);

        int databaseSizeBeforeCreate = issuerRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restIssuerMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(issuer)))
            .andExpect(status().isBadRequest());

        // Validate the Issuer in the database
        List<Issuer> issuerList = issuerRepository.findAll();
        assertThat(issuerList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllIssuers() throws Exception {
        // Initialize the database
        issuerRepository.saveAndFlush(issuer);

        // Get all the issuerList
        restIssuerMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(issuer.getId().intValue())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));
    }

    @Test
    @Transactional
    void getIssuer() throws Exception {
        // Initialize the database
        issuerRepository.saveAndFlush(issuer);

        // Get the issuer
        restIssuerMockMvc
            .perform(get(ENTITY_API_URL_ID, issuer.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(issuer.getId().intValue()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME));
    }

    @Test
    @Transactional
    void getNonExistingIssuer() throws Exception {
        // Get the issuer
        restIssuerMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewIssuer() throws Exception {
        // Initialize the database
        issuerRepository.saveAndFlush(issuer);

        int databaseSizeBeforeUpdate = issuerRepository.findAll().size();

        // Update the issuer
        Issuer updatedIssuer = issuerRepository.findById(issuer.getId()).get();
        // Disconnect from session so that the updates on updatedIssuer are not directly saved in db
        em.detach(updatedIssuer);
        updatedIssuer.type(UPDATED_TYPE).name(UPDATED_NAME);

        restIssuerMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedIssuer.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedIssuer))
            )
            .andExpect(status().isOk());

        // Validate the Issuer in the database
        List<Issuer> issuerList = issuerRepository.findAll();
        assertThat(issuerList).hasSize(databaseSizeBeforeUpdate);
        Issuer testIssuer = issuerList.get(issuerList.size() - 1);
        assertThat(testIssuer.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testIssuer.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    void putNonExistingIssuer() throws Exception {
        int databaseSizeBeforeUpdate = issuerRepository.findAll().size();
        issuer.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restIssuerMockMvc
            .perform(
                put(ENTITY_API_URL_ID, issuer.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(issuer))
            )
            .andExpect(status().isBadRequest());

        // Validate the Issuer in the database
        List<Issuer> issuerList = issuerRepository.findAll();
        assertThat(issuerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchIssuer() throws Exception {
        int databaseSizeBeforeUpdate = issuerRepository.findAll().size();
        issuer.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restIssuerMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(issuer))
            )
            .andExpect(status().isBadRequest());

        // Validate the Issuer in the database
        List<Issuer> issuerList = issuerRepository.findAll();
        assertThat(issuerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamIssuer() throws Exception {
        int databaseSizeBeforeUpdate = issuerRepository.findAll().size();
        issuer.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restIssuerMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(issuer)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Issuer in the database
        List<Issuer> issuerList = issuerRepository.findAll();
        assertThat(issuerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateIssuerWithPatch() throws Exception {
        // Initialize the database
        issuerRepository.saveAndFlush(issuer);

        int databaseSizeBeforeUpdate = issuerRepository.findAll().size();

        // Update the issuer using partial update
        Issuer partialUpdatedIssuer = new Issuer();
        partialUpdatedIssuer.setId(issuer.getId());

        partialUpdatedIssuer.type(UPDATED_TYPE);

        restIssuerMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedIssuer.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedIssuer))
            )
            .andExpect(status().isOk());

        // Validate the Issuer in the database
        List<Issuer> issuerList = issuerRepository.findAll();
        assertThat(issuerList).hasSize(databaseSizeBeforeUpdate);
        Issuer testIssuer = issuerList.get(issuerList.size() - 1);
        assertThat(testIssuer.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testIssuer.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    void fullUpdateIssuerWithPatch() throws Exception {
        // Initialize the database
        issuerRepository.saveAndFlush(issuer);

        int databaseSizeBeforeUpdate = issuerRepository.findAll().size();

        // Update the issuer using partial update
        Issuer partialUpdatedIssuer = new Issuer();
        partialUpdatedIssuer.setId(issuer.getId());

        partialUpdatedIssuer.type(UPDATED_TYPE).name(UPDATED_NAME);

        restIssuerMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedIssuer.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedIssuer))
            )
            .andExpect(status().isOk());

        // Validate the Issuer in the database
        List<Issuer> issuerList = issuerRepository.findAll();
        assertThat(issuerList).hasSize(databaseSizeBeforeUpdate);
        Issuer testIssuer = issuerList.get(issuerList.size() - 1);
        assertThat(testIssuer.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testIssuer.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    void patchNonExistingIssuer() throws Exception {
        int databaseSizeBeforeUpdate = issuerRepository.findAll().size();
        issuer.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restIssuerMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, issuer.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(issuer))
            )
            .andExpect(status().isBadRequest());

        // Validate the Issuer in the database
        List<Issuer> issuerList = issuerRepository.findAll();
        assertThat(issuerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchIssuer() throws Exception {
        int databaseSizeBeforeUpdate = issuerRepository.findAll().size();
        issuer.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restIssuerMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(issuer))
            )
            .andExpect(status().isBadRequest());

        // Validate the Issuer in the database
        List<Issuer> issuerList = issuerRepository.findAll();
        assertThat(issuerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamIssuer() throws Exception {
        int databaseSizeBeforeUpdate = issuerRepository.findAll().size();
        issuer.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restIssuerMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(issuer)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Issuer in the database
        List<Issuer> issuerList = issuerRepository.findAll();
        assertThat(issuerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteIssuer() throws Exception {
        // Initialize the database
        issuerRepository.saveAndFlush(issuer);

        int databaseSizeBeforeDelete = issuerRepository.findAll().size();

        // Delete the issuer
        restIssuerMockMvc
            .perform(delete(ENTITY_API_URL_ID, issuer.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Issuer> issuerList = issuerRepository.findAll();
        assertThat(issuerList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
