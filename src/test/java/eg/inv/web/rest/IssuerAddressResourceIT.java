package eg.inv.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import eg.inv.IntegrationTest;
import eg.inv.domain.IssuerAddress;
import eg.inv.repository.IssuerAddressRepository;
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
 * Integration tests for the {@link IssuerAddressResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class IssuerAddressResourceIT {

    private static final String DEFAULT_BRANCH_ID = "AAAAAAAAAA";
    private static final String UPDATED_BRANCH_ID = "BBBBBBBBBB";

    private static final String DEFAULT_COUNTRY = "AAAAAAAAAA";
    private static final String UPDATED_COUNTRY = "BBBBBBBBBB";

    private static final String DEFAULT_GOVERNATE = "AAAAAAAAAA";
    private static final String UPDATED_GOVERNATE = "BBBBBBBBBB";

    private static final String DEFAULT_REGION_CITY = "AAAAAAAAAA";
    private static final String UPDATED_REGION_CITY = "BBBBBBBBBB";

    private static final String DEFAULT_STREET = "AAAAAAAAAA";
    private static final String UPDATED_STREET = "BBBBBBBBBB";

    private static final String DEFAULT_BUILDING_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_BUILDING_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_POSTAL_CODE = "AAAAAAAAAA";
    private static final String UPDATED_POSTAL_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_FLOOR = "AAAAAAAAAA";
    private static final String UPDATED_FLOOR = "BBBBBBBBBB";

    private static final String DEFAULT_ROOM = "AAAAAAAAAA";
    private static final String UPDATED_ROOM = "BBBBBBBBBB";

    private static final String DEFAULT_LANDMARK = "AAAAAAAAAA";
    private static final String UPDATED_LANDMARK = "BBBBBBBBBB";

    private static final String DEFAULT_ADDITIONAL_INFORMATION = "AAAAAAAAAA";
    private static final String UPDATED_ADDITIONAL_INFORMATION = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/issuer-addresses";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private IssuerAddressRepository issuerAddressRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restIssuerAddressMockMvc;

    private IssuerAddress issuerAddress;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static IssuerAddress createEntity(EntityManager em) {
        IssuerAddress issuerAddress = new IssuerAddress()
            .branchId(DEFAULT_BRANCH_ID)
            .country(DEFAULT_COUNTRY)
            .governate(DEFAULT_GOVERNATE)
            .regionCity(DEFAULT_REGION_CITY)
            .street(DEFAULT_STREET)
            .buildingNumber(DEFAULT_BUILDING_NUMBER)
            .postalCode(DEFAULT_POSTAL_CODE)
            .floor(DEFAULT_FLOOR)
            .room(DEFAULT_ROOM)
            .landmark(DEFAULT_LANDMARK)
            .additionalInformation(DEFAULT_ADDITIONAL_INFORMATION);
        return issuerAddress;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static IssuerAddress createUpdatedEntity(EntityManager em) {
        IssuerAddress issuerAddress = new IssuerAddress()
            .branchId(UPDATED_BRANCH_ID)
            .country(UPDATED_COUNTRY)
            .governate(UPDATED_GOVERNATE)
            .regionCity(UPDATED_REGION_CITY)
            .street(UPDATED_STREET)
            .buildingNumber(UPDATED_BUILDING_NUMBER)
            .postalCode(UPDATED_POSTAL_CODE)
            .floor(UPDATED_FLOOR)
            .room(UPDATED_ROOM)
            .landmark(UPDATED_LANDMARK)
            .additionalInformation(UPDATED_ADDITIONAL_INFORMATION);
        return issuerAddress;
    }

    @BeforeEach
    public void initTest() {
        issuerAddress = createEntity(em);
    }

    @Test
    @Transactional
    void createIssuerAddress() throws Exception {
        int databaseSizeBeforeCreate = issuerAddressRepository.findAll().size();
        // Create the IssuerAddress
        restIssuerAddressMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(issuerAddress)))
            .andExpect(status().isCreated());

        // Validate the IssuerAddress in the database
        List<IssuerAddress> issuerAddressList = issuerAddressRepository.findAll();
        assertThat(issuerAddressList).hasSize(databaseSizeBeforeCreate + 1);
        IssuerAddress testIssuerAddress = issuerAddressList.get(issuerAddressList.size() - 1);
        assertThat(testIssuerAddress.getBranchId()).isEqualTo(DEFAULT_BRANCH_ID);
        assertThat(testIssuerAddress.getCountry()).isEqualTo(DEFAULT_COUNTRY);
        assertThat(testIssuerAddress.getGovernate()).isEqualTo(DEFAULT_GOVERNATE);
        assertThat(testIssuerAddress.getRegionCity()).isEqualTo(DEFAULT_REGION_CITY);
        assertThat(testIssuerAddress.getStreet()).isEqualTo(DEFAULT_STREET);
        assertThat(testIssuerAddress.getBuildingNumber()).isEqualTo(DEFAULT_BUILDING_NUMBER);
        assertThat(testIssuerAddress.getPostalCode()).isEqualTo(DEFAULT_POSTAL_CODE);
        assertThat(testIssuerAddress.getFloor()).isEqualTo(DEFAULT_FLOOR);
        assertThat(testIssuerAddress.getRoom()).isEqualTo(DEFAULT_ROOM);
        assertThat(testIssuerAddress.getLandmark()).isEqualTo(DEFAULT_LANDMARK);
        assertThat(testIssuerAddress.getAdditionalInformation()).isEqualTo(DEFAULT_ADDITIONAL_INFORMATION);
    }

    @Test
    @Transactional
    void createIssuerAddressWithExistingId() throws Exception {
        // Create the IssuerAddress with an existing ID
        issuerAddress.setId(1L);

        int databaseSizeBeforeCreate = issuerAddressRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restIssuerAddressMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(issuerAddress)))
            .andExpect(status().isBadRequest());

        // Validate the IssuerAddress in the database
        List<IssuerAddress> issuerAddressList = issuerAddressRepository.findAll();
        assertThat(issuerAddressList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllIssuerAddresses() throws Exception {
        // Initialize the database
        issuerAddressRepository.saveAndFlush(issuerAddress);

        // Get all the issuerAddressList
        restIssuerAddressMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(issuerAddress.getId().intValue())))
            .andExpect(jsonPath("$.[*].branchId").value(hasItem(DEFAULT_BRANCH_ID)))
            .andExpect(jsonPath("$.[*].country").value(hasItem(DEFAULT_COUNTRY)))
            .andExpect(jsonPath("$.[*].governate").value(hasItem(DEFAULT_GOVERNATE)))
            .andExpect(jsonPath("$.[*].regionCity").value(hasItem(DEFAULT_REGION_CITY)))
            .andExpect(jsonPath("$.[*].street").value(hasItem(DEFAULT_STREET)))
            .andExpect(jsonPath("$.[*].buildingNumber").value(hasItem(DEFAULT_BUILDING_NUMBER)))
            .andExpect(jsonPath("$.[*].postalCode").value(hasItem(DEFAULT_POSTAL_CODE)))
            .andExpect(jsonPath("$.[*].floor").value(hasItem(DEFAULT_FLOOR)))
            .andExpect(jsonPath("$.[*].room").value(hasItem(DEFAULT_ROOM)))
            .andExpect(jsonPath("$.[*].landmark").value(hasItem(DEFAULT_LANDMARK)))
            .andExpect(jsonPath("$.[*].additionalInformation").value(hasItem(DEFAULT_ADDITIONAL_INFORMATION)));
    }

    @Test
    @Transactional
    void getIssuerAddress() throws Exception {
        // Initialize the database
        issuerAddressRepository.saveAndFlush(issuerAddress);

        // Get the issuerAddress
        restIssuerAddressMockMvc
            .perform(get(ENTITY_API_URL_ID, issuerAddress.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(issuerAddress.getId().intValue()))
            .andExpect(jsonPath("$.branchId").value(DEFAULT_BRANCH_ID))
            .andExpect(jsonPath("$.country").value(DEFAULT_COUNTRY))
            .andExpect(jsonPath("$.governate").value(DEFAULT_GOVERNATE))
            .andExpect(jsonPath("$.regionCity").value(DEFAULT_REGION_CITY))
            .andExpect(jsonPath("$.street").value(DEFAULT_STREET))
            .andExpect(jsonPath("$.buildingNumber").value(DEFAULT_BUILDING_NUMBER))
            .andExpect(jsonPath("$.postalCode").value(DEFAULT_POSTAL_CODE))
            .andExpect(jsonPath("$.floor").value(DEFAULT_FLOOR))
            .andExpect(jsonPath("$.room").value(DEFAULT_ROOM))
            .andExpect(jsonPath("$.landmark").value(DEFAULT_LANDMARK))
            .andExpect(jsonPath("$.additionalInformation").value(DEFAULT_ADDITIONAL_INFORMATION));
    }

    @Test
    @Transactional
    void getNonExistingIssuerAddress() throws Exception {
        // Get the issuerAddress
        restIssuerAddressMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewIssuerAddress() throws Exception {
        // Initialize the database
        issuerAddressRepository.saveAndFlush(issuerAddress);

        int databaseSizeBeforeUpdate = issuerAddressRepository.findAll().size();

        // Update the issuerAddress
        IssuerAddress updatedIssuerAddress = issuerAddressRepository.findById(issuerAddress.getId()).get();
        // Disconnect from session so that the updates on updatedIssuerAddress are not directly saved in db
        em.detach(updatedIssuerAddress);
        updatedIssuerAddress
            .branchId(UPDATED_BRANCH_ID)
            .country(UPDATED_COUNTRY)
            .governate(UPDATED_GOVERNATE)
            .regionCity(UPDATED_REGION_CITY)
            .street(UPDATED_STREET)
            .buildingNumber(UPDATED_BUILDING_NUMBER)
            .postalCode(UPDATED_POSTAL_CODE)
            .floor(UPDATED_FLOOR)
            .room(UPDATED_ROOM)
            .landmark(UPDATED_LANDMARK)
            .additionalInformation(UPDATED_ADDITIONAL_INFORMATION);

        restIssuerAddressMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedIssuerAddress.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedIssuerAddress))
            )
            .andExpect(status().isOk());

        // Validate the IssuerAddress in the database
        List<IssuerAddress> issuerAddressList = issuerAddressRepository.findAll();
        assertThat(issuerAddressList).hasSize(databaseSizeBeforeUpdate);
        IssuerAddress testIssuerAddress = issuerAddressList.get(issuerAddressList.size() - 1);
        assertThat(testIssuerAddress.getBranchId()).isEqualTo(UPDATED_BRANCH_ID);
        assertThat(testIssuerAddress.getCountry()).isEqualTo(UPDATED_COUNTRY);
        assertThat(testIssuerAddress.getGovernate()).isEqualTo(UPDATED_GOVERNATE);
        assertThat(testIssuerAddress.getRegionCity()).isEqualTo(UPDATED_REGION_CITY);
        assertThat(testIssuerAddress.getStreet()).isEqualTo(UPDATED_STREET);
        assertThat(testIssuerAddress.getBuildingNumber()).isEqualTo(UPDATED_BUILDING_NUMBER);
        assertThat(testIssuerAddress.getPostalCode()).isEqualTo(UPDATED_POSTAL_CODE);
        assertThat(testIssuerAddress.getFloor()).isEqualTo(UPDATED_FLOOR);
        assertThat(testIssuerAddress.getRoom()).isEqualTo(UPDATED_ROOM);
        assertThat(testIssuerAddress.getLandmark()).isEqualTo(UPDATED_LANDMARK);
        assertThat(testIssuerAddress.getAdditionalInformation()).isEqualTo(UPDATED_ADDITIONAL_INFORMATION);
    }

    @Test
    @Transactional
    void putNonExistingIssuerAddress() throws Exception {
        int databaseSizeBeforeUpdate = issuerAddressRepository.findAll().size();
        issuerAddress.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restIssuerAddressMockMvc
            .perform(
                put(ENTITY_API_URL_ID, issuerAddress.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(issuerAddress))
            )
            .andExpect(status().isBadRequest());

        // Validate the IssuerAddress in the database
        List<IssuerAddress> issuerAddressList = issuerAddressRepository.findAll();
        assertThat(issuerAddressList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchIssuerAddress() throws Exception {
        int databaseSizeBeforeUpdate = issuerAddressRepository.findAll().size();
        issuerAddress.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restIssuerAddressMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(issuerAddress))
            )
            .andExpect(status().isBadRequest());

        // Validate the IssuerAddress in the database
        List<IssuerAddress> issuerAddressList = issuerAddressRepository.findAll();
        assertThat(issuerAddressList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamIssuerAddress() throws Exception {
        int databaseSizeBeforeUpdate = issuerAddressRepository.findAll().size();
        issuerAddress.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restIssuerAddressMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(issuerAddress)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the IssuerAddress in the database
        List<IssuerAddress> issuerAddressList = issuerAddressRepository.findAll();
        assertThat(issuerAddressList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateIssuerAddressWithPatch() throws Exception {
        // Initialize the database
        issuerAddressRepository.saveAndFlush(issuerAddress);

        int databaseSizeBeforeUpdate = issuerAddressRepository.findAll().size();

        // Update the issuerAddress using partial update
        IssuerAddress partialUpdatedIssuerAddress = new IssuerAddress();
        partialUpdatedIssuerAddress.setId(issuerAddress.getId());

        partialUpdatedIssuerAddress.regionCity(UPDATED_REGION_CITY).buildingNumber(UPDATED_BUILDING_NUMBER);

        restIssuerAddressMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedIssuerAddress.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedIssuerAddress))
            )
            .andExpect(status().isOk());

        // Validate the IssuerAddress in the database
        List<IssuerAddress> issuerAddressList = issuerAddressRepository.findAll();
        assertThat(issuerAddressList).hasSize(databaseSizeBeforeUpdate);
        IssuerAddress testIssuerAddress = issuerAddressList.get(issuerAddressList.size() - 1);
        assertThat(testIssuerAddress.getBranchId()).isEqualTo(DEFAULT_BRANCH_ID);
        assertThat(testIssuerAddress.getCountry()).isEqualTo(DEFAULT_COUNTRY);
        assertThat(testIssuerAddress.getGovernate()).isEqualTo(DEFAULT_GOVERNATE);
        assertThat(testIssuerAddress.getRegionCity()).isEqualTo(UPDATED_REGION_CITY);
        assertThat(testIssuerAddress.getStreet()).isEqualTo(DEFAULT_STREET);
        assertThat(testIssuerAddress.getBuildingNumber()).isEqualTo(UPDATED_BUILDING_NUMBER);
        assertThat(testIssuerAddress.getPostalCode()).isEqualTo(DEFAULT_POSTAL_CODE);
        assertThat(testIssuerAddress.getFloor()).isEqualTo(DEFAULT_FLOOR);
        assertThat(testIssuerAddress.getRoom()).isEqualTo(DEFAULT_ROOM);
        assertThat(testIssuerAddress.getLandmark()).isEqualTo(DEFAULT_LANDMARK);
        assertThat(testIssuerAddress.getAdditionalInformation()).isEqualTo(DEFAULT_ADDITIONAL_INFORMATION);
    }

    @Test
    @Transactional
    void fullUpdateIssuerAddressWithPatch() throws Exception {
        // Initialize the database
        issuerAddressRepository.saveAndFlush(issuerAddress);

        int databaseSizeBeforeUpdate = issuerAddressRepository.findAll().size();

        // Update the issuerAddress using partial update
        IssuerAddress partialUpdatedIssuerAddress = new IssuerAddress();
        partialUpdatedIssuerAddress.setId(issuerAddress.getId());

        partialUpdatedIssuerAddress
            .branchId(UPDATED_BRANCH_ID)
            .country(UPDATED_COUNTRY)
            .governate(UPDATED_GOVERNATE)
            .regionCity(UPDATED_REGION_CITY)
            .street(UPDATED_STREET)
            .buildingNumber(UPDATED_BUILDING_NUMBER)
            .postalCode(UPDATED_POSTAL_CODE)
            .floor(UPDATED_FLOOR)
            .room(UPDATED_ROOM)
            .landmark(UPDATED_LANDMARK)
            .additionalInformation(UPDATED_ADDITIONAL_INFORMATION);

        restIssuerAddressMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedIssuerAddress.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedIssuerAddress))
            )
            .andExpect(status().isOk());

        // Validate the IssuerAddress in the database
        List<IssuerAddress> issuerAddressList = issuerAddressRepository.findAll();
        assertThat(issuerAddressList).hasSize(databaseSizeBeforeUpdate);
        IssuerAddress testIssuerAddress = issuerAddressList.get(issuerAddressList.size() - 1);
        assertThat(testIssuerAddress.getBranchId()).isEqualTo(UPDATED_BRANCH_ID);
        assertThat(testIssuerAddress.getCountry()).isEqualTo(UPDATED_COUNTRY);
        assertThat(testIssuerAddress.getGovernate()).isEqualTo(UPDATED_GOVERNATE);
        assertThat(testIssuerAddress.getRegionCity()).isEqualTo(UPDATED_REGION_CITY);
        assertThat(testIssuerAddress.getStreet()).isEqualTo(UPDATED_STREET);
        assertThat(testIssuerAddress.getBuildingNumber()).isEqualTo(UPDATED_BUILDING_NUMBER);
        assertThat(testIssuerAddress.getPostalCode()).isEqualTo(UPDATED_POSTAL_CODE);
        assertThat(testIssuerAddress.getFloor()).isEqualTo(UPDATED_FLOOR);
        assertThat(testIssuerAddress.getRoom()).isEqualTo(UPDATED_ROOM);
        assertThat(testIssuerAddress.getLandmark()).isEqualTo(UPDATED_LANDMARK);
        assertThat(testIssuerAddress.getAdditionalInformation()).isEqualTo(UPDATED_ADDITIONAL_INFORMATION);
    }

    @Test
    @Transactional
    void patchNonExistingIssuerAddress() throws Exception {
        int databaseSizeBeforeUpdate = issuerAddressRepository.findAll().size();
        issuerAddress.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restIssuerAddressMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, issuerAddress.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(issuerAddress))
            )
            .andExpect(status().isBadRequest());

        // Validate the IssuerAddress in the database
        List<IssuerAddress> issuerAddressList = issuerAddressRepository.findAll();
        assertThat(issuerAddressList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchIssuerAddress() throws Exception {
        int databaseSizeBeforeUpdate = issuerAddressRepository.findAll().size();
        issuerAddress.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restIssuerAddressMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(issuerAddress))
            )
            .andExpect(status().isBadRequest());

        // Validate the IssuerAddress in the database
        List<IssuerAddress> issuerAddressList = issuerAddressRepository.findAll();
        assertThat(issuerAddressList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamIssuerAddress() throws Exception {
        int databaseSizeBeforeUpdate = issuerAddressRepository.findAll().size();
        issuerAddress.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restIssuerAddressMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(issuerAddress))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the IssuerAddress in the database
        List<IssuerAddress> issuerAddressList = issuerAddressRepository.findAll();
        assertThat(issuerAddressList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteIssuerAddress() throws Exception {
        // Initialize the database
        issuerAddressRepository.saveAndFlush(issuerAddress);

        int databaseSizeBeforeDelete = issuerAddressRepository.findAll().size();

        // Delete the issuerAddress
        restIssuerAddressMockMvc
            .perform(delete(ENTITY_API_URL_ID, issuerAddress.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<IssuerAddress> issuerAddressList = issuerAddressRepository.findAll();
        assertThat(issuerAddressList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
