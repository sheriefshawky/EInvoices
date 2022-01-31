package eg.inv.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import eg.inv.IntegrationTest;
import eg.inv.domain.Receiver;
import eg.inv.repository.ReceiverRepository;
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
 * Integration tests for the {@link ReceiverResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ReceiverResourceIT {

    private static final String DEFAULT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/receivers";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ReceiverRepository receiverRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restReceiverMockMvc;

    private Receiver receiver;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Receiver createEntity(EntityManager em) {
        Receiver receiver = new Receiver().type(DEFAULT_TYPE).name(DEFAULT_NAME);
        return receiver;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Receiver createUpdatedEntity(EntityManager em) {
        Receiver receiver = new Receiver().type(UPDATED_TYPE).name(UPDATED_NAME);
        return receiver;
    }

    @BeforeEach
    public void initTest() {
        receiver = createEntity(em);
    }

    @Test
    @Transactional
    void createReceiver() throws Exception {
        int databaseSizeBeforeCreate = receiverRepository.findAll().size();
        // Create the Receiver
        restReceiverMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(receiver)))
            .andExpect(status().isCreated());

        // Validate the Receiver in the database
        List<Receiver> receiverList = receiverRepository.findAll();
        assertThat(receiverList).hasSize(databaseSizeBeforeCreate + 1);
        Receiver testReceiver = receiverList.get(receiverList.size() - 1);
        assertThat(testReceiver.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testReceiver.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    void createReceiverWithExistingId() throws Exception {
        // Create the Receiver with an existing ID
        receiver.setId(1L);

        int databaseSizeBeforeCreate = receiverRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restReceiverMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(receiver)))
            .andExpect(status().isBadRequest());

        // Validate the Receiver in the database
        List<Receiver> receiverList = receiverRepository.findAll();
        assertThat(receiverList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllReceivers() throws Exception {
        // Initialize the database
        receiverRepository.saveAndFlush(receiver);

        // Get all the receiverList
        restReceiverMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(receiver.getId().intValue())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));
    }

    @Test
    @Transactional
    void getReceiver() throws Exception {
        // Initialize the database
        receiverRepository.saveAndFlush(receiver);

        // Get the receiver
        restReceiverMockMvc
            .perform(get(ENTITY_API_URL_ID, receiver.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(receiver.getId().intValue()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME));
    }

    @Test
    @Transactional
    void getNonExistingReceiver() throws Exception {
        // Get the receiver
        restReceiverMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewReceiver() throws Exception {
        // Initialize the database
        receiverRepository.saveAndFlush(receiver);

        int databaseSizeBeforeUpdate = receiverRepository.findAll().size();

        // Update the receiver
        Receiver updatedReceiver = receiverRepository.findById(receiver.getId()).get();
        // Disconnect from session so that the updates on updatedReceiver are not directly saved in db
        em.detach(updatedReceiver);
        updatedReceiver.type(UPDATED_TYPE).name(UPDATED_NAME);

        restReceiverMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedReceiver.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedReceiver))
            )
            .andExpect(status().isOk());

        // Validate the Receiver in the database
        List<Receiver> receiverList = receiverRepository.findAll();
        assertThat(receiverList).hasSize(databaseSizeBeforeUpdate);
        Receiver testReceiver = receiverList.get(receiverList.size() - 1);
        assertThat(testReceiver.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testReceiver.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    void putNonExistingReceiver() throws Exception {
        int databaseSizeBeforeUpdate = receiverRepository.findAll().size();
        receiver.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restReceiverMockMvc
            .perform(
                put(ENTITY_API_URL_ID, receiver.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(receiver))
            )
            .andExpect(status().isBadRequest());

        // Validate the Receiver in the database
        List<Receiver> receiverList = receiverRepository.findAll();
        assertThat(receiverList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchReceiver() throws Exception {
        int databaseSizeBeforeUpdate = receiverRepository.findAll().size();
        receiver.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restReceiverMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(receiver))
            )
            .andExpect(status().isBadRequest());

        // Validate the Receiver in the database
        List<Receiver> receiverList = receiverRepository.findAll();
        assertThat(receiverList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamReceiver() throws Exception {
        int databaseSizeBeforeUpdate = receiverRepository.findAll().size();
        receiver.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restReceiverMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(receiver)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Receiver in the database
        List<Receiver> receiverList = receiverRepository.findAll();
        assertThat(receiverList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateReceiverWithPatch() throws Exception {
        // Initialize the database
        receiverRepository.saveAndFlush(receiver);

        int databaseSizeBeforeUpdate = receiverRepository.findAll().size();

        // Update the receiver using partial update
        Receiver partialUpdatedReceiver = new Receiver();
        partialUpdatedReceiver.setId(receiver.getId());

        partialUpdatedReceiver.name(UPDATED_NAME);

        restReceiverMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedReceiver.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedReceiver))
            )
            .andExpect(status().isOk());

        // Validate the Receiver in the database
        List<Receiver> receiverList = receiverRepository.findAll();
        assertThat(receiverList).hasSize(databaseSizeBeforeUpdate);
        Receiver testReceiver = receiverList.get(receiverList.size() - 1);
        assertThat(testReceiver.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testReceiver.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    void fullUpdateReceiverWithPatch() throws Exception {
        // Initialize the database
        receiverRepository.saveAndFlush(receiver);

        int databaseSizeBeforeUpdate = receiverRepository.findAll().size();

        // Update the receiver using partial update
        Receiver partialUpdatedReceiver = new Receiver();
        partialUpdatedReceiver.setId(receiver.getId());

        partialUpdatedReceiver.type(UPDATED_TYPE).name(UPDATED_NAME);

        restReceiverMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedReceiver.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedReceiver))
            )
            .andExpect(status().isOk());

        // Validate the Receiver in the database
        List<Receiver> receiverList = receiverRepository.findAll();
        assertThat(receiverList).hasSize(databaseSizeBeforeUpdate);
        Receiver testReceiver = receiverList.get(receiverList.size() - 1);
        assertThat(testReceiver.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testReceiver.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    void patchNonExistingReceiver() throws Exception {
        int databaseSizeBeforeUpdate = receiverRepository.findAll().size();
        receiver.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restReceiverMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, receiver.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(receiver))
            )
            .andExpect(status().isBadRequest());

        // Validate the Receiver in the database
        List<Receiver> receiverList = receiverRepository.findAll();
        assertThat(receiverList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchReceiver() throws Exception {
        int databaseSizeBeforeUpdate = receiverRepository.findAll().size();
        receiver.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restReceiverMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(receiver))
            )
            .andExpect(status().isBadRequest());

        // Validate the Receiver in the database
        List<Receiver> receiverList = receiverRepository.findAll();
        assertThat(receiverList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamReceiver() throws Exception {
        int databaseSizeBeforeUpdate = receiverRepository.findAll().size();
        receiver.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restReceiverMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(receiver)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Receiver in the database
        List<Receiver> receiverList = receiverRepository.findAll();
        assertThat(receiverList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteReceiver() throws Exception {
        // Initialize the database
        receiverRepository.saveAndFlush(receiver);

        int databaseSizeBeforeDelete = receiverRepository.findAll().size();

        // Delete the receiver
        restReceiverMockMvc
            .perform(delete(ENTITY_API_URL_ID, receiver.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Receiver> receiverList = receiverRepository.findAll();
        assertThat(receiverList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
