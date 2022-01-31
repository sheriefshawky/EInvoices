package eg.inv.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import eg.inv.IntegrationTest;
import eg.inv.domain.DocumentType;
import eg.inv.repository.DocumentTypeRepository;
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
 * Integration tests for the {@link DocumentTypeResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class DocumentTypeResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Instant DEFAULT_ACTIVE_FROM = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_ACTIVE_FROM = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_ACTIVE_TO = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_ACTIVE_TO = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/document-types";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private DocumentTypeRepository documentTypeRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDocumentTypeMockMvc;

    private DocumentType documentType;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DocumentType createEntity(EntityManager em) {
        DocumentType documentType = new DocumentType()
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION)
            .activeFrom(DEFAULT_ACTIVE_FROM)
            .activeTo(DEFAULT_ACTIVE_TO);
        return documentType;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DocumentType createUpdatedEntity(EntityManager em) {
        DocumentType documentType = new DocumentType()
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .activeFrom(UPDATED_ACTIVE_FROM)
            .activeTo(UPDATED_ACTIVE_TO);
        return documentType;
    }

    @BeforeEach
    public void initTest() {
        documentType = createEntity(em);
    }

    @Test
    @Transactional
    void createDocumentType() throws Exception {
        int databaseSizeBeforeCreate = documentTypeRepository.findAll().size();
        // Create the DocumentType
        restDocumentTypeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(documentType)))
            .andExpect(status().isCreated());

        // Validate the DocumentType in the database
        List<DocumentType> documentTypeList = documentTypeRepository.findAll();
        assertThat(documentTypeList).hasSize(databaseSizeBeforeCreate + 1);
        DocumentType testDocumentType = documentTypeList.get(documentTypeList.size() - 1);
        assertThat(testDocumentType.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testDocumentType.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testDocumentType.getActiveFrom()).isEqualTo(DEFAULT_ACTIVE_FROM);
        assertThat(testDocumentType.getActiveTo()).isEqualTo(DEFAULT_ACTIVE_TO);
    }

    @Test
    @Transactional
    void createDocumentTypeWithExistingId() throws Exception {
        // Create the DocumentType with an existing ID
        documentType.setId(1L);

        int databaseSizeBeforeCreate = documentTypeRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restDocumentTypeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(documentType)))
            .andExpect(status().isBadRequest());

        // Validate the DocumentType in the database
        List<DocumentType> documentTypeList = documentTypeRepository.findAll();
        assertThat(documentTypeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllDocumentTypes() throws Exception {
        // Initialize the database
        documentTypeRepository.saveAndFlush(documentType);

        // Get all the documentTypeList
        restDocumentTypeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(documentType.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].activeFrom").value(hasItem(DEFAULT_ACTIVE_FROM.toString())))
            .andExpect(jsonPath("$.[*].activeTo").value(hasItem(DEFAULT_ACTIVE_TO.toString())));
    }

    @Test
    @Transactional
    void getDocumentType() throws Exception {
        // Initialize the database
        documentTypeRepository.saveAndFlush(documentType);

        // Get the documentType
        restDocumentTypeMockMvc
            .perform(get(ENTITY_API_URL_ID, documentType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(documentType.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.activeFrom").value(DEFAULT_ACTIVE_FROM.toString()))
            .andExpect(jsonPath("$.activeTo").value(DEFAULT_ACTIVE_TO.toString()));
    }

    @Test
    @Transactional
    void getNonExistingDocumentType() throws Exception {
        // Get the documentType
        restDocumentTypeMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewDocumentType() throws Exception {
        // Initialize the database
        documentTypeRepository.saveAndFlush(documentType);

        int databaseSizeBeforeUpdate = documentTypeRepository.findAll().size();

        // Update the documentType
        DocumentType updatedDocumentType = documentTypeRepository.findById(documentType.getId()).get();
        // Disconnect from session so that the updates on updatedDocumentType are not directly saved in db
        em.detach(updatedDocumentType);
        updatedDocumentType.name(UPDATED_NAME).description(UPDATED_DESCRIPTION).activeFrom(UPDATED_ACTIVE_FROM).activeTo(UPDATED_ACTIVE_TO);

        restDocumentTypeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedDocumentType.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedDocumentType))
            )
            .andExpect(status().isOk());

        // Validate the DocumentType in the database
        List<DocumentType> documentTypeList = documentTypeRepository.findAll();
        assertThat(documentTypeList).hasSize(databaseSizeBeforeUpdate);
        DocumentType testDocumentType = documentTypeList.get(documentTypeList.size() - 1);
        assertThat(testDocumentType.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testDocumentType.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testDocumentType.getActiveFrom()).isEqualTo(UPDATED_ACTIVE_FROM);
        assertThat(testDocumentType.getActiveTo()).isEqualTo(UPDATED_ACTIVE_TO);
    }

    @Test
    @Transactional
    void putNonExistingDocumentType() throws Exception {
        int databaseSizeBeforeUpdate = documentTypeRepository.findAll().size();
        documentType.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDocumentTypeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, documentType.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(documentType))
            )
            .andExpect(status().isBadRequest());

        // Validate the DocumentType in the database
        List<DocumentType> documentTypeList = documentTypeRepository.findAll();
        assertThat(documentTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchDocumentType() throws Exception {
        int databaseSizeBeforeUpdate = documentTypeRepository.findAll().size();
        documentType.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDocumentTypeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(documentType))
            )
            .andExpect(status().isBadRequest());

        // Validate the DocumentType in the database
        List<DocumentType> documentTypeList = documentTypeRepository.findAll();
        assertThat(documentTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamDocumentType() throws Exception {
        int databaseSizeBeforeUpdate = documentTypeRepository.findAll().size();
        documentType.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDocumentTypeMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(documentType)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the DocumentType in the database
        List<DocumentType> documentTypeList = documentTypeRepository.findAll();
        assertThat(documentTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateDocumentTypeWithPatch() throws Exception {
        // Initialize the database
        documentTypeRepository.saveAndFlush(documentType);

        int databaseSizeBeforeUpdate = documentTypeRepository.findAll().size();

        // Update the documentType using partial update
        DocumentType partialUpdatedDocumentType = new DocumentType();
        partialUpdatedDocumentType.setId(documentType.getId());

        partialUpdatedDocumentType.description(UPDATED_DESCRIPTION).activeFrom(UPDATED_ACTIVE_FROM);

        restDocumentTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDocumentType.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDocumentType))
            )
            .andExpect(status().isOk());

        // Validate the DocumentType in the database
        List<DocumentType> documentTypeList = documentTypeRepository.findAll();
        assertThat(documentTypeList).hasSize(databaseSizeBeforeUpdate);
        DocumentType testDocumentType = documentTypeList.get(documentTypeList.size() - 1);
        assertThat(testDocumentType.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testDocumentType.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testDocumentType.getActiveFrom()).isEqualTo(UPDATED_ACTIVE_FROM);
        assertThat(testDocumentType.getActiveTo()).isEqualTo(DEFAULT_ACTIVE_TO);
    }

    @Test
    @Transactional
    void fullUpdateDocumentTypeWithPatch() throws Exception {
        // Initialize the database
        documentTypeRepository.saveAndFlush(documentType);

        int databaseSizeBeforeUpdate = documentTypeRepository.findAll().size();

        // Update the documentType using partial update
        DocumentType partialUpdatedDocumentType = new DocumentType();
        partialUpdatedDocumentType.setId(documentType.getId());

        partialUpdatedDocumentType
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .activeFrom(UPDATED_ACTIVE_FROM)
            .activeTo(UPDATED_ACTIVE_TO);

        restDocumentTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDocumentType.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDocumentType))
            )
            .andExpect(status().isOk());

        // Validate the DocumentType in the database
        List<DocumentType> documentTypeList = documentTypeRepository.findAll();
        assertThat(documentTypeList).hasSize(databaseSizeBeforeUpdate);
        DocumentType testDocumentType = documentTypeList.get(documentTypeList.size() - 1);
        assertThat(testDocumentType.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testDocumentType.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testDocumentType.getActiveFrom()).isEqualTo(UPDATED_ACTIVE_FROM);
        assertThat(testDocumentType.getActiveTo()).isEqualTo(UPDATED_ACTIVE_TO);
    }

    @Test
    @Transactional
    void patchNonExistingDocumentType() throws Exception {
        int databaseSizeBeforeUpdate = documentTypeRepository.findAll().size();
        documentType.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDocumentTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, documentType.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(documentType))
            )
            .andExpect(status().isBadRequest());

        // Validate the DocumentType in the database
        List<DocumentType> documentTypeList = documentTypeRepository.findAll();
        assertThat(documentTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchDocumentType() throws Exception {
        int databaseSizeBeforeUpdate = documentTypeRepository.findAll().size();
        documentType.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDocumentTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(documentType))
            )
            .andExpect(status().isBadRequest());

        // Validate the DocumentType in the database
        List<DocumentType> documentTypeList = documentTypeRepository.findAll();
        assertThat(documentTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamDocumentType() throws Exception {
        int databaseSizeBeforeUpdate = documentTypeRepository.findAll().size();
        documentType.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDocumentTypeMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(documentType))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the DocumentType in the database
        List<DocumentType> documentTypeList = documentTypeRepository.findAll();
        assertThat(documentTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteDocumentType() throws Exception {
        // Initialize the database
        documentTypeRepository.saveAndFlush(documentType);

        int databaseSizeBeforeDelete = documentTypeRepository.findAll().size();

        // Delete the documentType
        restDocumentTypeMockMvc
            .perform(delete(ENTITY_API_URL_ID, documentType.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<DocumentType> documentTypeList = documentTypeRepository.findAll();
        assertThat(documentTypeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
