package eg.inv.web.rest;

import static eg.inv.web.rest.TestUtil.sameNumber;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import eg.inv.IntegrationTest;
import eg.inv.domain.Document;
import eg.inv.repository.DocumentRepository;
import java.math.BigDecimal;
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
 * Integration tests for the {@link DocumentResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class DocumentResourceIT {

    private static final String DEFAULT_DOCUMENT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_DOCUMENT_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_DOCUMENT_TYPE_VERSION = "AAAAAAAAAA";
    private static final String UPDATED_DOCUMENT_TYPE_VERSION = "BBBBBBBBBB";

    private static final Instant DEFAULT_DATE_TIME_ISSUED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATE_TIME_ISSUED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_TAXPAYER_ACTIVITY_CODE = "AAAAAAAAAA";
    private static final String UPDATED_TAXPAYER_ACTIVITY_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_INTERNAL_ID = "AAAAAAAAAA";
    private static final String UPDATED_INTERNAL_ID = "BBBBBBBBBB";

    private static final String DEFAULT_PURCHASE_ORDER_REFERENCE = "AAAAAAAAAA";
    private static final String UPDATED_PURCHASE_ORDER_REFERENCE = "BBBBBBBBBB";

    private static final String DEFAULT_PURCHASE_ORDER_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_PURCHASE_ORDER_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_SALES_ORDER_REFERENCE = "AAAAAAAAAA";
    private static final String UPDATED_SALES_ORDER_REFERENCE = "BBBBBBBBBB";

    private static final String DEFAULT_SALES_ORDER_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_SALES_ORDER_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_PROFORMA_INVOICE_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_PROFORMA_INVOICE_NUMBER = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_TOTAL_SALES_AMOUNT = new BigDecimal(1);
    private static final BigDecimal UPDATED_TOTAL_SALES_AMOUNT = new BigDecimal(2);

    private static final BigDecimal DEFAULT_TOTAL_DISCOUNT_AMOUNT = new BigDecimal(1);
    private static final BigDecimal UPDATED_TOTAL_DISCOUNT_AMOUNT = new BigDecimal(2);

    private static final BigDecimal DEFAULT_NET_AMOUNT = new BigDecimal(1);
    private static final BigDecimal UPDATED_NET_AMOUNT = new BigDecimal(2);

    private static final BigDecimal DEFAULT_EXTRA_DISCOUNT_AMOUNT = new BigDecimal(1);
    private static final BigDecimal UPDATED_EXTRA_DISCOUNT_AMOUNT = new BigDecimal(2);

    private static final BigDecimal DEFAULT_TOTAL_ITEMS_DISCOUNT_AMOUNT = new BigDecimal(1);
    private static final BigDecimal UPDATED_TOTAL_ITEMS_DISCOUNT_AMOUNT = new BigDecimal(2);

    private static final BigDecimal DEFAULT_TOTAL_AMOUNT = new BigDecimal(1);
    private static final BigDecimal UPDATED_TOTAL_AMOUNT = new BigDecimal(2);

    private static final String ENTITY_API_URL = "/api/documents";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private DocumentRepository documentRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDocumentMockMvc;

    private Document document;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Document createEntity(EntityManager em) {
        Document document = new Document()
            .documentType(DEFAULT_DOCUMENT_TYPE)
            .documentTypeVersion(DEFAULT_DOCUMENT_TYPE_VERSION)
            .dateTimeIssued(DEFAULT_DATE_TIME_ISSUED)
            .taxpayerActivityCode(DEFAULT_TAXPAYER_ACTIVITY_CODE)
            .internalId(DEFAULT_INTERNAL_ID)
            .purchaseOrderReference(DEFAULT_PURCHASE_ORDER_REFERENCE)
            .purchaseOrderDescription(DEFAULT_PURCHASE_ORDER_DESCRIPTION)
            .salesOrderReference(DEFAULT_SALES_ORDER_REFERENCE)
            .salesOrderDescription(DEFAULT_SALES_ORDER_DESCRIPTION)
            .proformaInvoiceNumber(DEFAULT_PROFORMA_INVOICE_NUMBER)
            .totalSalesAmount(DEFAULT_TOTAL_SALES_AMOUNT)
            .totalDiscountAmount(DEFAULT_TOTAL_DISCOUNT_AMOUNT)
            .netAmount(DEFAULT_NET_AMOUNT)
            .extraDiscountAmount(DEFAULT_EXTRA_DISCOUNT_AMOUNT)
            .totalItemsDiscountAmount(DEFAULT_TOTAL_ITEMS_DISCOUNT_AMOUNT)
            .totalAmount(DEFAULT_TOTAL_AMOUNT);
        return document;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Document createUpdatedEntity(EntityManager em) {
        Document document = new Document()
            .documentType(UPDATED_DOCUMENT_TYPE)
            .documentTypeVersion(UPDATED_DOCUMENT_TYPE_VERSION)
            .dateTimeIssued(UPDATED_DATE_TIME_ISSUED)
            .taxpayerActivityCode(UPDATED_TAXPAYER_ACTIVITY_CODE)
            .internalId(UPDATED_INTERNAL_ID)
            .purchaseOrderReference(UPDATED_PURCHASE_ORDER_REFERENCE)
            .purchaseOrderDescription(UPDATED_PURCHASE_ORDER_DESCRIPTION)
            .salesOrderReference(UPDATED_SALES_ORDER_REFERENCE)
            .salesOrderDescription(UPDATED_SALES_ORDER_DESCRIPTION)
            .proformaInvoiceNumber(UPDATED_PROFORMA_INVOICE_NUMBER)
            .totalSalesAmount(UPDATED_TOTAL_SALES_AMOUNT)
            .totalDiscountAmount(UPDATED_TOTAL_DISCOUNT_AMOUNT)
            .netAmount(UPDATED_NET_AMOUNT)
            .extraDiscountAmount(UPDATED_EXTRA_DISCOUNT_AMOUNT)
            .totalItemsDiscountAmount(UPDATED_TOTAL_ITEMS_DISCOUNT_AMOUNT)
            .totalAmount(UPDATED_TOTAL_AMOUNT);
        return document;
    }

    @BeforeEach
    public void initTest() {
        document = createEntity(em);
    }

    @Test
    @Transactional
    void createDocument() throws Exception {
        int databaseSizeBeforeCreate = documentRepository.findAll().size();
        // Create the Document
        restDocumentMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(document)))
            .andExpect(status().isCreated());

        // Validate the Document in the database
        List<Document> documentList = documentRepository.findAll();
        assertThat(documentList).hasSize(databaseSizeBeforeCreate + 1);
        Document testDocument = documentList.get(documentList.size() - 1);
        assertThat(testDocument.getDocumentType()).isEqualTo(DEFAULT_DOCUMENT_TYPE);
        assertThat(testDocument.getDocumentTypeVersion()).isEqualTo(DEFAULT_DOCUMENT_TYPE_VERSION);
        assertThat(testDocument.getDateTimeIssued()).isEqualTo(DEFAULT_DATE_TIME_ISSUED);
        assertThat(testDocument.getTaxpayerActivityCode()).isEqualTo(DEFAULT_TAXPAYER_ACTIVITY_CODE);
        assertThat(testDocument.getInternalId()).isEqualTo(DEFAULT_INTERNAL_ID);
        assertThat(testDocument.getPurchaseOrderReference()).isEqualTo(DEFAULT_PURCHASE_ORDER_REFERENCE);
        assertThat(testDocument.getPurchaseOrderDescription()).isEqualTo(DEFAULT_PURCHASE_ORDER_DESCRIPTION);
        assertThat(testDocument.getSalesOrderReference()).isEqualTo(DEFAULT_SALES_ORDER_REFERENCE);
        assertThat(testDocument.getSalesOrderDescription()).isEqualTo(DEFAULT_SALES_ORDER_DESCRIPTION);
        assertThat(testDocument.getProformaInvoiceNumber()).isEqualTo(DEFAULT_PROFORMA_INVOICE_NUMBER);
        assertThat(testDocument.getTotalSalesAmount()).isEqualByComparingTo(DEFAULT_TOTAL_SALES_AMOUNT);
        assertThat(testDocument.getTotalDiscountAmount()).isEqualByComparingTo(DEFAULT_TOTAL_DISCOUNT_AMOUNT);
        assertThat(testDocument.getNetAmount()).isEqualByComparingTo(DEFAULT_NET_AMOUNT);
        assertThat(testDocument.getExtraDiscountAmount()).isEqualByComparingTo(DEFAULT_EXTRA_DISCOUNT_AMOUNT);
        assertThat(testDocument.getTotalItemsDiscountAmount()).isEqualByComparingTo(DEFAULT_TOTAL_ITEMS_DISCOUNT_AMOUNT);
        assertThat(testDocument.getTotalAmount()).isEqualByComparingTo(DEFAULT_TOTAL_AMOUNT);
    }

    @Test
    @Transactional
    void createDocumentWithExistingId() throws Exception {
        // Create the Document with an existing ID
        document.setId(1L);

        int databaseSizeBeforeCreate = documentRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restDocumentMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(document)))
            .andExpect(status().isBadRequest());

        // Validate the Document in the database
        List<Document> documentList = documentRepository.findAll();
        assertThat(documentList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllDocuments() throws Exception {
        // Initialize the database
        documentRepository.saveAndFlush(document);

        // Get all the documentList
        restDocumentMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(document.getId().intValue())))
            .andExpect(jsonPath("$.[*].documentType").value(hasItem(DEFAULT_DOCUMENT_TYPE)))
            .andExpect(jsonPath("$.[*].documentTypeVersion").value(hasItem(DEFAULT_DOCUMENT_TYPE_VERSION)))
            .andExpect(jsonPath("$.[*].dateTimeIssued").value(hasItem(DEFAULT_DATE_TIME_ISSUED.toString())))
            .andExpect(jsonPath("$.[*].taxpayerActivityCode").value(hasItem(DEFAULT_TAXPAYER_ACTIVITY_CODE)))
            .andExpect(jsonPath("$.[*].internalId").value(hasItem(DEFAULT_INTERNAL_ID)))
            .andExpect(jsonPath("$.[*].purchaseOrderReference").value(hasItem(DEFAULT_PURCHASE_ORDER_REFERENCE)))
            .andExpect(jsonPath("$.[*].purchaseOrderDescription").value(hasItem(DEFAULT_PURCHASE_ORDER_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].salesOrderReference").value(hasItem(DEFAULT_SALES_ORDER_REFERENCE)))
            .andExpect(jsonPath("$.[*].salesOrderDescription").value(hasItem(DEFAULT_SALES_ORDER_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].proformaInvoiceNumber").value(hasItem(DEFAULT_PROFORMA_INVOICE_NUMBER)))
            .andExpect(jsonPath("$.[*].totalSalesAmount").value(hasItem(sameNumber(DEFAULT_TOTAL_SALES_AMOUNT))))
            .andExpect(jsonPath("$.[*].totalDiscountAmount").value(hasItem(sameNumber(DEFAULT_TOTAL_DISCOUNT_AMOUNT))))
            .andExpect(jsonPath("$.[*].netAmount").value(hasItem(sameNumber(DEFAULT_NET_AMOUNT))))
            .andExpect(jsonPath("$.[*].extraDiscountAmount").value(hasItem(sameNumber(DEFAULT_EXTRA_DISCOUNT_AMOUNT))))
            .andExpect(jsonPath("$.[*].totalItemsDiscountAmount").value(hasItem(sameNumber(DEFAULT_TOTAL_ITEMS_DISCOUNT_AMOUNT))))
            .andExpect(jsonPath("$.[*].totalAmount").value(hasItem(sameNumber(DEFAULT_TOTAL_AMOUNT))));
    }

    @Test
    @Transactional
    void getDocument() throws Exception {
        // Initialize the database
        documentRepository.saveAndFlush(document);

        // Get the document
        restDocumentMockMvc
            .perform(get(ENTITY_API_URL_ID, document.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(document.getId().intValue()))
            .andExpect(jsonPath("$.documentType").value(DEFAULT_DOCUMENT_TYPE))
            .andExpect(jsonPath("$.documentTypeVersion").value(DEFAULT_DOCUMENT_TYPE_VERSION))
            .andExpect(jsonPath("$.dateTimeIssued").value(DEFAULT_DATE_TIME_ISSUED.toString()))
            .andExpect(jsonPath("$.taxpayerActivityCode").value(DEFAULT_TAXPAYER_ACTIVITY_CODE))
            .andExpect(jsonPath("$.internalId").value(DEFAULT_INTERNAL_ID))
            .andExpect(jsonPath("$.purchaseOrderReference").value(DEFAULT_PURCHASE_ORDER_REFERENCE))
            .andExpect(jsonPath("$.purchaseOrderDescription").value(DEFAULT_PURCHASE_ORDER_DESCRIPTION))
            .andExpect(jsonPath("$.salesOrderReference").value(DEFAULT_SALES_ORDER_REFERENCE))
            .andExpect(jsonPath("$.salesOrderDescription").value(DEFAULT_SALES_ORDER_DESCRIPTION))
            .andExpect(jsonPath("$.proformaInvoiceNumber").value(DEFAULT_PROFORMA_INVOICE_NUMBER))
            .andExpect(jsonPath("$.totalSalesAmount").value(sameNumber(DEFAULT_TOTAL_SALES_AMOUNT)))
            .andExpect(jsonPath("$.totalDiscountAmount").value(sameNumber(DEFAULT_TOTAL_DISCOUNT_AMOUNT)))
            .andExpect(jsonPath("$.netAmount").value(sameNumber(DEFAULT_NET_AMOUNT)))
            .andExpect(jsonPath("$.extraDiscountAmount").value(sameNumber(DEFAULT_EXTRA_DISCOUNT_AMOUNT)))
            .andExpect(jsonPath("$.totalItemsDiscountAmount").value(sameNumber(DEFAULT_TOTAL_ITEMS_DISCOUNT_AMOUNT)))
            .andExpect(jsonPath("$.totalAmount").value(sameNumber(DEFAULT_TOTAL_AMOUNT)));
    }

    @Test
    @Transactional
    void getNonExistingDocument() throws Exception {
        // Get the document
        restDocumentMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewDocument() throws Exception {
        // Initialize the database
        documentRepository.saveAndFlush(document);

        int databaseSizeBeforeUpdate = documentRepository.findAll().size();

        // Update the document
        Document updatedDocument = documentRepository.findById(document.getId()).get();
        // Disconnect from session so that the updates on updatedDocument are not directly saved in db
        em.detach(updatedDocument);
        updatedDocument
            .documentType(UPDATED_DOCUMENT_TYPE)
            .documentTypeVersion(UPDATED_DOCUMENT_TYPE_VERSION)
            .dateTimeIssued(UPDATED_DATE_TIME_ISSUED)
            .taxpayerActivityCode(UPDATED_TAXPAYER_ACTIVITY_CODE)
            .internalId(UPDATED_INTERNAL_ID)
            .purchaseOrderReference(UPDATED_PURCHASE_ORDER_REFERENCE)
            .purchaseOrderDescription(UPDATED_PURCHASE_ORDER_DESCRIPTION)
            .salesOrderReference(UPDATED_SALES_ORDER_REFERENCE)
            .salesOrderDescription(UPDATED_SALES_ORDER_DESCRIPTION)
            .proformaInvoiceNumber(UPDATED_PROFORMA_INVOICE_NUMBER)
            .totalSalesAmount(UPDATED_TOTAL_SALES_AMOUNT)
            .totalDiscountAmount(UPDATED_TOTAL_DISCOUNT_AMOUNT)
            .netAmount(UPDATED_NET_AMOUNT)
            .extraDiscountAmount(UPDATED_EXTRA_DISCOUNT_AMOUNT)
            .totalItemsDiscountAmount(UPDATED_TOTAL_ITEMS_DISCOUNT_AMOUNT)
            .totalAmount(UPDATED_TOTAL_AMOUNT);

        restDocumentMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedDocument.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedDocument))
            )
            .andExpect(status().isOk());

        // Validate the Document in the database
        List<Document> documentList = documentRepository.findAll();
        assertThat(documentList).hasSize(databaseSizeBeforeUpdate);
        Document testDocument = documentList.get(documentList.size() - 1);
        assertThat(testDocument.getDocumentType()).isEqualTo(UPDATED_DOCUMENT_TYPE);
        assertThat(testDocument.getDocumentTypeVersion()).isEqualTo(UPDATED_DOCUMENT_TYPE_VERSION);
        assertThat(testDocument.getDateTimeIssued()).isEqualTo(UPDATED_DATE_TIME_ISSUED);
        assertThat(testDocument.getTaxpayerActivityCode()).isEqualTo(UPDATED_TAXPAYER_ACTIVITY_CODE);
        assertThat(testDocument.getInternalId()).isEqualTo(UPDATED_INTERNAL_ID);
        assertThat(testDocument.getPurchaseOrderReference()).isEqualTo(UPDATED_PURCHASE_ORDER_REFERENCE);
        assertThat(testDocument.getPurchaseOrderDescription()).isEqualTo(UPDATED_PURCHASE_ORDER_DESCRIPTION);
        assertThat(testDocument.getSalesOrderReference()).isEqualTo(UPDATED_SALES_ORDER_REFERENCE);
        assertThat(testDocument.getSalesOrderDescription()).isEqualTo(UPDATED_SALES_ORDER_DESCRIPTION);
        assertThat(testDocument.getProformaInvoiceNumber()).isEqualTo(UPDATED_PROFORMA_INVOICE_NUMBER);
        assertThat(testDocument.getTotalSalesAmount()).isEqualByComparingTo(UPDATED_TOTAL_SALES_AMOUNT);
        assertThat(testDocument.getTotalDiscountAmount()).isEqualByComparingTo(UPDATED_TOTAL_DISCOUNT_AMOUNT);
        assertThat(testDocument.getNetAmount()).isEqualByComparingTo(UPDATED_NET_AMOUNT);
        assertThat(testDocument.getExtraDiscountAmount()).isEqualByComparingTo(UPDATED_EXTRA_DISCOUNT_AMOUNT);
        assertThat(testDocument.getTotalItemsDiscountAmount()).isEqualByComparingTo(UPDATED_TOTAL_ITEMS_DISCOUNT_AMOUNT);
        assertThat(testDocument.getTotalAmount()).isEqualByComparingTo(UPDATED_TOTAL_AMOUNT);
    }

    @Test
    @Transactional
    void putNonExistingDocument() throws Exception {
        int databaseSizeBeforeUpdate = documentRepository.findAll().size();
        document.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDocumentMockMvc
            .perform(
                put(ENTITY_API_URL_ID, document.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(document))
            )
            .andExpect(status().isBadRequest());

        // Validate the Document in the database
        List<Document> documentList = documentRepository.findAll();
        assertThat(documentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchDocument() throws Exception {
        int databaseSizeBeforeUpdate = documentRepository.findAll().size();
        document.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDocumentMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(document))
            )
            .andExpect(status().isBadRequest());

        // Validate the Document in the database
        List<Document> documentList = documentRepository.findAll();
        assertThat(documentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamDocument() throws Exception {
        int databaseSizeBeforeUpdate = documentRepository.findAll().size();
        document.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDocumentMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(document)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Document in the database
        List<Document> documentList = documentRepository.findAll();
        assertThat(documentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateDocumentWithPatch() throws Exception {
        // Initialize the database
        documentRepository.saveAndFlush(document);

        int databaseSizeBeforeUpdate = documentRepository.findAll().size();

        // Update the document using partial update
        Document partialUpdatedDocument = new Document();
        partialUpdatedDocument.setId(document.getId());

        partialUpdatedDocument
            .documentType(UPDATED_DOCUMENT_TYPE)
            .documentTypeVersion(UPDATED_DOCUMENT_TYPE_VERSION)
            .salesOrderReference(UPDATED_SALES_ORDER_REFERENCE)
            .proformaInvoiceNumber(UPDATED_PROFORMA_INVOICE_NUMBER)
            .totalDiscountAmount(UPDATED_TOTAL_DISCOUNT_AMOUNT)
            .netAmount(UPDATED_NET_AMOUNT)
            .totalAmount(UPDATED_TOTAL_AMOUNT);

        restDocumentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDocument.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDocument))
            )
            .andExpect(status().isOk());

        // Validate the Document in the database
        List<Document> documentList = documentRepository.findAll();
        assertThat(documentList).hasSize(databaseSizeBeforeUpdate);
        Document testDocument = documentList.get(documentList.size() - 1);
        assertThat(testDocument.getDocumentType()).isEqualTo(UPDATED_DOCUMENT_TYPE);
        assertThat(testDocument.getDocumentTypeVersion()).isEqualTo(UPDATED_DOCUMENT_TYPE_VERSION);
        assertThat(testDocument.getDateTimeIssued()).isEqualTo(DEFAULT_DATE_TIME_ISSUED);
        assertThat(testDocument.getTaxpayerActivityCode()).isEqualTo(DEFAULT_TAXPAYER_ACTIVITY_CODE);
        assertThat(testDocument.getInternalId()).isEqualTo(DEFAULT_INTERNAL_ID);
        assertThat(testDocument.getPurchaseOrderReference()).isEqualTo(DEFAULT_PURCHASE_ORDER_REFERENCE);
        assertThat(testDocument.getPurchaseOrderDescription()).isEqualTo(DEFAULT_PURCHASE_ORDER_DESCRIPTION);
        assertThat(testDocument.getSalesOrderReference()).isEqualTo(UPDATED_SALES_ORDER_REFERENCE);
        assertThat(testDocument.getSalesOrderDescription()).isEqualTo(DEFAULT_SALES_ORDER_DESCRIPTION);
        assertThat(testDocument.getProformaInvoiceNumber()).isEqualTo(UPDATED_PROFORMA_INVOICE_NUMBER);
        assertThat(testDocument.getTotalSalesAmount()).isEqualByComparingTo(DEFAULT_TOTAL_SALES_AMOUNT);
        assertThat(testDocument.getTotalDiscountAmount()).isEqualByComparingTo(UPDATED_TOTAL_DISCOUNT_AMOUNT);
        assertThat(testDocument.getNetAmount()).isEqualByComparingTo(UPDATED_NET_AMOUNT);
        assertThat(testDocument.getExtraDiscountAmount()).isEqualByComparingTo(DEFAULT_EXTRA_DISCOUNT_AMOUNT);
        assertThat(testDocument.getTotalItemsDiscountAmount()).isEqualByComparingTo(DEFAULT_TOTAL_ITEMS_DISCOUNT_AMOUNT);
        assertThat(testDocument.getTotalAmount()).isEqualByComparingTo(UPDATED_TOTAL_AMOUNT);
    }

    @Test
    @Transactional
    void fullUpdateDocumentWithPatch() throws Exception {
        // Initialize the database
        documentRepository.saveAndFlush(document);

        int databaseSizeBeforeUpdate = documentRepository.findAll().size();

        // Update the document using partial update
        Document partialUpdatedDocument = new Document();
        partialUpdatedDocument.setId(document.getId());

        partialUpdatedDocument
            .documentType(UPDATED_DOCUMENT_TYPE)
            .documentTypeVersion(UPDATED_DOCUMENT_TYPE_VERSION)
            .dateTimeIssued(UPDATED_DATE_TIME_ISSUED)
            .taxpayerActivityCode(UPDATED_TAXPAYER_ACTIVITY_CODE)
            .internalId(UPDATED_INTERNAL_ID)
            .purchaseOrderReference(UPDATED_PURCHASE_ORDER_REFERENCE)
            .purchaseOrderDescription(UPDATED_PURCHASE_ORDER_DESCRIPTION)
            .salesOrderReference(UPDATED_SALES_ORDER_REFERENCE)
            .salesOrderDescription(UPDATED_SALES_ORDER_DESCRIPTION)
            .proformaInvoiceNumber(UPDATED_PROFORMA_INVOICE_NUMBER)
            .totalSalesAmount(UPDATED_TOTAL_SALES_AMOUNT)
            .totalDiscountAmount(UPDATED_TOTAL_DISCOUNT_AMOUNT)
            .netAmount(UPDATED_NET_AMOUNT)
            .extraDiscountAmount(UPDATED_EXTRA_DISCOUNT_AMOUNT)
            .totalItemsDiscountAmount(UPDATED_TOTAL_ITEMS_DISCOUNT_AMOUNT)
            .totalAmount(UPDATED_TOTAL_AMOUNT);

        restDocumentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDocument.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDocument))
            )
            .andExpect(status().isOk());

        // Validate the Document in the database
        List<Document> documentList = documentRepository.findAll();
        assertThat(documentList).hasSize(databaseSizeBeforeUpdate);
        Document testDocument = documentList.get(documentList.size() - 1);
        assertThat(testDocument.getDocumentType()).isEqualTo(UPDATED_DOCUMENT_TYPE);
        assertThat(testDocument.getDocumentTypeVersion()).isEqualTo(UPDATED_DOCUMENT_TYPE_VERSION);
        assertThat(testDocument.getDateTimeIssued()).isEqualTo(UPDATED_DATE_TIME_ISSUED);
        assertThat(testDocument.getTaxpayerActivityCode()).isEqualTo(UPDATED_TAXPAYER_ACTIVITY_CODE);
        assertThat(testDocument.getInternalId()).isEqualTo(UPDATED_INTERNAL_ID);
        assertThat(testDocument.getPurchaseOrderReference()).isEqualTo(UPDATED_PURCHASE_ORDER_REFERENCE);
        assertThat(testDocument.getPurchaseOrderDescription()).isEqualTo(UPDATED_PURCHASE_ORDER_DESCRIPTION);
        assertThat(testDocument.getSalesOrderReference()).isEqualTo(UPDATED_SALES_ORDER_REFERENCE);
        assertThat(testDocument.getSalesOrderDescription()).isEqualTo(UPDATED_SALES_ORDER_DESCRIPTION);
        assertThat(testDocument.getProformaInvoiceNumber()).isEqualTo(UPDATED_PROFORMA_INVOICE_NUMBER);
        assertThat(testDocument.getTotalSalesAmount()).isEqualByComparingTo(UPDATED_TOTAL_SALES_AMOUNT);
        assertThat(testDocument.getTotalDiscountAmount()).isEqualByComparingTo(UPDATED_TOTAL_DISCOUNT_AMOUNT);
        assertThat(testDocument.getNetAmount()).isEqualByComparingTo(UPDATED_NET_AMOUNT);
        assertThat(testDocument.getExtraDiscountAmount()).isEqualByComparingTo(UPDATED_EXTRA_DISCOUNT_AMOUNT);
        assertThat(testDocument.getTotalItemsDiscountAmount()).isEqualByComparingTo(UPDATED_TOTAL_ITEMS_DISCOUNT_AMOUNT);
        assertThat(testDocument.getTotalAmount()).isEqualByComparingTo(UPDATED_TOTAL_AMOUNT);
    }

    @Test
    @Transactional
    void patchNonExistingDocument() throws Exception {
        int databaseSizeBeforeUpdate = documentRepository.findAll().size();
        document.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDocumentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, document.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(document))
            )
            .andExpect(status().isBadRequest());

        // Validate the Document in the database
        List<Document> documentList = documentRepository.findAll();
        assertThat(documentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchDocument() throws Exception {
        int databaseSizeBeforeUpdate = documentRepository.findAll().size();
        document.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDocumentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(document))
            )
            .andExpect(status().isBadRequest());

        // Validate the Document in the database
        List<Document> documentList = documentRepository.findAll();
        assertThat(documentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamDocument() throws Exception {
        int databaseSizeBeforeUpdate = documentRepository.findAll().size();
        document.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDocumentMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(document)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Document in the database
        List<Document> documentList = documentRepository.findAll();
        assertThat(documentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteDocument() throws Exception {
        // Initialize the database
        documentRepository.saveAndFlush(document);

        int databaseSizeBeforeDelete = documentRepository.findAll().size();

        // Delete the document
        restDocumentMockMvc
            .perform(delete(ENTITY_API_URL_ID, document.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Document> documentList = documentRepository.findAll();
        assertThat(documentList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
