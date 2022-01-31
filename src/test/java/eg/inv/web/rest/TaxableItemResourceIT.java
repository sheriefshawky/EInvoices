package eg.inv.web.rest;

import static eg.inv.web.rest.TestUtil.sameNumber;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import eg.inv.IntegrationTest;
import eg.inv.domain.TaxableItem;
import eg.inv.repository.TaxableItemRepository;
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
 * Integration tests for the {@link TaxableItemResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class TaxableItemResourceIT {

    private static final String DEFAULT_TAX_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TAX_TYPE = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_AMOUNT = new BigDecimal(1);
    private static final BigDecimal UPDATED_AMOUNT = new BigDecimal(2);

    private static final String DEFAULT_SUB_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_SUB_TYPE = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_RATE = new BigDecimal(1);
    private static final BigDecimal UPDATED_RATE = new BigDecimal(2);

    private static final String ENTITY_API_URL = "/api/taxable-items";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private TaxableItemRepository taxableItemRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTaxableItemMockMvc;

    private TaxableItem taxableItem;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TaxableItem createEntity(EntityManager em) {
        TaxableItem taxableItem = new TaxableItem()
            .taxType(DEFAULT_TAX_TYPE)
            .amount(DEFAULT_AMOUNT)
            .subType(DEFAULT_SUB_TYPE)
            .rate(DEFAULT_RATE);
        return taxableItem;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TaxableItem createUpdatedEntity(EntityManager em) {
        TaxableItem taxableItem = new TaxableItem()
            .taxType(UPDATED_TAX_TYPE)
            .amount(UPDATED_AMOUNT)
            .subType(UPDATED_SUB_TYPE)
            .rate(UPDATED_RATE);
        return taxableItem;
    }

    @BeforeEach
    public void initTest() {
        taxableItem = createEntity(em);
    }

    @Test
    @Transactional
    void createTaxableItem() throws Exception {
        int databaseSizeBeforeCreate = taxableItemRepository.findAll().size();
        // Create the TaxableItem
        restTaxableItemMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(taxableItem)))
            .andExpect(status().isCreated());

        // Validate the TaxableItem in the database
        List<TaxableItem> taxableItemList = taxableItemRepository.findAll();
        assertThat(taxableItemList).hasSize(databaseSizeBeforeCreate + 1);
        TaxableItem testTaxableItem = taxableItemList.get(taxableItemList.size() - 1);
        assertThat(testTaxableItem.getTaxType()).isEqualTo(DEFAULT_TAX_TYPE);
        assertThat(testTaxableItem.getAmount()).isEqualByComparingTo(DEFAULT_AMOUNT);
        assertThat(testTaxableItem.getSubType()).isEqualTo(DEFAULT_SUB_TYPE);
        assertThat(testTaxableItem.getRate()).isEqualByComparingTo(DEFAULT_RATE);
    }

    @Test
    @Transactional
    void createTaxableItemWithExistingId() throws Exception {
        // Create the TaxableItem with an existing ID
        taxableItem.setId(1L);

        int databaseSizeBeforeCreate = taxableItemRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restTaxableItemMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(taxableItem)))
            .andExpect(status().isBadRequest());

        // Validate the TaxableItem in the database
        List<TaxableItem> taxableItemList = taxableItemRepository.findAll();
        assertThat(taxableItemList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllTaxableItems() throws Exception {
        // Initialize the database
        taxableItemRepository.saveAndFlush(taxableItem);

        // Get all the taxableItemList
        restTaxableItemMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(taxableItem.getId().intValue())))
            .andExpect(jsonPath("$.[*].taxType").value(hasItem(DEFAULT_TAX_TYPE)))
            .andExpect(jsonPath("$.[*].amount").value(hasItem(sameNumber(DEFAULT_AMOUNT))))
            .andExpect(jsonPath("$.[*].subType").value(hasItem(DEFAULT_SUB_TYPE)))
            .andExpect(jsonPath("$.[*].rate").value(hasItem(sameNumber(DEFAULT_RATE))));
    }

    @Test
    @Transactional
    void getTaxableItem() throws Exception {
        // Initialize the database
        taxableItemRepository.saveAndFlush(taxableItem);

        // Get the taxableItem
        restTaxableItemMockMvc
            .perform(get(ENTITY_API_URL_ID, taxableItem.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(taxableItem.getId().intValue()))
            .andExpect(jsonPath("$.taxType").value(DEFAULT_TAX_TYPE))
            .andExpect(jsonPath("$.amount").value(sameNumber(DEFAULT_AMOUNT)))
            .andExpect(jsonPath("$.subType").value(DEFAULT_SUB_TYPE))
            .andExpect(jsonPath("$.rate").value(sameNumber(DEFAULT_RATE)));
    }

    @Test
    @Transactional
    void getNonExistingTaxableItem() throws Exception {
        // Get the taxableItem
        restTaxableItemMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewTaxableItem() throws Exception {
        // Initialize the database
        taxableItemRepository.saveAndFlush(taxableItem);

        int databaseSizeBeforeUpdate = taxableItemRepository.findAll().size();

        // Update the taxableItem
        TaxableItem updatedTaxableItem = taxableItemRepository.findById(taxableItem.getId()).get();
        // Disconnect from session so that the updates on updatedTaxableItem are not directly saved in db
        em.detach(updatedTaxableItem);
        updatedTaxableItem.taxType(UPDATED_TAX_TYPE).amount(UPDATED_AMOUNT).subType(UPDATED_SUB_TYPE).rate(UPDATED_RATE);

        restTaxableItemMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedTaxableItem.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedTaxableItem))
            )
            .andExpect(status().isOk());

        // Validate the TaxableItem in the database
        List<TaxableItem> taxableItemList = taxableItemRepository.findAll();
        assertThat(taxableItemList).hasSize(databaseSizeBeforeUpdate);
        TaxableItem testTaxableItem = taxableItemList.get(taxableItemList.size() - 1);
        assertThat(testTaxableItem.getTaxType()).isEqualTo(UPDATED_TAX_TYPE);
        assertThat(testTaxableItem.getAmount()).isEqualByComparingTo(UPDATED_AMOUNT);
        assertThat(testTaxableItem.getSubType()).isEqualTo(UPDATED_SUB_TYPE);
        assertThat(testTaxableItem.getRate()).isEqualByComparingTo(UPDATED_RATE);
    }

    @Test
    @Transactional
    void putNonExistingTaxableItem() throws Exception {
        int databaseSizeBeforeUpdate = taxableItemRepository.findAll().size();
        taxableItem.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTaxableItemMockMvc
            .perform(
                put(ENTITY_API_URL_ID, taxableItem.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(taxableItem))
            )
            .andExpect(status().isBadRequest());

        // Validate the TaxableItem in the database
        List<TaxableItem> taxableItemList = taxableItemRepository.findAll();
        assertThat(taxableItemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchTaxableItem() throws Exception {
        int databaseSizeBeforeUpdate = taxableItemRepository.findAll().size();
        taxableItem.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTaxableItemMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(taxableItem))
            )
            .andExpect(status().isBadRequest());

        // Validate the TaxableItem in the database
        List<TaxableItem> taxableItemList = taxableItemRepository.findAll();
        assertThat(taxableItemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamTaxableItem() throws Exception {
        int databaseSizeBeforeUpdate = taxableItemRepository.findAll().size();
        taxableItem.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTaxableItemMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(taxableItem)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the TaxableItem in the database
        List<TaxableItem> taxableItemList = taxableItemRepository.findAll();
        assertThat(taxableItemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateTaxableItemWithPatch() throws Exception {
        // Initialize the database
        taxableItemRepository.saveAndFlush(taxableItem);

        int databaseSizeBeforeUpdate = taxableItemRepository.findAll().size();

        // Update the taxableItem using partial update
        TaxableItem partialUpdatedTaxableItem = new TaxableItem();
        partialUpdatedTaxableItem.setId(taxableItem.getId());

        partialUpdatedTaxableItem.taxType(UPDATED_TAX_TYPE);

        restTaxableItemMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTaxableItem.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTaxableItem))
            )
            .andExpect(status().isOk());

        // Validate the TaxableItem in the database
        List<TaxableItem> taxableItemList = taxableItemRepository.findAll();
        assertThat(taxableItemList).hasSize(databaseSizeBeforeUpdate);
        TaxableItem testTaxableItem = taxableItemList.get(taxableItemList.size() - 1);
        assertThat(testTaxableItem.getTaxType()).isEqualTo(UPDATED_TAX_TYPE);
        assertThat(testTaxableItem.getAmount()).isEqualByComparingTo(DEFAULT_AMOUNT);
        assertThat(testTaxableItem.getSubType()).isEqualTo(DEFAULT_SUB_TYPE);
        assertThat(testTaxableItem.getRate()).isEqualByComparingTo(DEFAULT_RATE);
    }

    @Test
    @Transactional
    void fullUpdateTaxableItemWithPatch() throws Exception {
        // Initialize the database
        taxableItemRepository.saveAndFlush(taxableItem);

        int databaseSizeBeforeUpdate = taxableItemRepository.findAll().size();

        // Update the taxableItem using partial update
        TaxableItem partialUpdatedTaxableItem = new TaxableItem();
        partialUpdatedTaxableItem.setId(taxableItem.getId());

        partialUpdatedTaxableItem.taxType(UPDATED_TAX_TYPE).amount(UPDATED_AMOUNT).subType(UPDATED_SUB_TYPE).rate(UPDATED_RATE);

        restTaxableItemMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTaxableItem.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTaxableItem))
            )
            .andExpect(status().isOk());

        // Validate the TaxableItem in the database
        List<TaxableItem> taxableItemList = taxableItemRepository.findAll();
        assertThat(taxableItemList).hasSize(databaseSizeBeforeUpdate);
        TaxableItem testTaxableItem = taxableItemList.get(taxableItemList.size() - 1);
        assertThat(testTaxableItem.getTaxType()).isEqualTo(UPDATED_TAX_TYPE);
        assertThat(testTaxableItem.getAmount()).isEqualByComparingTo(UPDATED_AMOUNT);
        assertThat(testTaxableItem.getSubType()).isEqualTo(UPDATED_SUB_TYPE);
        assertThat(testTaxableItem.getRate()).isEqualByComparingTo(UPDATED_RATE);
    }

    @Test
    @Transactional
    void patchNonExistingTaxableItem() throws Exception {
        int databaseSizeBeforeUpdate = taxableItemRepository.findAll().size();
        taxableItem.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTaxableItemMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, taxableItem.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(taxableItem))
            )
            .andExpect(status().isBadRequest());

        // Validate the TaxableItem in the database
        List<TaxableItem> taxableItemList = taxableItemRepository.findAll();
        assertThat(taxableItemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchTaxableItem() throws Exception {
        int databaseSizeBeforeUpdate = taxableItemRepository.findAll().size();
        taxableItem.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTaxableItemMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(taxableItem))
            )
            .andExpect(status().isBadRequest());

        // Validate the TaxableItem in the database
        List<TaxableItem> taxableItemList = taxableItemRepository.findAll();
        assertThat(taxableItemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamTaxableItem() throws Exception {
        int databaseSizeBeforeUpdate = taxableItemRepository.findAll().size();
        taxableItem.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTaxableItemMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(taxableItem))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the TaxableItem in the database
        List<TaxableItem> taxableItemList = taxableItemRepository.findAll();
        assertThat(taxableItemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteTaxableItem() throws Exception {
        // Initialize the database
        taxableItemRepository.saveAndFlush(taxableItem);

        int databaseSizeBeforeDelete = taxableItemRepository.findAll().size();

        // Delete the taxableItem
        restTaxableItemMockMvc
            .perform(delete(ENTITY_API_URL_ID, taxableItem.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TaxableItem> taxableItemList = taxableItemRepository.findAll();
        assertThat(taxableItemList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
