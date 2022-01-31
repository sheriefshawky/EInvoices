package eg.inv.web.rest;

import static eg.inv.web.rest.TestUtil.sameNumber;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import eg.inv.IntegrationTest;
import eg.inv.domain.Value;
import eg.inv.repository.ValueRepository;
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
 * Integration tests for the {@link ValueResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ValueResourceIT {

    private static final String DEFAULT_CURRENCY_SOLD = "AAAAAAAAAA";
    private static final String UPDATED_CURRENCY_SOLD = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_AMOUNT_EGP = new BigDecimal(1);
    private static final BigDecimal UPDATED_AMOUNT_EGP = new BigDecimal(2);

    private static final BigDecimal DEFAULT_AMOUNT_SOLD = new BigDecimal(1);
    private static final BigDecimal UPDATED_AMOUNT_SOLD = new BigDecimal(2);

    private static final BigDecimal DEFAULT_CURRENCY_EXCHANGE_RATE = new BigDecimal(1);
    private static final BigDecimal UPDATED_CURRENCY_EXCHANGE_RATE = new BigDecimal(2);

    private static final String ENTITY_API_URL = "/api/values";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ValueRepository valueRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restValueMockMvc;

    private Value value;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Value createEntity(EntityManager em) {
        Value value = new Value()
            .currencySold(DEFAULT_CURRENCY_SOLD)
            .amountEGP(DEFAULT_AMOUNT_EGP)
            .amountSold(DEFAULT_AMOUNT_SOLD)
            .currencyExchangeRate(DEFAULT_CURRENCY_EXCHANGE_RATE);
        return value;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Value createUpdatedEntity(EntityManager em) {
        Value value = new Value()
            .currencySold(UPDATED_CURRENCY_SOLD)
            .amountEGP(UPDATED_AMOUNT_EGP)
            .amountSold(UPDATED_AMOUNT_SOLD)
            .currencyExchangeRate(UPDATED_CURRENCY_EXCHANGE_RATE);
        return value;
    }

    @BeforeEach
    public void initTest() {
        value = createEntity(em);
    }

    @Test
    @Transactional
    void createValue() throws Exception {
        int databaseSizeBeforeCreate = valueRepository.findAll().size();
        // Create the Value
        restValueMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(value)))
            .andExpect(status().isCreated());

        // Validate the Value in the database
        List<Value> valueList = valueRepository.findAll();
        assertThat(valueList).hasSize(databaseSizeBeforeCreate + 1);
        Value testValue = valueList.get(valueList.size() - 1);
        assertThat(testValue.getCurrencySold()).isEqualTo(DEFAULT_CURRENCY_SOLD);
        assertThat(testValue.getAmountEGP()).isEqualByComparingTo(DEFAULT_AMOUNT_EGP);
        assertThat(testValue.getAmountSold()).isEqualByComparingTo(DEFAULT_AMOUNT_SOLD);
        assertThat(testValue.getCurrencyExchangeRate()).isEqualByComparingTo(DEFAULT_CURRENCY_EXCHANGE_RATE);
    }

    @Test
    @Transactional
    void createValueWithExistingId() throws Exception {
        // Create the Value with an existing ID
        value.setId(1L);

        int databaseSizeBeforeCreate = valueRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restValueMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(value)))
            .andExpect(status().isBadRequest());

        // Validate the Value in the database
        List<Value> valueList = valueRepository.findAll();
        assertThat(valueList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllValues() throws Exception {
        // Initialize the database
        valueRepository.saveAndFlush(value);

        // Get all the valueList
        restValueMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(value.getId().intValue())))
            .andExpect(jsonPath("$.[*].currencySold").value(hasItem(DEFAULT_CURRENCY_SOLD)))
            .andExpect(jsonPath("$.[*].amountEGP").value(hasItem(sameNumber(DEFAULT_AMOUNT_EGP))))
            .andExpect(jsonPath("$.[*].amountSold").value(hasItem(sameNumber(DEFAULT_AMOUNT_SOLD))))
            .andExpect(jsonPath("$.[*].currencyExchangeRate").value(hasItem(sameNumber(DEFAULT_CURRENCY_EXCHANGE_RATE))));
    }

    @Test
    @Transactional
    void getValue() throws Exception {
        // Initialize the database
        valueRepository.saveAndFlush(value);

        // Get the value
        restValueMockMvc
            .perform(get(ENTITY_API_URL_ID, value.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(value.getId().intValue()))
            .andExpect(jsonPath("$.currencySold").value(DEFAULT_CURRENCY_SOLD))
            .andExpect(jsonPath("$.amountEGP").value(sameNumber(DEFAULT_AMOUNT_EGP)))
            .andExpect(jsonPath("$.amountSold").value(sameNumber(DEFAULT_AMOUNT_SOLD)))
            .andExpect(jsonPath("$.currencyExchangeRate").value(sameNumber(DEFAULT_CURRENCY_EXCHANGE_RATE)));
    }

    @Test
    @Transactional
    void getNonExistingValue() throws Exception {
        // Get the value
        restValueMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewValue() throws Exception {
        // Initialize the database
        valueRepository.saveAndFlush(value);

        int databaseSizeBeforeUpdate = valueRepository.findAll().size();

        // Update the value
        Value updatedValue = valueRepository.findById(value.getId()).get();
        // Disconnect from session so that the updates on updatedValue are not directly saved in db
        em.detach(updatedValue);
        updatedValue
            .currencySold(UPDATED_CURRENCY_SOLD)
            .amountEGP(UPDATED_AMOUNT_EGP)
            .amountSold(UPDATED_AMOUNT_SOLD)
            .currencyExchangeRate(UPDATED_CURRENCY_EXCHANGE_RATE);

        restValueMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedValue.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedValue))
            )
            .andExpect(status().isOk());

        // Validate the Value in the database
        List<Value> valueList = valueRepository.findAll();
        assertThat(valueList).hasSize(databaseSizeBeforeUpdate);
        Value testValue = valueList.get(valueList.size() - 1);
        assertThat(testValue.getCurrencySold()).isEqualTo(UPDATED_CURRENCY_SOLD);
        assertThat(testValue.getAmountEGP()).isEqualByComparingTo(UPDATED_AMOUNT_EGP);
        assertThat(testValue.getAmountSold()).isEqualByComparingTo(UPDATED_AMOUNT_SOLD);
        assertThat(testValue.getCurrencyExchangeRate()).isEqualByComparingTo(UPDATED_CURRENCY_EXCHANGE_RATE);
    }

    @Test
    @Transactional
    void putNonExistingValue() throws Exception {
        int databaseSizeBeforeUpdate = valueRepository.findAll().size();
        value.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restValueMockMvc
            .perform(
                put(ENTITY_API_URL_ID, value.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(value))
            )
            .andExpect(status().isBadRequest());

        // Validate the Value in the database
        List<Value> valueList = valueRepository.findAll();
        assertThat(valueList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchValue() throws Exception {
        int databaseSizeBeforeUpdate = valueRepository.findAll().size();
        value.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restValueMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(value))
            )
            .andExpect(status().isBadRequest());

        // Validate the Value in the database
        List<Value> valueList = valueRepository.findAll();
        assertThat(valueList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamValue() throws Exception {
        int databaseSizeBeforeUpdate = valueRepository.findAll().size();
        value.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restValueMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(value)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Value in the database
        List<Value> valueList = valueRepository.findAll();
        assertThat(valueList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateValueWithPatch() throws Exception {
        // Initialize the database
        valueRepository.saveAndFlush(value);

        int databaseSizeBeforeUpdate = valueRepository.findAll().size();

        // Update the value using partial update
        Value partialUpdatedValue = new Value();
        partialUpdatedValue.setId(value.getId());

        partialUpdatedValue.currencySold(UPDATED_CURRENCY_SOLD).amountEGP(UPDATED_AMOUNT_EGP);

        restValueMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedValue.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedValue))
            )
            .andExpect(status().isOk());

        // Validate the Value in the database
        List<Value> valueList = valueRepository.findAll();
        assertThat(valueList).hasSize(databaseSizeBeforeUpdate);
        Value testValue = valueList.get(valueList.size() - 1);
        assertThat(testValue.getCurrencySold()).isEqualTo(UPDATED_CURRENCY_SOLD);
        assertThat(testValue.getAmountEGP()).isEqualByComparingTo(UPDATED_AMOUNT_EGP);
        assertThat(testValue.getAmountSold()).isEqualByComparingTo(DEFAULT_AMOUNT_SOLD);
        assertThat(testValue.getCurrencyExchangeRate()).isEqualByComparingTo(DEFAULT_CURRENCY_EXCHANGE_RATE);
    }

    @Test
    @Transactional
    void fullUpdateValueWithPatch() throws Exception {
        // Initialize the database
        valueRepository.saveAndFlush(value);

        int databaseSizeBeforeUpdate = valueRepository.findAll().size();

        // Update the value using partial update
        Value partialUpdatedValue = new Value();
        partialUpdatedValue.setId(value.getId());

        partialUpdatedValue
            .currencySold(UPDATED_CURRENCY_SOLD)
            .amountEGP(UPDATED_AMOUNT_EGP)
            .amountSold(UPDATED_AMOUNT_SOLD)
            .currencyExchangeRate(UPDATED_CURRENCY_EXCHANGE_RATE);

        restValueMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedValue.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedValue))
            )
            .andExpect(status().isOk());

        // Validate the Value in the database
        List<Value> valueList = valueRepository.findAll();
        assertThat(valueList).hasSize(databaseSizeBeforeUpdate);
        Value testValue = valueList.get(valueList.size() - 1);
        assertThat(testValue.getCurrencySold()).isEqualTo(UPDATED_CURRENCY_SOLD);
        assertThat(testValue.getAmountEGP()).isEqualByComparingTo(UPDATED_AMOUNT_EGP);
        assertThat(testValue.getAmountSold()).isEqualByComparingTo(UPDATED_AMOUNT_SOLD);
        assertThat(testValue.getCurrencyExchangeRate()).isEqualByComparingTo(UPDATED_CURRENCY_EXCHANGE_RATE);
    }

    @Test
    @Transactional
    void patchNonExistingValue() throws Exception {
        int databaseSizeBeforeUpdate = valueRepository.findAll().size();
        value.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restValueMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, value.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(value))
            )
            .andExpect(status().isBadRequest());

        // Validate the Value in the database
        List<Value> valueList = valueRepository.findAll();
        assertThat(valueList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchValue() throws Exception {
        int databaseSizeBeforeUpdate = valueRepository.findAll().size();
        value.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restValueMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(value))
            )
            .andExpect(status().isBadRequest());

        // Validate the Value in the database
        List<Value> valueList = valueRepository.findAll();
        assertThat(valueList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamValue() throws Exception {
        int databaseSizeBeforeUpdate = valueRepository.findAll().size();
        value.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restValueMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(value)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Value in the database
        List<Value> valueList = valueRepository.findAll();
        assertThat(valueList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteValue() throws Exception {
        // Initialize the database
        valueRepository.saveAndFlush(value);

        int databaseSizeBeforeDelete = valueRepository.findAll().size();

        // Delete the value
        restValueMockMvc
            .perform(delete(ENTITY_API_URL_ID, value.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Value> valueList = valueRepository.findAll();
        assertThat(valueList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
