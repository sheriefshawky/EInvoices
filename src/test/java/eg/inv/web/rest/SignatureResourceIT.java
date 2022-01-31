package eg.inv.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import eg.inv.IntegrationTest;
import eg.inv.domain.Signature;
import eg.inv.repository.SignatureRepository;
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
 * Integration tests for the {@link SignatureResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class SignatureResourceIT {

    private static final String DEFAULT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_VALUE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/signatures";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private SignatureRepository signatureRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSignatureMockMvc;

    private Signature signature;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Signature createEntity(EntityManager em) {
        Signature signature = new Signature().type(DEFAULT_TYPE).value(DEFAULT_VALUE);
        return signature;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Signature createUpdatedEntity(EntityManager em) {
        Signature signature = new Signature().type(UPDATED_TYPE).value(UPDATED_VALUE);
        return signature;
    }

    @BeforeEach
    public void initTest() {
        signature = createEntity(em);
    }

    @Test
    @Transactional
    void createSignature() throws Exception {
        int databaseSizeBeforeCreate = signatureRepository.findAll().size();
        // Create the Signature
        restSignatureMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(signature)))
            .andExpect(status().isCreated());

        // Validate the Signature in the database
        List<Signature> signatureList = signatureRepository.findAll();
        assertThat(signatureList).hasSize(databaseSizeBeforeCreate + 1);
        Signature testSignature = signatureList.get(signatureList.size() - 1);
        assertThat(testSignature.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testSignature.getValue()).isEqualTo(DEFAULT_VALUE);
    }

    @Test
    @Transactional
    void createSignatureWithExistingId() throws Exception {
        // Create the Signature with an existing ID
        signature.setId(1L);

        int databaseSizeBeforeCreate = signatureRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restSignatureMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(signature)))
            .andExpect(status().isBadRequest());

        // Validate the Signature in the database
        List<Signature> signatureList = signatureRepository.findAll();
        assertThat(signatureList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllSignatures() throws Exception {
        // Initialize the database
        signatureRepository.saveAndFlush(signature);

        // Get all the signatureList
        restSignatureMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(signature.getId().intValue())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE)));
    }

    @Test
    @Transactional
    void getSignature() throws Exception {
        // Initialize the database
        signatureRepository.saveAndFlush(signature);

        // Get the signature
        restSignatureMockMvc
            .perform(get(ENTITY_API_URL_ID, signature.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(signature.getId().intValue()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE));
    }

    @Test
    @Transactional
    void getNonExistingSignature() throws Exception {
        // Get the signature
        restSignatureMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewSignature() throws Exception {
        // Initialize the database
        signatureRepository.saveAndFlush(signature);

        int databaseSizeBeforeUpdate = signatureRepository.findAll().size();

        // Update the signature
        Signature updatedSignature = signatureRepository.findById(signature.getId()).get();
        // Disconnect from session so that the updates on updatedSignature are not directly saved in db
        em.detach(updatedSignature);
        updatedSignature.type(UPDATED_TYPE).value(UPDATED_VALUE);

        restSignatureMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedSignature.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedSignature))
            )
            .andExpect(status().isOk());

        // Validate the Signature in the database
        List<Signature> signatureList = signatureRepository.findAll();
        assertThat(signatureList).hasSize(databaseSizeBeforeUpdate);
        Signature testSignature = signatureList.get(signatureList.size() - 1);
        assertThat(testSignature.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testSignature.getValue()).isEqualTo(UPDATED_VALUE);
    }

    @Test
    @Transactional
    void putNonExistingSignature() throws Exception {
        int databaseSizeBeforeUpdate = signatureRepository.findAll().size();
        signature.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSignatureMockMvc
            .perform(
                put(ENTITY_API_URL_ID, signature.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(signature))
            )
            .andExpect(status().isBadRequest());

        // Validate the Signature in the database
        List<Signature> signatureList = signatureRepository.findAll();
        assertThat(signatureList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchSignature() throws Exception {
        int databaseSizeBeforeUpdate = signatureRepository.findAll().size();
        signature.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSignatureMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(signature))
            )
            .andExpect(status().isBadRequest());

        // Validate the Signature in the database
        List<Signature> signatureList = signatureRepository.findAll();
        assertThat(signatureList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamSignature() throws Exception {
        int databaseSizeBeforeUpdate = signatureRepository.findAll().size();
        signature.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSignatureMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(signature)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Signature in the database
        List<Signature> signatureList = signatureRepository.findAll();
        assertThat(signatureList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateSignatureWithPatch() throws Exception {
        // Initialize the database
        signatureRepository.saveAndFlush(signature);

        int databaseSizeBeforeUpdate = signatureRepository.findAll().size();

        // Update the signature using partial update
        Signature partialUpdatedSignature = new Signature();
        partialUpdatedSignature.setId(signature.getId());

        partialUpdatedSignature.type(UPDATED_TYPE);

        restSignatureMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSignature.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSignature))
            )
            .andExpect(status().isOk());

        // Validate the Signature in the database
        List<Signature> signatureList = signatureRepository.findAll();
        assertThat(signatureList).hasSize(databaseSizeBeforeUpdate);
        Signature testSignature = signatureList.get(signatureList.size() - 1);
        assertThat(testSignature.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testSignature.getValue()).isEqualTo(DEFAULT_VALUE);
    }

    @Test
    @Transactional
    void fullUpdateSignatureWithPatch() throws Exception {
        // Initialize the database
        signatureRepository.saveAndFlush(signature);

        int databaseSizeBeforeUpdate = signatureRepository.findAll().size();

        // Update the signature using partial update
        Signature partialUpdatedSignature = new Signature();
        partialUpdatedSignature.setId(signature.getId());

        partialUpdatedSignature.type(UPDATED_TYPE).value(UPDATED_VALUE);

        restSignatureMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSignature.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSignature))
            )
            .andExpect(status().isOk());

        // Validate the Signature in the database
        List<Signature> signatureList = signatureRepository.findAll();
        assertThat(signatureList).hasSize(databaseSizeBeforeUpdate);
        Signature testSignature = signatureList.get(signatureList.size() - 1);
        assertThat(testSignature.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testSignature.getValue()).isEqualTo(UPDATED_VALUE);
    }

    @Test
    @Transactional
    void patchNonExistingSignature() throws Exception {
        int databaseSizeBeforeUpdate = signatureRepository.findAll().size();
        signature.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSignatureMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, signature.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(signature))
            )
            .andExpect(status().isBadRequest());

        // Validate the Signature in the database
        List<Signature> signatureList = signatureRepository.findAll();
        assertThat(signatureList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchSignature() throws Exception {
        int databaseSizeBeforeUpdate = signatureRepository.findAll().size();
        signature.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSignatureMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(signature))
            )
            .andExpect(status().isBadRequest());

        // Validate the Signature in the database
        List<Signature> signatureList = signatureRepository.findAll();
        assertThat(signatureList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamSignature() throws Exception {
        int databaseSizeBeforeUpdate = signatureRepository.findAll().size();
        signature.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSignatureMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(signature))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Signature in the database
        List<Signature> signatureList = signatureRepository.findAll();
        assertThat(signatureList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteSignature() throws Exception {
        // Initialize the database
        signatureRepository.saveAndFlush(signature);

        int databaseSizeBeforeDelete = signatureRepository.findAll().size();

        // Delete the signature
        restSignatureMockMvc
            .perform(delete(ENTITY_API_URL_ID, signature.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Signature> signatureList = signatureRepository.findAll();
        assertThat(signatureList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
