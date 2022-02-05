package eg.inv.web.rest;

import static eg.inv.web.rest.TestUtil.sameNumber;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import eg.inv.IntegrationTest;
import eg.inv.domain.ItemValue;
import eg.inv.repository.ItemValueRepository;
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
 * Integration tests for the {@link ItemValueResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ItemValueResourceIT {

    private static final String DEFAULT_CURRENCY_SOLD = "AAAAAAAAAA";
    private static final String UPDATED_CURRENCY_SOLD = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_AMOUNT_EGP = new BigDecimal(1);
    private static final BigDecimal UPDATED_AMOUNT_EGP = new BigDecimal(2);

    private static final BigDecimal DEFAULT_AMOUNT_SOLD = new BigDecimal(1);
    private static final BigDecimal UPDATED_AMOUNT_SOLD = new BigDecimal(2);

    private static final BigDecimal DEFAULT_CURRENCY_EXCHANGE_RATE = new BigDecimal(1);
    private static final BigDecimal UPDATED_CURRENCY_EXCHANGE_RATE = new BigDecimal(2);

    private static final String ENTITY_API_URL = "/api/item-values";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ItemValueRepository itemValueRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restItemValueMockMvc;

    private ItemValue itemValue;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ItemValue createEntity(EntityManager em) {
        ItemValue itemValue = new ItemValue()
            .currencySold(DEFAULT_CURRENCY_SOLD)
            .amountEGP(DEFAULT_AMOUNT_EGP)
            .amountSold(DEFAULT_AMOUNT_SOLD)
            .currencyExchangeRate(DEFAULT_CURRENCY_EXCHANGE_RATE);
        return itemValue;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ItemValue createUpdatedEntity(EntityManager em) {
        ItemValue itemValue = new ItemValue()
            .currencySold(UPDATED_CURRENCY_SOLD)
            .amountEGP(UPDATED_AMOUNT_EGP)
            .amountSold(UPDATED_AMOUNT_SOLD)
            .currencyExchangeRate(UPDATED_CURRENCY_EXCHANGE_RATE);
        return itemValue;
    }

    @BeforeEach
    public void initTest() {
        itemValue = createEntity(em);
    }

    @Test
    @Transactional
    void createItemValue() throws Exception {
        int databaseSizeBeforeCreate = itemValueRepository.findAll().size();
        // Create the ItemValue
        restItemValueMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(itemValue)))
            .andExpect(status().isCreated());

        // Validate the ItemValue in the database
        List<ItemValue> itemValueList = itemValueRepository.findAll();
        assertThat(itemValueList).hasSize(databaseSizeBeforeCreate + 1);
        ItemValue testItemValue = itemValueList.get(itemValueList.size() - 1);
        assertThat(testItemValue.getCurrencySold()).isEqualTo(DEFAULT_CURRENCY_SOLD);
        assertThat(testItemValue.getAmountEGP()).isEqualByComparingTo(DEFAULT_AMOUNT_EGP);
        assertThat(testItemValue.getAmountSold()).isEqualByComparingTo(DEFAULT_AMOUNT_SOLD);
        assertThat(testItemValue.getCurrencyExchangeRate()).isEqualByComparingTo(DEFAULT_CURRENCY_EXCHANGE_RATE);
    }

    @Test
    @Transactional
    void createItemValueWithExistingId() throws Exception {
        // Create the ItemValue with an existing ID
        itemValue.setId(1L);

        int databaseSizeBeforeCreate = itemValueRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restItemValueMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(itemValue)))
            .andExpect(status().isBadRequest());

        // Validate the ItemValue in the database
        List<ItemValue> itemValueList = itemValueRepository.findAll();
        assertThat(itemValueList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllItemValues() throws Exception {
        // Initialize the database
        itemValueRepository.saveAndFlush(itemValue);

        // Get all the itemValueList
        restItemValueMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(itemValue.getId().intValue())))
            .andExpect(jsonPath("$.[*].currencySold").value(hasItem(DEFAULT_CURRENCY_SOLD)))
            .andExpect(jsonPath("$.[*].amountEGP").value(hasItem(sameNumber(DEFAULT_AMOUNT_EGP))))
            .andExpect(jsonPath("$.[*].amountSold").value(hasItem(sameNumber(DEFAULT_AMOUNT_SOLD))))
            .andExpect(jsonPath("$.[*].currencyExchangeRate").value(hasItem(sameNumber(DEFAULT_CURRENCY_EXCHANGE_RATE))));
    }

    @Test
    @Transactional
    void getItemValue() throws Exception {
        // Initialize the database
        itemValueRepository.saveAndFlush(itemValue);

        // Get the itemValue
        restItemValueMockMvc
            .perform(get(ENTITY_API_URL_ID, itemValue.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(itemValue.getId().intValue()))
            .andExpect(jsonPath("$.currencySold").value(DEFAULT_CURRENCY_SOLD))
            .andExpect(jsonPath("$.amountEGP").value(sameNumber(DEFAULT_AMOUNT_EGP)))
            .andExpect(jsonPath("$.amountSold").value(sameNumber(DEFAULT_AMOUNT_SOLD)))
            .andExpect(jsonPath("$.currencyExchangeRate").value(sameNumber(DEFAULT_CURRENCY_EXCHANGE_RATE)));
    }

    @Test
    @Transactional
    void getNonExistingItemValue() throws Exception {
        // Get the itemValue
        restItemValueMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewItemValue() throws Exception {
        // Initialize the database
        itemValueRepository.saveAndFlush(itemValue);

        int databaseSizeBeforeUpdate = itemValueRepository.findAll().size();

        // Update the itemValue
        ItemValue updatedItemValue = itemValueRepository.findById(itemValue.getId()).get();
        // Disconnect from session so that the updates on updatedItemValue are not directly saved in db
        em.detach(updatedItemValue);
        updatedItemValue
            .currencySold(UPDATED_CURRENCY_SOLD)
            .amountEGP(UPDATED_AMOUNT_EGP)
            .amountSold(UPDATED_AMOUNT_SOLD)
            .currencyExchangeRate(UPDATED_CURRENCY_EXCHANGE_RATE);

        restItemValueMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedItemValue.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedItemValue))
            )
            .andExpect(status().isOk());

        // Validate the ItemValue in the database
        List<ItemValue> itemValueList = itemValueRepository.findAll();
        assertThat(itemValueList).hasSize(databaseSizeBeforeUpdate);
        ItemValue testItemValue = itemValueList.get(itemValueList.size() - 1);
        assertThat(testItemValue.getCurrencySold()).isEqualTo(UPDATED_CURRENCY_SOLD);
        assertThat(testItemValue.getAmountEGP()).isEqualByComparingTo(UPDATED_AMOUNT_EGP);
        assertThat(testItemValue.getAmountSold()).isEqualByComparingTo(UPDATED_AMOUNT_SOLD);
        assertThat(testItemValue.getCurrencyExchangeRate()).isEqualByComparingTo(UPDATED_CURRENCY_EXCHANGE_RATE);
    }

    @Test
    @Transactional
    void putNonExistingItemValue() throws Exception {
        int databaseSizeBeforeUpdate = itemValueRepository.findAll().size();
        itemValue.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restItemValueMockMvc
            .perform(
                put(ENTITY_API_URL_ID, itemValue.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(itemValue))
            )
            .andExpect(status().isBadRequest());

        // Validate the ItemValue in the database
        List<ItemValue> itemValueList = itemValueRepository.findAll();
        assertThat(itemValueList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchItemValue() throws Exception {
        int databaseSizeBeforeUpdate = itemValueRepository.findAll().size();
        itemValue.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restItemValueMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(itemValue))
            )
            .andExpect(status().isBadRequest());

        // Validate the ItemValue in the database
        List<ItemValue> itemValueList = itemValueRepository.findAll();
        assertThat(itemValueList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamItemValue() throws Exception {
        int databaseSizeBeforeUpdate = itemValueRepository.findAll().size();
        itemValue.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restItemValueMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(itemValue)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the ItemValue in the database
        List<ItemValue> itemValueList = itemValueRepository.findAll();
        assertThat(itemValueList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateItemValueWithPatch() throws Exception {
        // Initialize the database
        itemValueRepository.saveAndFlush(itemValue);

        int databaseSizeBeforeUpdate = itemValueRepository.findAll().size();

        // Update the itemValue using partial update
        ItemValue partialUpdatedItemValue = new ItemValue();
        partialUpdatedItemValue.setId(itemValue.getId());

        partialUpdatedItemValue
            .currencySold(UPDATED_CURRENCY_SOLD)
            .amountEGP(UPDATED_AMOUNT_EGP)
            .currencyExchangeRate(UPDATED_CURRENCY_EXCHANGE_RATE);

        restItemValueMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedItemValue.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedItemValue))
            )
            .andExpect(status().isOk());

        // Validate the ItemValue in the database
        List<ItemValue> itemValueList = itemValueRepository.findAll();
        assertThat(itemValueList).hasSize(databaseSizeBeforeUpdate);
        ItemValue testItemValue = itemValueList.get(itemValueList.size() - 1);
        assertThat(testItemValue.getCurrencySold()).isEqualTo(UPDATED_CURRENCY_SOLD);
        assertThat(testItemValue.getAmountEGP()).isEqualByComparingTo(UPDATED_AMOUNT_EGP);
        assertThat(testItemValue.getAmountSold()).isEqualByComparingTo(DEFAULT_AMOUNT_SOLD);
        assertThat(testItemValue.getCurrencyExchangeRate()).isEqualByComparingTo(UPDATED_CURRENCY_EXCHANGE_RATE);
    }

    @Test
    @Transactional
    void fullUpdateItemValueWithPatch() throws Exception {
        // Initialize the database
        itemValueRepository.saveAndFlush(itemValue);

        int databaseSizeBeforeUpdate = itemValueRepository.findAll().size();

        // Update the itemValue using partial update
        ItemValue partialUpdatedItemValue = new ItemValue();
        partialUpdatedItemValue.setId(itemValue.getId());

        partialUpdatedItemValue
            .currencySold(UPDATED_CURRENCY_SOLD)
            .amountEGP(UPDATED_AMOUNT_EGP)
            .amountSold(UPDATED_AMOUNT_SOLD)
            .currencyExchangeRate(UPDATED_CURRENCY_EXCHANGE_RATE);

        restItemValueMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedItemValue.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedItemValue))
            )
            .andExpect(status().isOk());

        // Validate the ItemValue in the database
        List<ItemValue> itemValueList = itemValueRepository.findAll();
        assertThat(itemValueList).hasSize(databaseSizeBeforeUpdate);
        ItemValue testItemValue = itemValueList.get(itemValueList.size() - 1);
        assertThat(testItemValue.getCurrencySold()).isEqualTo(UPDATED_CURRENCY_SOLD);
        assertThat(testItemValue.getAmountEGP()).isEqualByComparingTo(UPDATED_AMOUNT_EGP);
        assertThat(testItemValue.getAmountSold()).isEqualByComparingTo(UPDATED_AMOUNT_SOLD);
        assertThat(testItemValue.getCurrencyExchangeRate()).isEqualByComparingTo(UPDATED_CURRENCY_EXCHANGE_RATE);
    }

    @Test
    @Transactional
    void patchNonExistingItemValue() throws Exception {
        int databaseSizeBeforeUpdate = itemValueRepository.findAll().size();
        itemValue.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restItemValueMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, itemValue.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(itemValue))
            )
            .andExpect(status().isBadRequest());

        // Validate the ItemValue in the database
        List<ItemValue> itemValueList = itemValueRepository.findAll();
        assertThat(itemValueList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchItemValue() throws Exception {
        int databaseSizeBeforeUpdate = itemValueRepository.findAll().size();
        itemValue.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restItemValueMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(itemValue))
            )
            .andExpect(status().isBadRequest());

        // Validate the ItemValue in the database
        List<ItemValue> itemValueList = itemValueRepository.findAll();
        assertThat(itemValueList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamItemValue() throws Exception {
        int databaseSizeBeforeUpdate = itemValueRepository.findAll().size();
        itemValue.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restItemValueMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(itemValue))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ItemValue in the database
        List<ItemValue> itemValueList = itemValueRepository.findAll();
        assertThat(itemValueList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteItemValue() throws Exception {
        // Initialize the database
        itemValueRepository.saveAndFlush(itemValue);

        int databaseSizeBeforeDelete = itemValueRepository.findAll().size();

        // Delete the itemValue
        restItemValueMockMvc
            .perform(delete(ENTITY_API_URL_ID, itemValue.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ItemValue> itemValueList = itemValueRepository.findAll();
        assertThat(itemValueList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
