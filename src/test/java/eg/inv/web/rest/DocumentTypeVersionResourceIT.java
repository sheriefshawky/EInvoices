package eg.inv.web.rest;

import static eg.inv.web.rest.TestUtil.sameNumber;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import eg.inv.IntegrationTest;
import eg.inv.domain.DocumentTypeVersion;
import eg.inv.repository.DocumentTypeVersionRepository;
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
 * Integration tests for the {@link DocumentTypeVersionResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class DocumentTypeVersionResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_VERSION_NUMBER = new BigDecimal(1);
    private static final BigDecimal UPDATED_VERSION_NUMBER = new BigDecimal(2);

    private static final String DEFAULT_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_STATUS = "BBBBBBBBBB";

    private static final Instant DEFAULT_ACTIVE_FROM = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_ACTIVE_FROM = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_ACTIVE_TO = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_ACTIVE_TO = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/document-type-versions";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private DocumentTypeVersionRepository documentTypeVersionRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDocumentTypeVersionMockMvc;

    private DocumentTypeVersion documentTypeVersion;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DocumentTypeVersion createEntity(EntityManager em) {
        DocumentTypeVersion documentTypeVersion = new DocumentTypeVersion()
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION)
            .versionNumber(DEFAULT_VERSION_NUMBER)
            .status(DEFAULT_STATUS)
            .activeFrom(DEFAULT_ACTIVE_FROM)
            .activeTo(DEFAULT_ACTIVE_TO);
        return documentTypeVersion;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DocumentTypeVersion createUpdatedEntity(EntityManager em) {
        DocumentTypeVersion documentTypeVersion = new DocumentTypeVersion()
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .versionNumber(UPDATED_VERSION_NUMBER)
            .status(UPDATED_STATUS)
            .activeFrom(UPDATED_ACTIVE_FROM)
            .activeTo(UPDATED_ACTIVE_TO);
        return documentTypeVersion;
    }

    @BeforeEach
    public void initTest() {
        documentTypeVersion = createEntity(em);
    }

    @Test
    @Transactional
    void createDocumentTypeVersion() throws Exception {
        int databaseSizeBeforeCreate = documentTypeVersionRepository.findAll().size();
        // Create the DocumentTypeVersion
        restDocumentTypeVersionMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(documentTypeVersion))
            )
            .andExpect(status().isCreated());

        // Validate the DocumentTypeVersion in the database
        List<DocumentTypeVersion> documentTypeVersionList = documentTypeVersionRepository.findAll();
        assertThat(documentTypeVersionList).hasSize(databaseSizeBeforeCreate + 1);
        DocumentTypeVersion testDocumentTypeVersion = documentTypeVersionList.get(documentTypeVersionList.size() - 1);
        assertThat(testDocumentTypeVersion.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testDocumentTypeVersion.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testDocumentTypeVersion.getVersionNumber()).isEqualByComparingTo(DEFAULT_VERSION_NUMBER);
        assertThat(testDocumentTypeVersion.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testDocumentTypeVersion.getActiveFrom()).isEqualTo(DEFAULT_ACTIVE_FROM);
        assertThat(testDocumentTypeVersion.getActiveTo()).isEqualTo(DEFAULT_ACTIVE_TO);
    }

    @Test
    @Transactional
    void createDocumentTypeVersionWithExistingId() throws Exception {
        // Create the DocumentTypeVersion with an existing ID
        documentTypeVersion.setId(1L);

        int databaseSizeBeforeCreate = documentTypeVersionRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restDocumentTypeVersionMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(documentTypeVersion))
            )
            .andExpect(status().isBadRequest());

        // Validate the DocumentTypeVersion in the database
        List<DocumentTypeVersion> documentTypeVersionList = documentTypeVersionRepository.findAll();
        assertThat(documentTypeVersionList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllDocumentTypeVersions() throws Exception {
        // Initialize the database
        documentTypeVersionRepository.saveAndFlush(documentTypeVersion);

        // Get all the documentTypeVersionList
        restDocumentTypeVersionMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(documentTypeVersion.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].versionNumber").value(hasItem(sameNumber(DEFAULT_VERSION_NUMBER))))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].activeFrom").value(hasItem(DEFAULT_ACTIVE_FROM.toString())))
            .andExpect(jsonPath("$.[*].activeTo").value(hasItem(DEFAULT_ACTIVE_TO.toString())));
    }

    @Test
    @Transactional
    void getDocumentTypeVersion() throws Exception {
        // Initialize the database
        documentTypeVersionRepository.saveAndFlush(documentTypeVersion);

        // Get the documentTypeVersion
        restDocumentTypeVersionMockMvc
            .perform(get(ENTITY_API_URL_ID, documentTypeVersion.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(documentTypeVersion.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.versionNumber").value(sameNumber(DEFAULT_VERSION_NUMBER)))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS))
            .andExpect(jsonPath("$.activeFrom").value(DEFAULT_ACTIVE_FROM.toString()))
            .andExpect(jsonPath("$.activeTo").value(DEFAULT_ACTIVE_TO.toString()));
    }

    @Test
    @Transactional
    void getNonExistingDocumentTypeVersion() throws Exception {
        // Get the documentTypeVersion
        restDocumentTypeVersionMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewDocumentTypeVersion() throws Exception {
        // Initialize the database
        documentTypeVersionRepository.saveAndFlush(documentTypeVersion);

        int databaseSizeBeforeUpdate = documentTypeVersionRepository.findAll().size();

        // Update the documentTypeVersion
        DocumentTypeVersion updatedDocumentTypeVersion = documentTypeVersionRepository.findById(documentTypeVersion.getId()).get();
        // Disconnect from session so that the updates on updatedDocumentTypeVersion are not directly saved in db
        em.detach(updatedDocumentTypeVersion);
        updatedDocumentTypeVersion
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .versionNumber(UPDATED_VERSION_NUMBER)
            .status(UPDATED_STATUS)
            .activeFrom(UPDATED_ACTIVE_FROM)
            .activeTo(UPDATED_ACTIVE_TO);

        restDocumentTypeVersionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedDocumentTypeVersion.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedDocumentTypeVersion))
            )
            .andExpect(status().isOk());

        // Validate the DocumentTypeVersion in the database
        List<DocumentTypeVersion> documentTypeVersionList = documentTypeVersionRepository.findAll();
        assertThat(documentTypeVersionList).hasSize(databaseSizeBeforeUpdate);
        DocumentTypeVersion testDocumentTypeVersion = documentTypeVersionList.get(documentTypeVersionList.size() - 1);
        assertThat(testDocumentTypeVersion.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testDocumentTypeVersion.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testDocumentTypeVersion.getVersionNumber()).isEqualByComparingTo(UPDATED_VERSION_NUMBER);
        assertThat(testDocumentTypeVersion.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testDocumentTypeVersion.getActiveFrom()).isEqualTo(UPDATED_ACTIVE_FROM);
        assertThat(testDocumentTypeVersion.getActiveTo()).isEqualTo(UPDATED_ACTIVE_TO);
    }

    @Test
    @Transactional
    void putNonExistingDocumentTypeVersion() throws Exception {
        int databaseSizeBeforeUpdate = documentTypeVersionRepository.findAll().size();
        documentTypeVersion.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDocumentTypeVersionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, documentTypeVersion.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(documentTypeVersion))
            )
            .andExpect(status().isBadRequest());

        // Validate the DocumentTypeVersion in the database
        List<DocumentTypeVersion> documentTypeVersionList = documentTypeVersionRepository.findAll();
        assertThat(documentTypeVersionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchDocumentTypeVersion() throws Exception {
        int databaseSizeBeforeUpdate = documentTypeVersionRepository.findAll().size();
        documentTypeVersion.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDocumentTypeVersionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(documentTypeVersion))
            )
            .andExpect(status().isBadRequest());

        // Validate the DocumentTypeVersion in the database
        List<DocumentTypeVersion> documentTypeVersionList = documentTypeVersionRepository.findAll();
        assertThat(documentTypeVersionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamDocumentTypeVersion() throws Exception {
        int databaseSizeBeforeUpdate = documentTypeVersionRepository.findAll().size();
        documentTypeVersion.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDocumentTypeVersionMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(documentTypeVersion))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the DocumentTypeVersion in the database
        List<DocumentTypeVersion> documentTypeVersionList = documentTypeVersionRepository.findAll();
        assertThat(documentTypeVersionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateDocumentTypeVersionWithPatch() throws Exception {
        // Initialize the database
        documentTypeVersionRepository.saveAndFlush(documentTypeVersion);

        int databaseSizeBeforeUpdate = documentTypeVersionRepository.findAll().size();

        // Update the documentTypeVersion using partial update
        DocumentTypeVersion partialUpdatedDocumentTypeVersion = new DocumentTypeVersion();
        partialUpdatedDocumentTypeVersion.setId(documentTypeVersion.getId());

        partialUpdatedDocumentTypeVersion.name(UPDATED_NAME).description(UPDATED_DESCRIPTION).status(UPDATED_STATUS);

        restDocumentTypeVersionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDocumentTypeVersion.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDocumentTypeVersion))
            )
            .andExpect(status().isOk());

        // Validate the DocumentTypeVersion in the database
        List<DocumentTypeVersion> documentTypeVersionList = documentTypeVersionRepository.findAll();
        assertThat(documentTypeVersionList).hasSize(databaseSizeBeforeUpdate);
        DocumentTypeVersion testDocumentTypeVersion = documentTypeVersionList.get(documentTypeVersionList.size() - 1);
        assertThat(testDocumentTypeVersion.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testDocumentTypeVersion.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testDocumentTypeVersion.getVersionNumber()).isEqualByComparingTo(DEFAULT_VERSION_NUMBER);
        assertThat(testDocumentTypeVersion.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testDocumentTypeVersion.getActiveFrom()).isEqualTo(DEFAULT_ACTIVE_FROM);
        assertThat(testDocumentTypeVersion.getActiveTo()).isEqualTo(DEFAULT_ACTIVE_TO);
    }

    @Test
    @Transactional
    void fullUpdateDocumentTypeVersionWithPatch() throws Exception {
        // Initialize the database
        documentTypeVersionRepository.saveAndFlush(documentTypeVersion);

        int databaseSizeBeforeUpdate = documentTypeVersionRepository.findAll().size();

        // Update the documentTypeVersion using partial update
        DocumentTypeVersion partialUpdatedDocumentTypeVersion = new DocumentTypeVersion();
        partialUpdatedDocumentTypeVersion.setId(documentTypeVersion.getId());

        partialUpdatedDocumentTypeVersion
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .versionNumber(UPDATED_VERSION_NUMBER)
            .status(UPDATED_STATUS)
            .activeFrom(UPDATED_ACTIVE_FROM)
            .activeTo(UPDATED_ACTIVE_TO);

        restDocumentTypeVersionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDocumentTypeVersion.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDocumentTypeVersion))
            )
            .andExpect(status().isOk());

        // Validate the DocumentTypeVersion in the database
        List<DocumentTypeVersion> documentTypeVersionList = documentTypeVersionRepository.findAll();
        assertThat(documentTypeVersionList).hasSize(databaseSizeBeforeUpdate);
        DocumentTypeVersion testDocumentTypeVersion = documentTypeVersionList.get(documentTypeVersionList.size() - 1);
        assertThat(testDocumentTypeVersion.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testDocumentTypeVersion.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testDocumentTypeVersion.getVersionNumber()).isEqualByComparingTo(UPDATED_VERSION_NUMBER);
        assertThat(testDocumentTypeVersion.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testDocumentTypeVersion.getActiveFrom()).isEqualTo(UPDATED_ACTIVE_FROM);
        assertThat(testDocumentTypeVersion.getActiveTo()).isEqualTo(UPDATED_ACTIVE_TO);
    }

    @Test
    @Transactional
    void patchNonExistingDocumentTypeVersion() throws Exception {
        int databaseSizeBeforeUpdate = documentTypeVersionRepository.findAll().size();
        documentTypeVersion.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDocumentTypeVersionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, documentTypeVersion.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(documentTypeVersion))
            )
            .andExpect(status().isBadRequest());

        // Validate the DocumentTypeVersion in the database
        List<DocumentTypeVersion> documentTypeVersionList = documentTypeVersionRepository.findAll();
        assertThat(documentTypeVersionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchDocumentTypeVersion() throws Exception {
        int databaseSizeBeforeUpdate = documentTypeVersionRepository.findAll().size();
        documentTypeVersion.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDocumentTypeVersionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(documentTypeVersion))
            )
            .andExpect(status().isBadRequest());

        // Validate the DocumentTypeVersion in the database
        List<DocumentTypeVersion> documentTypeVersionList = documentTypeVersionRepository.findAll();
        assertThat(documentTypeVersionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamDocumentTypeVersion() throws Exception {
        int databaseSizeBeforeUpdate = documentTypeVersionRepository.findAll().size();
        documentTypeVersion.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDocumentTypeVersionMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(documentTypeVersion))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the DocumentTypeVersion in the database
        List<DocumentTypeVersion> documentTypeVersionList = documentTypeVersionRepository.findAll();
        assertThat(documentTypeVersionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteDocumentTypeVersion() throws Exception {
        // Initialize the database
        documentTypeVersionRepository.saveAndFlush(documentTypeVersion);

        int databaseSizeBeforeDelete = documentTypeVersionRepository.findAll().size();

        // Delete the documentTypeVersion
        restDocumentTypeVersionMockMvc
            .perform(delete(ENTITY_API_URL_ID, documentTypeVersion.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<DocumentTypeVersion> documentTypeVersionList = documentTypeVersionRepository.findAll();
        assertThat(documentTypeVersionList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
