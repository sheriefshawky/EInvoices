package eg.inv.web.rest;

import static eg.inv.web.rest.TestUtil.sameNumber;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import eg.inv.IntegrationTest;
import eg.inv.domain.TaxTotal;
import eg.inv.repository.TaxTotalRepository;
import java.math.BigDecimal;
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
 * Integration tests for the {@link TaxTotalResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class TaxTotalResourceIT {

    private static final String DEFAULT_TAX_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TAX_TYPE = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_AMOUNT = new BigDecimal(1);
    private static final BigDecimal UPDATED_AMOUNT = new BigDecimal(2);

    private static final String ENTITY_API_URL = "/api/tax-totals";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private TaxTotalRepository taxTotalRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTaxTotalMockMvc;

    private TaxTotal taxTotal;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TaxTotal createEntity(EntityManager em) {
        TaxTotal taxTotal = new TaxTotal().taxType(DEFAULT_TAX_TYPE).amount(DEFAULT_AMOUNT);
        return taxTotal;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TaxTotal createUpdatedEntity(EntityManager em) {
        TaxTotal taxTotal = new TaxTotal().taxType(UPDATED_TAX_TYPE).amount(UPDATED_AMOUNT);
        return taxTotal;
    }

    @BeforeEach
    public void initTest() {
        taxTotal = createEntity(em);
    }

    @Test
    @Transactional
    void createTaxTotal() throws Exception {
        int databaseSizeBeforeCreate = taxTotalRepository.findAll().size();
        // Create the TaxTotal
        restTaxTotalMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(taxTotal)))
            .andExpect(status().isCreated());

        // Validate the TaxTotal in the database
        List<TaxTotal> taxTotalList = taxTotalRepository.findAll();
        assertThat(taxTotalList).hasSize(databaseSizeBeforeCreate + 1);
        TaxTotal testTaxTotal = taxTotalList.get(taxTotalList.size() - 1);
        assertThat(testTaxTotal.getTaxType()).isEqualTo(DEFAULT_TAX_TYPE);
        assertThat(testTaxTotal.getAmount()).isEqualByComparingTo(DEFAULT_AMOUNT);
    }

    @Test
    @Transactional
    void createTaxTotalWithExistingId() throws Exception {
        // Create the TaxTotal with an existing ID
        taxTotal.setId(1L);

        int databaseSizeBeforeCreate = taxTotalRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restTaxTotalMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(taxTotal)))
            .andExpect(status().isBadRequest());

        // Validate the TaxTotal in the database
        List<TaxTotal> taxTotalList = taxTotalRepository.findAll();
        assertThat(taxTotalList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllTaxTotals() throws Exception {
        // Initialize the database
        taxTotalRepository.saveAndFlush(taxTotal);

        // Get all the taxTotalList
        restTaxTotalMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(taxTotal.getId().intValue())))
            .andExpect(jsonPath("$.[*].taxType").value(hasItem(DEFAULT_TAX_TYPE)))
            .andExpect(jsonPath("$.[*].amount").value(hasItem(sameNumber(DEFAULT_AMOUNT))));
    }

    @Test
    @Transactional
    void getTaxTotal() throws Exception {
        // Initialize the database
        taxTotalRepository.saveAndFlush(taxTotal);

        // Get the taxTotal
        restTaxTotalMockMvc
            .perform(get(ENTITY_API_URL_ID, taxTotal.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(taxTotal.getId().intValue()))
            .andExpect(jsonPath("$.taxType").value(DEFAULT_TAX_TYPE))
            .andExpect(jsonPath("$.amount").value(sameNumber(DEFAULT_AMOUNT)));
    }

    @Test
    @Transactional
    void getNonExistingTaxTotal() throws Exception {
        // Get the taxTotal
        restTaxTotalMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewTaxTotal() throws Exception {
        // Initialize the database
        taxTotalRepository.saveAndFlush(taxTotal);

        int databaseSizeBeforeUpdate = taxTotalRepository.findAll().size();

        // Update the taxTotal
        TaxTotal updatedTaxTotal = taxTotalRepository.findById(taxTotal.getId()).get();
        // Disconnect from session so that the updates on updatedTaxTotal are not directly saved in db
        em.detach(updatedTaxTotal);
        updatedTaxTotal.taxType(UPDATED_TAX_TYPE).amount(UPDATED_AMOUNT);

        restTaxTotalMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedTaxTotal.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedTaxTotal))
            )
            .andExpect(status().isOk());

        // Validate the TaxTotal in the database
        List<TaxTotal> taxTotalList = taxTotalRepository.findAll();
        assertThat(taxTotalList).hasSize(databaseSizeBeforeUpdate);
        TaxTotal testTaxTotal = taxTotalList.get(taxTotalList.size() - 1);
        assertThat(testTaxTotal.getTaxType()).isEqualTo(UPDATED_TAX_TYPE);
        assertThat(testTaxTotal.getAmount()).isEqualByComparingTo(UPDATED_AMOUNT);
    }

    @Test
    @Transactional
    void putNonExistingTaxTotal() throws Exception {
        int databaseSizeBeforeUpdate = taxTotalRepository.findAll().size();
        taxTotal.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTaxTotalMockMvc
            .perform(
                put(ENTITY_API_URL_ID, taxTotal.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(taxTotal))
            )
            .andExpect(status().isBadRequest());

        // Validate the TaxTotal in the database
        List<TaxTotal> taxTotalList = taxTotalRepository.findAll();
        assertThat(taxTotalList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchTaxTotal() throws Exception {
        int databaseSizeBeforeUpdate = taxTotalRepository.findAll().size();
        taxTotal.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTaxTotalMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(taxTotal))
            )
            .andExpect(status().isBadRequest());

        // Validate the TaxTotal in the database
        List<TaxTotal> taxTotalList = taxTotalRepository.findAll();
        assertThat(taxTotalList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamTaxTotal() throws Exception {
        int databaseSizeBeforeUpdate = taxTotalRepository.findAll().size();
        taxTotal.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTaxTotalMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(taxTotal)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the TaxTotal in the database
        List<TaxTotal> taxTotalList = taxTotalRepository.findAll();
        assertThat(taxTotalList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateTaxTotalWithPatch() throws Exception {
        // Initialize the database
        taxTotalRepository.saveAndFlush(taxTotal);

        int databaseSizeBeforeUpdate = taxTotalRepository.findAll().size();

        // Update the taxTotal using partial update
        TaxTotal partialUpdatedTaxTotal = new TaxTotal();
        partialUpdatedTaxTotal.setId(taxTotal.getId());

        partialUpdatedTaxTotal.taxType(UPDATED_TAX_TYPE);

        restTaxTotalMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTaxTotal.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTaxTotal))
            )
            .andExpect(status().isOk());

        // Validate the TaxTotal in the database
        List<TaxTotal> taxTotalList = taxTotalRepository.findAll();
        assertThat(taxTotalList).hasSize(databaseSizeBeforeUpdate);
        TaxTotal testTaxTotal = taxTotalList.get(taxTotalList.size() - 1);
        assertThat(testTaxTotal.getTaxType()).isEqualTo(UPDATED_TAX_TYPE);
        assertThat(testTaxTotal.getAmount()).isEqualByComparingTo(DEFAULT_AMOUNT);
    }

    @Test
    @Transactional
    void fullUpdateTaxTotalWithPatch() throws Exception {
        // Initialize the database
        taxTotalRepository.saveAndFlush(taxTotal);

        int databaseSizeBeforeUpdate = taxTotalRepository.findAll().size();

        // Update the taxTotal using partial update
        TaxTotal partialUpdatedTaxTotal = new TaxTotal();
        partialUpdatedTaxTotal.setId(taxTotal.getId());

        partialUpdatedTaxTotal.taxType(UPDATED_TAX_TYPE).amount(UPDATED_AMOUNT);

        restTaxTotalMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTaxTotal.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTaxTotal))
            )
            .andExpect(status().isOk());

        // Validate the TaxTotal in the database
        List<TaxTotal> taxTotalList = taxTotalRepository.findAll();
        assertThat(taxTotalList).hasSize(databaseSizeBeforeUpdate);
        TaxTotal testTaxTotal = taxTotalList.get(taxTotalList.size() - 1);
        assertThat(testTaxTotal.getTaxType()).isEqualTo(UPDATED_TAX_TYPE);
        assertThat(testTaxTotal.getAmount()).isEqualByComparingTo(UPDATED_AMOUNT);
    }

    @Test
    @Transactional
    void patchNonExistingTaxTotal() throws Exception {
        int databaseSizeBeforeUpdate = taxTotalRepository.findAll().size();
        taxTotal.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTaxTotalMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, taxTotal.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(taxTotal))
            )
            .andExpect(status().isBadRequest());

        // Validate the TaxTotal in the database
        List<TaxTotal> taxTotalList = taxTotalRepository.findAll();
        assertThat(taxTotalList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchTaxTotal() throws Exception {
        int databaseSizeBeforeUpdate = taxTotalRepository.findAll().size();
        taxTotal.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTaxTotalMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(taxTotal))
            )
            .andExpect(status().isBadRequest());

        // Validate the TaxTotal in the database
        List<TaxTotal> taxTotalList = taxTotalRepository.findAll();
        assertThat(taxTotalList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamTaxTotal() throws Exception {
        int databaseSizeBeforeUpdate = taxTotalRepository.findAll().size();
        taxTotal.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTaxTotalMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(taxTotal)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the TaxTotal in the database
        List<TaxTotal> taxTotalList = taxTotalRepository.findAll();
        assertThat(taxTotalList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteTaxTotal() throws Exception {
        // Initialize the database
        taxTotalRepository.saveAndFlush(taxTotal);

        int databaseSizeBeforeDelete = taxTotalRepository.findAll().size();

        // Delete the taxTotal
        restTaxTotalMockMvc
            .perform(delete(ENTITY_API_URL_ID, taxTotal.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TaxTotal> taxTotalList = taxTotalRepository.findAll();
        assertThat(taxTotalList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
