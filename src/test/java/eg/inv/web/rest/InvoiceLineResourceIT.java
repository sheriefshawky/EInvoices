package eg.inv.web.rest;

import static eg.inv.web.rest.TestUtil.sameNumber;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import eg.inv.IntegrationTest;
import eg.inv.domain.InvoiceLine;
import eg.inv.repository.InvoiceLineRepository;
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
 * Integration tests for the {@link InvoiceLineResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class InvoiceLineResourceIT {

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_ITEM_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_ITEM_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_ITEM_CODE = "AAAAAAAAAA";
    private static final String UPDATED_ITEM_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_UNIT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_UNIT_TYPE = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_QUANTITY = new BigDecimal(1);
    private static final BigDecimal UPDATED_QUANTITY = new BigDecimal(2);

    private static final BigDecimal DEFAULT_SALES_TOTAL = new BigDecimal(1);
    private static final BigDecimal UPDATED_SALES_TOTAL = new BigDecimal(2);

    private static final BigDecimal DEFAULT_TOTAL = new BigDecimal(1);
    private static final BigDecimal UPDATED_TOTAL = new BigDecimal(2);

    private static final BigDecimal DEFAULT_VALUE_DIFFERENCE = new BigDecimal(1);
    private static final BigDecimal UPDATED_VALUE_DIFFERENCE = new BigDecimal(2);

    private static final BigDecimal DEFAULT_TOTAL_TAXABLE_FEES = new BigDecimal(1);
    private static final BigDecimal UPDATED_TOTAL_TAXABLE_FEES = new BigDecimal(2);

    private static final BigDecimal DEFAULT_NET_TOTAL = new BigDecimal(1);
    private static final BigDecimal UPDATED_NET_TOTAL = new BigDecimal(2);

    private static final BigDecimal DEFAULT_ITEMS_DISCOUNT = new BigDecimal(1);
    private static final BigDecimal UPDATED_ITEMS_DISCOUNT = new BigDecimal(2);

    private static final String DEFAULT_INTERNAL_CODE = "AAAAAAAAAA";
    private static final String UPDATED_INTERNAL_CODE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/invoice-lines";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private InvoiceLineRepository invoiceLineRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restInvoiceLineMockMvc;

    private InvoiceLine invoiceLine;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static InvoiceLine createEntity(EntityManager em) {
        InvoiceLine invoiceLine = new InvoiceLine()
            .description(DEFAULT_DESCRIPTION)
            .itemType(DEFAULT_ITEM_TYPE)
            .itemCode(DEFAULT_ITEM_CODE)
            .unitType(DEFAULT_UNIT_TYPE)
            .quantity(DEFAULT_QUANTITY)
            .salesTotal(DEFAULT_SALES_TOTAL)
            .total(DEFAULT_TOTAL)
            .valueDifference(DEFAULT_VALUE_DIFFERENCE)
            .totalTaxableFees(DEFAULT_TOTAL_TAXABLE_FEES)
            .netTotal(DEFAULT_NET_TOTAL)
            .itemsDiscount(DEFAULT_ITEMS_DISCOUNT)
            .internalCode(DEFAULT_INTERNAL_CODE);
        return invoiceLine;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static InvoiceLine createUpdatedEntity(EntityManager em) {
        InvoiceLine invoiceLine = new InvoiceLine()
            .description(UPDATED_DESCRIPTION)
            .itemType(UPDATED_ITEM_TYPE)
            .itemCode(UPDATED_ITEM_CODE)
            .unitType(UPDATED_UNIT_TYPE)
            .quantity(UPDATED_QUANTITY)
            .salesTotal(UPDATED_SALES_TOTAL)
            .total(UPDATED_TOTAL)
            .valueDifference(UPDATED_VALUE_DIFFERENCE)
            .totalTaxableFees(UPDATED_TOTAL_TAXABLE_FEES)
            .netTotal(UPDATED_NET_TOTAL)
            .itemsDiscount(UPDATED_ITEMS_DISCOUNT)
            .internalCode(UPDATED_INTERNAL_CODE);
        return invoiceLine;
    }

    @BeforeEach
    public void initTest() {
        invoiceLine = createEntity(em);
    }

    @Test
    @Transactional
    void createInvoiceLine() throws Exception {
        int databaseSizeBeforeCreate = invoiceLineRepository.findAll().size();
        // Create the InvoiceLine
        restInvoiceLineMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(invoiceLine)))
            .andExpect(status().isCreated());

        // Validate the InvoiceLine in the database
        List<InvoiceLine> invoiceLineList = invoiceLineRepository.findAll();
        assertThat(invoiceLineList).hasSize(databaseSizeBeforeCreate + 1);
        InvoiceLine testInvoiceLine = invoiceLineList.get(invoiceLineList.size() - 1);
        assertThat(testInvoiceLine.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testInvoiceLine.getItemType()).isEqualTo(DEFAULT_ITEM_TYPE);
        assertThat(testInvoiceLine.getItemCode()).isEqualTo(DEFAULT_ITEM_CODE);
        assertThat(testInvoiceLine.getUnitType()).isEqualTo(DEFAULT_UNIT_TYPE);
        assertThat(testInvoiceLine.getQuantity()).isEqualByComparingTo(DEFAULT_QUANTITY);
        assertThat(testInvoiceLine.getSalesTotal()).isEqualByComparingTo(DEFAULT_SALES_TOTAL);
        assertThat(testInvoiceLine.getTotal()).isEqualByComparingTo(DEFAULT_TOTAL);
        assertThat(testInvoiceLine.getValueDifference()).isEqualByComparingTo(DEFAULT_VALUE_DIFFERENCE);
        assertThat(testInvoiceLine.getTotalTaxableFees()).isEqualByComparingTo(DEFAULT_TOTAL_TAXABLE_FEES);
        assertThat(testInvoiceLine.getNetTotal()).isEqualByComparingTo(DEFAULT_NET_TOTAL);
        assertThat(testInvoiceLine.getItemsDiscount()).isEqualByComparingTo(DEFAULT_ITEMS_DISCOUNT);
        assertThat(testInvoiceLine.getInternalCode()).isEqualTo(DEFAULT_INTERNAL_CODE);
    }

    @Test
    @Transactional
    void createInvoiceLineWithExistingId() throws Exception {
        // Create the InvoiceLine with an existing ID
        invoiceLine.setId(1L);

        int databaseSizeBeforeCreate = invoiceLineRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restInvoiceLineMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(invoiceLine)))
            .andExpect(status().isBadRequest());

        // Validate the InvoiceLine in the database
        List<InvoiceLine> invoiceLineList = invoiceLineRepository.findAll();
        assertThat(invoiceLineList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllInvoiceLines() throws Exception {
        // Initialize the database
        invoiceLineRepository.saveAndFlush(invoiceLine);

        // Get all the invoiceLineList
        restInvoiceLineMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(invoiceLine.getId().intValue())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].itemType").value(hasItem(DEFAULT_ITEM_TYPE)))
            .andExpect(jsonPath("$.[*].itemCode").value(hasItem(DEFAULT_ITEM_CODE)))
            .andExpect(jsonPath("$.[*].unitType").value(hasItem(DEFAULT_UNIT_TYPE)))
            .andExpect(jsonPath("$.[*].quantity").value(hasItem(sameNumber(DEFAULT_QUANTITY))))
            .andExpect(jsonPath("$.[*].salesTotal").value(hasItem(sameNumber(DEFAULT_SALES_TOTAL))))
            .andExpect(jsonPath("$.[*].total").value(hasItem(sameNumber(DEFAULT_TOTAL))))
            .andExpect(jsonPath("$.[*].valueDifference").value(hasItem(sameNumber(DEFAULT_VALUE_DIFFERENCE))))
            .andExpect(jsonPath("$.[*].totalTaxableFees").value(hasItem(sameNumber(DEFAULT_TOTAL_TAXABLE_FEES))))
            .andExpect(jsonPath("$.[*].netTotal").value(hasItem(sameNumber(DEFAULT_NET_TOTAL))))
            .andExpect(jsonPath("$.[*].itemsDiscount").value(hasItem(sameNumber(DEFAULT_ITEMS_DISCOUNT))))
            .andExpect(jsonPath("$.[*].internalCode").value(hasItem(DEFAULT_INTERNAL_CODE)));
    }

    @Test
    @Transactional
    void getInvoiceLine() throws Exception {
        // Initialize the database
        invoiceLineRepository.saveAndFlush(invoiceLine);

        // Get the invoiceLine
        restInvoiceLineMockMvc
            .perform(get(ENTITY_API_URL_ID, invoiceLine.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(invoiceLine.getId().intValue()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.itemType").value(DEFAULT_ITEM_TYPE))
            .andExpect(jsonPath("$.itemCode").value(DEFAULT_ITEM_CODE))
            .andExpect(jsonPath("$.unitType").value(DEFAULT_UNIT_TYPE))
            .andExpect(jsonPath("$.quantity").value(sameNumber(DEFAULT_QUANTITY)))
            .andExpect(jsonPath("$.salesTotal").value(sameNumber(DEFAULT_SALES_TOTAL)))
            .andExpect(jsonPath("$.total").value(sameNumber(DEFAULT_TOTAL)))
            .andExpect(jsonPath("$.valueDifference").value(sameNumber(DEFAULT_VALUE_DIFFERENCE)))
            .andExpect(jsonPath("$.totalTaxableFees").value(sameNumber(DEFAULT_TOTAL_TAXABLE_FEES)))
            .andExpect(jsonPath("$.netTotal").value(sameNumber(DEFAULT_NET_TOTAL)))
            .andExpect(jsonPath("$.itemsDiscount").value(sameNumber(DEFAULT_ITEMS_DISCOUNT)))
            .andExpect(jsonPath("$.internalCode").value(DEFAULT_INTERNAL_CODE));
    }

    @Test
    @Transactional
    void getNonExistingInvoiceLine() throws Exception {
        // Get the invoiceLine
        restInvoiceLineMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewInvoiceLine() throws Exception {
        // Initialize the database
        invoiceLineRepository.saveAndFlush(invoiceLine);

        int databaseSizeBeforeUpdate = invoiceLineRepository.findAll().size();

        // Update the invoiceLine
        InvoiceLine updatedInvoiceLine = invoiceLineRepository.findById(invoiceLine.getId()).get();
        // Disconnect from session so that the updates on updatedInvoiceLine are not directly saved in db
        em.detach(updatedInvoiceLine);
        updatedInvoiceLine
            .description(UPDATED_DESCRIPTION)
            .itemType(UPDATED_ITEM_TYPE)
            .itemCode(UPDATED_ITEM_CODE)
            .unitType(UPDATED_UNIT_TYPE)
            .quantity(UPDATED_QUANTITY)
            .salesTotal(UPDATED_SALES_TOTAL)
            .total(UPDATED_TOTAL)
            .valueDifference(UPDATED_VALUE_DIFFERENCE)
            .totalTaxableFees(UPDATED_TOTAL_TAXABLE_FEES)
            .netTotal(UPDATED_NET_TOTAL)
            .itemsDiscount(UPDATED_ITEMS_DISCOUNT)
            .internalCode(UPDATED_INTERNAL_CODE);

        restInvoiceLineMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedInvoiceLine.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedInvoiceLine))
            )
            .andExpect(status().isOk());

        // Validate the InvoiceLine in the database
        List<InvoiceLine> invoiceLineList = invoiceLineRepository.findAll();
        assertThat(invoiceLineList).hasSize(databaseSizeBeforeUpdate);
        InvoiceLine testInvoiceLine = invoiceLineList.get(invoiceLineList.size() - 1);
        assertThat(testInvoiceLine.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testInvoiceLine.getItemType()).isEqualTo(UPDATED_ITEM_TYPE);
        assertThat(testInvoiceLine.getItemCode()).isEqualTo(UPDATED_ITEM_CODE);
        assertThat(testInvoiceLine.getUnitType()).isEqualTo(UPDATED_UNIT_TYPE);
        assertThat(testInvoiceLine.getQuantity()).isEqualByComparingTo(UPDATED_QUANTITY);
        assertThat(testInvoiceLine.getSalesTotal()).isEqualByComparingTo(UPDATED_SALES_TOTAL);
        assertThat(testInvoiceLine.getTotal()).isEqualByComparingTo(UPDATED_TOTAL);
        assertThat(testInvoiceLine.getValueDifference()).isEqualByComparingTo(UPDATED_VALUE_DIFFERENCE);
        assertThat(testInvoiceLine.getTotalTaxableFees()).isEqualByComparingTo(UPDATED_TOTAL_TAXABLE_FEES);
        assertThat(testInvoiceLine.getNetTotal()).isEqualByComparingTo(UPDATED_NET_TOTAL);
        assertThat(testInvoiceLine.getItemsDiscount()).isEqualByComparingTo(UPDATED_ITEMS_DISCOUNT);
        assertThat(testInvoiceLine.getInternalCode()).isEqualTo(UPDATED_INTERNAL_CODE);
    }

    @Test
    @Transactional
    void putNonExistingInvoiceLine() throws Exception {
        int databaseSizeBeforeUpdate = invoiceLineRepository.findAll().size();
        invoiceLine.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restInvoiceLineMockMvc
            .perform(
                put(ENTITY_API_URL_ID, invoiceLine.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(invoiceLine))
            )
            .andExpect(status().isBadRequest());

        // Validate the InvoiceLine in the database
        List<InvoiceLine> invoiceLineList = invoiceLineRepository.findAll();
        assertThat(invoiceLineList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchInvoiceLine() throws Exception {
        int databaseSizeBeforeUpdate = invoiceLineRepository.findAll().size();
        invoiceLine.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restInvoiceLineMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(invoiceLine))
            )
            .andExpect(status().isBadRequest());

        // Validate the InvoiceLine in the database
        List<InvoiceLine> invoiceLineList = invoiceLineRepository.findAll();
        assertThat(invoiceLineList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamInvoiceLine() throws Exception {
        int databaseSizeBeforeUpdate = invoiceLineRepository.findAll().size();
        invoiceLine.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restInvoiceLineMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(invoiceLine)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the InvoiceLine in the database
        List<InvoiceLine> invoiceLineList = invoiceLineRepository.findAll();
        assertThat(invoiceLineList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateInvoiceLineWithPatch() throws Exception {
        // Initialize the database
        invoiceLineRepository.saveAndFlush(invoiceLine);

        int databaseSizeBeforeUpdate = invoiceLineRepository.findAll().size();

        // Update the invoiceLine using partial update
        InvoiceLine partialUpdatedInvoiceLine = new InvoiceLine();
        partialUpdatedInvoiceLine.setId(invoiceLine.getId());

        partialUpdatedInvoiceLine
            .description(UPDATED_DESCRIPTION)
            .itemType(UPDATED_ITEM_TYPE)
            .unitType(UPDATED_UNIT_TYPE)
            .quantity(UPDATED_QUANTITY)
            .salesTotal(UPDATED_SALES_TOTAL)
            .itemsDiscount(UPDATED_ITEMS_DISCOUNT);

        restInvoiceLineMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedInvoiceLine.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedInvoiceLine))
            )
            .andExpect(status().isOk());

        // Validate the InvoiceLine in the database
        List<InvoiceLine> invoiceLineList = invoiceLineRepository.findAll();
        assertThat(invoiceLineList).hasSize(databaseSizeBeforeUpdate);
        InvoiceLine testInvoiceLine = invoiceLineList.get(invoiceLineList.size() - 1);
        assertThat(testInvoiceLine.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testInvoiceLine.getItemType()).isEqualTo(UPDATED_ITEM_TYPE);
        assertThat(testInvoiceLine.getItemCode()).isEqualTo(DEFAULT_ITEM_CODE);
        assertThat(testInvoiceLine.getUnitType()).isEqualTo(UPDATED_UNIT_TYPE);
        assertThat(testInvoiceLine.getQuantity()).isEqualByComparingTo(UPDATED_QUANTITY);
        assertThat(testInvoiceLine.getSalesTotal()).isEqualByComparingTo(UPDATED_SALES_TOTAL);
        assertThat(testInvoiceLine.getTotal()).isEqualByComparingTo(DEFAULT_TOTAL);
        assertThat(testInvoiceLine.getValueDifference()).isEqualByComparingTo(DEFAULT_VALUE_DIFFERENCE);
        assertThat(testInvoiceLine.getTotalTaxableFees()).isEqualByComparingTo(DEFAULT_TOTAL_TAXABLE_FEES);
        assertThat(testInvoiceLine.getNetTotal()).isEqualByComparingTo(DEFAULT_NET_TOTAL);
        assertThat(testInvoiceLine.getItemsDiscount()).isEqualByComparingTo(UPDATED_ITEMS_DISCOUNT);
        assertThat(testInvoiceLine.getInternalCode()).isEqualTo(DEFAULT_INTERNAL_CODE);
    }

    @Test
    @Transactional
    void fullUpdateInvoiceLineWithPatch() throws Exception {
        // Initialize the database
        invoiceLineRepository.saveAndFlush(invoiceLine);

        int databaseSizeBeforeUpdate = invoiceLineRepository.findAll().size();

        // Update the invoiceLine using partial update
        InvoiceLine partialUpdatedInvoiceLine = new InvoiceLine();
        partialUpdatedInvoiceLine.setId(invoiceLine.getId());

        partialUpdatedInvoiceLine
            .description(UPDATED_DESCRIPTION)
            .itemType(UPDATED_ITEM_TYPE)
            .itemCode(UPDATED_ITEM_CODE)
            .unitType(UPDATED_UNIT_TYPE)
            .quantity(UPDATED_QUANTITY)
            .salesTotal(UPDATED_SALES_TOTAL)
            .total(UPDATED_TOTAL)
            .valueDifference(UPDATED_VALUE_DIFFERENCE)
            .totalTaxableFees(UPDATED_TOTAL_TAXABLE_FEES)
            .netTotal(UPDATED_NET_TOTAL)
            .itemsDiscount(UPDATED_ITEMS_DISCOUNT)
            .internalCode(UPDATED_INTERNAL_CODE);

        restInvoiceLineMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedInvoiceLine.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedInvoiceLine))
            )
            .andExpect(status().isOk());

        // Validate the InvoiceLine in the database
        List<InvoiceLine> invoiceLineList = invoiceLineRepository.findAll();
        assertThat(invoiceLineList).hasSize(databaseSizeBeforeUpdate);
        InvoiceLine testInvoiceLine = invoiceLineList.get(invoiceLineList.size() - 1);
        assertThat(testInvoiceLine.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testInvoiceLine.getItemType()).isEqualTo(UPDATED_ITEM_TYPE);
        assertThat(testInvoiceLine.getItemCode()).isEqualTo(UPDATED_ITEM_CODE);
        assertThat(testInvoiceLine.getUnitType()).isEqualTo(UPDATED_UNIT_TYPE);
        assertThat(testInvoiceLine.getQuantity()).isEqualByComparingTo(UPDATED_QUANTITY);
        assertThat(testInvoiceLine.getSalesTotal()).isEqualByComparingTo(UPDATED_SALES_TOTAL);
        assertThat(testInvoiceLine.getTotal()).isEqualByComparingTo(UPDATED_TOTAL);
        assertThat(testInvoiceLine.getValueDifference()).isEqualByComparingTo(UPDATED_VALUE_DIFFERENCE);
        assertThat(testInvoiceLine.getTotalTaxableFees()).isEqualByComparingTo(UPDATED_TOTAL_TAXABLE_FEES);
        assertThat(testInvoiceLine.getNetTotal()).isEqualByComparingTo(UPDATED_NET_TOTAL);
        assertThat(testInvoiceLine.getItemsDiscount()).isEqualByComparingTo(UPDATED_ITEMS_DISCOUNT);
        assertThat(testInvoiceLine.getInternalCode()).isEqualTo(UPDATED_INTERNAL_CODE);
    }

    @Test
    @Transactional
    void patchNonExistingInvoiceLine() throws Exception {
        int databaseSizeBeforeUpdate = invoiceLineRepository.findAll().size();
        invoiceLine.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restInvoiceLineMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, invoiceLine.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(invoiceLine))
            )
            .andExpect(status().isBadRequest());

        // Validate the InvoiceLine in the database
        List<InvoiceLine> invoiceLineList = invoiceLineRepository.findAll();
        assertThat(invoiceLineList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchInvoiceLine() throws Exception {
        int databaseSizeBeforeUpdate = invoiceLineRepository.findAll().size();
        invoiceLine.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restInvoiceLineMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(invoiceLine))
            )
            .andExpect(status().isBadRequest());

        // Validate the InvoiceLine in the database
        List<InvoiceLine> invoiceLineList = invoiceLineRepository.findAll();
        assertThat(invoiceLineList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamInvoiceLine() throws Exception {
        int databaseSizeBeforeUpdate = invoiceLineRepository.findAll().size();
        invoiceLine.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restInvoiceLineMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(invoiceLine))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the InvoiceLine in the database
        List<InvoiceLine> invoiceLineList = invoiceLineRepository.findAll();
        assertThat(invoiceLineList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteInvoiceLine() throws Exception {
        // Initialize the database
        invoiceLineRepository.saveAndFlush(invoiceLine);

        int databaseSizeBeforeDelete = invoiceLineRepository.findAll().size();

        // Delete the invoiceLine
        restInvoiceLineMockMvc
            .perform(delete(ENTITY_API_URL_ID, invoiceLine.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<InvoiceLine> invoiceLineList = invoiceLineRepository.findAll();
        assertThat(invoiceLineList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
