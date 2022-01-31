package eg.inv.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import eg.inv.IntegrationTest;
import eg.inv.domain.ReceiverAddress;
import eg.inv.repository.ReceiverAddressRepository;
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
 * Integration tests for the {@link ReceiverAddressResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ReceiverAddressResourceIT {

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

    private static final String ENTITY_API_URL = "/api/receiver-addresses";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ReceiverAddressRepository receiverAddressRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restReceiverAddressMockMvc;

    private ReceiverAddress receiverAddress;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ReceiverAddress createEntity(EntityManager em) {
        ReceiverAddress receiverAddress = new ReceiverAddress()
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
        return receiverAddress;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ReceiverAddress createUpdatedEntity(EntityManager em) {
        ReceiverAddress receiverAddress = new ReceiverAddress()
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
        return receiverAddress;
    }

    @BeforeEach
    public void initTest() {
        receiverAddress = createEntity(em);
    }

    @Test
    @Transactional
    void createReceiverAddress() throws Exception {
        int databaseSizeBeforeCreate = receiverAddressRepository.findAll().size();
        // Create the ReceiverAddress
        restReceiverAddressMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(receiverAddress))
            )
            .andExpect(status().isCreated());

        // Validate the ReceiverAddress in the database
        List<ReceiverAddress> receiverAddressList = receiverAddressRepository.findAll();
        assertThat(receiverAddressList).hasSize(databaseSizeBeforeCreate + 1);
        ReceiverAddress testReceiverAddress = receiverAddressList.get(receiverAddressList.size() - 1);
        assertThat(testReceiverAddress.getCountry()).isEqualTo(DEFAULT_COUNTRY);
        assertThat(testReceiverAddress.getGovernate()).isEqualTo(DEFAULT_GOVERNATE);
        assertThat(testReceiverAddress.getRegionCity()).isEqualTo(DEFAULT_REGION_CITY);
        assertThat(testReceiverAddress.getStreet()).isEqualTo(DEFAULT_STREET);
        assertThat(testReceiverAddress.getBuildingNumber()).isEqualTo(DEFAULT_BUILDING_NUMBER);
        assertThat(testReceiverAddress.getPostalCode()).isEqualTo(DEFAULT_POSTAL_CODE);
        assertThat(testReceiverAddress.getFloor()).isEqualTo(DEFAULT_FLOOR);
        assertThat(testReceiverAddress.getRoom()).isEqualTo(DEFAULT_ROOM);
        assertThat(testReceiverAddress.getLandmark()).isEqualTo(DEFAULT_LANDMARK);
        assertThat(testReceiverAddress.getAdditionalInformation()).isEqualTo(DEFAULT_ADDITIONAL_INFORMATION);
    }

    @Test
    @Transactional
    void createReceiverAddressWithExistingId() throws Exception {
        // Create the ReceiverAddress with an existing ID
        receiverAddress.setId(1L);

        int databaseSizeBeforeCreate = receiverAddressRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restReceiverAddressMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(receiverAddress))
            )
            .andExpect(status().isBadRequest());

        // Validate the ReceiverAddress in the database
        List<ReceiverAddress> receiverAddressList = receiverAddressRepository.findAll();
        assertThat(receiverAddressList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllReceiverAddresses() throws Exception {
        // Initialize the database
        receiverAddressRepository.saveAndFlush(receiverAddress);

        // Get all the receiverAddressList
        restReceiverAddressMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(receiverAddress.getId().intValue())))
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
    void getReceiverAddress() throws Exception {
        // Initialize the database
        receiverAddressRepository.saveAndFlush(receiverAddress);

        // Get the receiverAddress
        restReceiverAddressMockMvc
            .perform(get(ENTITY_API_URL_ID, receiverAddress.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(receiverAddress.getId().intValue()))
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
    void getNonExistingReceiverAddress() throws Exception {
        // Get the receiverAddress
        restReceiverAddressMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewReceiverAddress() throws Exception {
        // Initialize the database
        receiverAddressRepository.saveAndFlush(receiverAddress);

        int databaseSizeBeforeUpdate = receiverAddressRepository.findAll().size();

        // Update the receiverAddress
        ReceiverAddress updatedReceiverAddress = receiverAddressRepository.findById(receiverAddress.getId()).get();
        // Disconnect from session so that the updates on updatedReceiverAddress are not directly saved in db
        em.detach(updatedReceiverAddress);
        updatedReceiverAddress
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

        restReceiverAddressMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedReceiverAddress.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedReceiverAddress))
            )
            .andExpect(status().isOk());

        // Validate the ReceiverAddress in the database
        List<ReceiverAddress> receiverAddressList = receiverAddressRepository.findAll();
        assertThat(receiverAddressList).hasSize(databaseSizeBeforeUpdate);
        ReceiverAddress testReceiverAddress = receiverAddressList.get(receiverAddressList.size() - 1);
        assertThat(testReceiverAddress.getCountry()).isEqualTo(UPDATED_COUNTRY);
        assertThat(testReceiverAddress.getGovernate()).isEqualTo(UPDATED_GOVERNATE);
        assertThat(testReceiverAddress.getRegionCity()).isEqualTo(UPDATED_REGION_CITY);
        assertThat(testReceiverAddress.getStreet()).isEqualTo(UPDATED_STREET);
        assertThat(testReceiverAddress.getBuildingNumber()).isEqualTo(UPDATED_BUILDING_NUMBER);
        assertThat(testReceiverAddress.getPostalCode()).isEqualTo(UPDATED_POSTAL_CODE);
        assertThat(testReceiverAddress.getFloor()).isEqualTo(UPDATED_FLOOR);
        assertThat(testReceiverAddress.getRoom()).isEqualTo(UPDATED_ROOM);
        assertThat(testReceiverAddress.getLandmark()).isEqualTo(UPDATED_LANDMARK);
        assertThat(testReceiverAddress.getAdditionalInformation()).isEqualTo(UPDATED_ADDITIONAL_INFORMATION);
    }

    @Test
    @Transactional
    void putNonExistingReceiverAddress() throws Exception {
        int databaseSizeBeforeUpdate = receiverAddressRepository.findAll().size();
        receiverAddress.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restReceiverAddressMockMvc
            .perform(
                put(ENTITY_API_URL_ID, receiverAddress.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(receiverAddress))
            )
            .andExpect(status().isBadRequest());

        // Validate the ReceiverAddress in the database
        List<ReceiverAddress> receiverAddressList = receiverAddressRepository.findAll();
        assertThat(receiverAddressList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchReceiverAddress() throws Exception {
        int databaseSizeBeforeUpdate = receiverAddressRepository.findAll().size();
        receiverAddress.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restReceiverAddressMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(receiverAddress))
            )
            .andExpect(status().isBadRequest());

        // Validate the ReceiverAddress in the database
        List<ReceiverAddress> receiverAddressList = receiverAddressRepository.findAll();
        assertThat(receiverAddressList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamReceiverAddress() throws Exception {
        int databaseSizeBeforeUpdate = receiverAddressRepository.findAll().size();
        receiverAddress.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restReceiverAddressMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(receiverAddress))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ReceiverAddress in the database
        List<ReceiverAddress> receiverAddressList = receiverAddressRepository.findAll();
        assertThat(receiverAddressList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateReceiverAddressWithPatch() throws Exception {
        // Initialize the database
        receiverAddressRepository.saveAndFlush(receiverAddress);

        int databaseSizeBeforeUpdate = receiverAddressRepository.findAll().size();

        // Update the receiverAddress using partial update
        ReceiverAddress partialUpdatedReceiverAddress = new ReceiverAddress();
        partialUpdatedReceiverAddress.setId(receiverAddress.getId());

        partialUpdatedReceiverAddress
            .governate(UPDATED_GOVERNATE)
            .postalCode(UPDATED_POSTAL_CODE)
            .floor(UPDATED_FLOOR)
            .additionalInformation(UPDATED_ADDITIONAL_INFORMATION);

        restReceiverAddressMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedReceiverAddress.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedReceiverAddress))
            )
            .andExpect(status().isOk());

        // Validate the ReceiverAddress in the database
        List<ReceiverAddress> receiverAddressList = receiverAddressRepository.findAll();
        assertThat(receiverAddressList).hasSize(databaseSizeBeforeUpdate);
        ReceiverAddress testReceiverAddress = receiverAddressList.get(receiverAddressList.size() - 1);
        assertThat(testReceiverAddress.getCountry()).isEqualTo(DEFAULT_COUNTRY);
        assertThat(testReceiverAddress.getGovernate()).isEqualTo(UPDATED_GOVERNATE);
        assertThat(testReceiverAddress.getRegionCity()).isEqualTo(DEFAULT_REGION_CITY);
        assertThat(testReceiverAddress.getStreet()).isEqualTo(DEFAULT_STREET);
        assertThat(testReceiverAddress.getBuildingNumber()).isEqualTo(DEFAULT_BUILDING_NUMBER);
        assertThat(testReceiverAddress.getPostalCode()).isEqualTo(UPDATED_POSTAL_CODE);
        assertThat(testReceiverAddress.getFloor()).isEqualTo(UPDATED_FLOOR);
        assertThat(testReceiverAddress.getRoom()).isEqualTo(DEFAULT_ROOM);
        assertThat(testReceiverAddress.getLandmark()).isEqualTo(DEFAULT_LANDMARK);
        assertThat(testReceiverAddress.getAdditionalInformation()).isEqualTo(UPDATED_ADDITIONAL_INFORMATION);
    }

    @Test
    @Transactional
    void fullUpdateReceiverAddressWithPatch() throws Exception {
        // Initialize the database
        receiverAddressRepository.saveAndFlush(receiverAddress);

        int databaseSizeBeforeUpdate = receiverAddressRepository.findAll().size();

        // Update the receiverAddress using partial update
        ReceiverAddress partialUpdatedReceiverAddress = new ReceiverAddress();
        partialUpdatedReceiverAddress.setId(receiverAddress.getId());

        partialUpdatedReceiverAddress
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

        restReceiverAddressMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedReceiverAddress.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedReceiverAddress))
            )
            .andExpect(status().isOk());

        // Validate the ReceiverAddress in the database
        List<ReceiverAddress> receiverAddressList = receiverAddressRepository.findAll();
        assertThat(receiverAddressList).hasSize(databaseSizeBeforeUpdate);
        ReceiverAddress testReceiverAddress = receiverAddressList.get(receiverAddressList.size() - 1);
        assertThat(testReceiverAddress.getCountry()).isEqualTo(UPDATED_COUNTRY);
        assertThat(testReceiverAddress.getGovernate()).isEqualTo(UPDATED_GOVERNATE);
        assertThat(testReceiverAddress.getRegionCity()).isEqualTo(UPDATED_REGION_CITY);
        assertThat(testReceiverAddress.getStreet()).isEqualTo(UPDATED_STREET);
        assertThat(testReceiverAddress.getBuildingNumber()).isEqualTo(UPDATED_BUILDING_NUMBER);
        assertThat(testReceiverAddress.getPostalCode()).isEqualTo(UPDATED_POSTAL_CODE);
        assertThat(testReceiverAddress.getFloor()).isEqualTo(UPDATED_FLOOR);
        assertThat(testReceiverAddress.getRoom()).isEqualTo(UPDATED_ROOM);
        assertThat(testReceiverAddress.getLandmark()).isEqualTo(UPDATED_LANDMARK);
        assertThat(testReceiverAddress.getAdditionalInformation()).isEqualTo(UPDATED_ADDITIONAL_INFORMATION);
    }

    @Test
    @Transactional
    void patchNonExistingReceiverAddress() throws Exception {
        int databaseSizeBeforeUpdate = receiverAddressRepository.findAll().size();
        receiverAddress.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restReceiverAddressMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, receiverAddress.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(receiverAddress))
            )
            .andExpect(status().isBadRequest());

        // Validate the ReceiverAddress in the database
        List<ReceiverAddress> receiverAddressList = receiverAddressRepository.findAll();
        assertThat(receiverAddressList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchReceiverAddress() throws Exception {
        int databaseSizeBeforeUpdate = receiverAddressRepository.findAll().size();
        receiverAddress.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restReceiverAddressMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(receiverAddress))
            )
            .andExpect(status().isBadRequest());

        // Validate the ReceiverAddress in the database
        List<ReceiverAddress> receiverAddressList = receiverAddressRepository.findAll();
        assertThat(receiverAddressList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamReceiverAddress() throws Exception {
        int databaseSizeBeforeUpdate = receiverAddressRepository.findAll().size();
        receiverAddress.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restReceiverAddressMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(receiverAddress))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ReceiverAddress in the database
        List<ReceiverAddress> receiverAddressList = receiverAddressRepository.findAll();
        assertThat(receiverAddressList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteReceiverAddress() throws Exception {
        // Initialize the database
        receiverAddressRepository.saveAndFlush(receiverAddress);

        int databaseSizeBeforeDelete = receiverAddressRepository.findAll().size();

        // Delete the receiverAddress
        restReceiverAddressMockMvc
            .perform(delete(ENTITY_API_URL_ID, receiverAddress.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ReceiverAddress> receiverAddressList = receiverAddressRepository.findAll();
        assertThat(receiverAddressList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
