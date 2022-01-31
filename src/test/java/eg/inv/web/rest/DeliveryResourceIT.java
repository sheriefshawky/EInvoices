package eg.inv.web.rest;

import static eg.inv.web.rest.TestUtil.sameNumber;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import eg.inv.IntegrationTest;
import eg.inv.domain.Delivery;
import eg.inv.repository.DeliveryRepository;
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
 * Integration tests for the {@link DeliveryResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class DeliveryResourceIT {

    private static final String DEFAULT_APPROACH = "AAAAAAAAAA";
    private static final String UPDATED_APPROACH = "BBBBBBBBBB";

    private static final String DEFAULT_PACKAGING = "AAAAAAAAAA";
    private static final String UPDATED_PACKAGING = "BBBBBBBBBB";

    private static final String DEFAULT_DATE_VALIDITY = "AAAAAAAAAA";
    private static final String UPDATED_DATE_VALIDITY = "BBBBBBBBBB";

    private static final String DEFAULT_EXPORT_PORT = "AAAAAAAAAA";
    private static final String UPDATED_EXPORT_PORT = "BBBBBBBBBB";

    private static final String DEFAULT_COUNTRY_OF_ORIGIN = "AAAAAAAAAA";
    private static final String UPDATED_COUNTRY_OF_ORIGIN = "BBBBBBBBBB";

    private static final String DEFAULT_GROSS_WEIGHT = "AAAAAAAAAA";
    private static final String UPDATED_GROSS_WEIGHT = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_NET_WEIGHT = new BigDecimal(1);
    private static final BigDecimal UPDATED_NET_WEIGHT = new BigDecimal(2);

    private static final String DEFAULT_TERMS = "AAAAAAAAAA";
    private static final String UPDATED_TERMS = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/deliveries";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private DeliveryRepository deliveryRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDeliveryMockMvc;

    private Delivery delivery;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Delivery createEntity(EntityManager em) {
        Delivery delivery = new Delivery()
            .approach(DEFAULT_APPROACH)
            .packaging(DEFAULT_PACKAGING)
            .dateValidity(DEFAULT_DATE_VALIDITY)
            .exportPort(DEFAULT_EXPORT_PORT)
            .countryOfOrigin(DEFAULT_COUNTRY_OF_ORIGIN)
            .grossWeight(DEFAULT_GROSS_WEIGHT)
            .netWeight(DEFAULT_NET_WEIGHT)
            .terms(DEFAULT_TERMS);
        return delivery;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Delivery createUpdatedEntity(EntityManager em) {
        Delivery delivery = new Delivery()
            .approach(UPDATED_APPROACH)
            .packaging(UPDATED_PACKAGING)
            .dateValidity(UPDATED_DATE_VALIDITY)
            .exportPort(UPDATED_EXPORT_PORT)
            .countryOfOrigin(UPDATED_COUNTRY_OF_ORIGIN)
            .grossWeight(UPDATED_GROSS_WEIGHT)
            .netWeight(UPDATED_NET_WEIGHT)
            .terms(UPDATED_TERMS);
        return delivery;
    }

    @BeforeEach
    public void initTest() {
        delivery = createEntity(em);
    }

    @Test
    @Transactional
    void createDelivery() throws Exception {
        int databaseSizeBeforeCreate = deliveryRepository.findAll().size();
        // Create the Delivery
        restDeliveryMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(delivery)))
            .andExpect(status().isCreated());

        // Validate the Delivery in the database
        List<Delivery> deliveryList = deliveryRepository.findAll();
        assertThat(deliveryList).hasSize(databaseSizeBeforeCreate + 1);
        Delivery testDelivery = deliveryList.get(deliveryList.size() - 1);
        assertThat(testDelivery.getApproach()).isEqualTo(DEFAULT_APPROACH);
        assertThat(testDelivery.getPackaging()).isEqualTo(DEFAULT_PACKAGING);
        assertThat(testDelivery.getDateValidity()).isEqualTo(DEFAULT_DATE_VALIDITY);
        assertThat(testDelivery.getExportPort()).isEqualTo(DEFAULT_EXPORT_PORT);
        assertThat(testDelivery.getCountryOfOrigin()).isEqualTo(DEFAULT_COUNTRY_OF_ORIGIN);
        assertThat(testDelivery.getGrossWeight()).isEqualTo(DEFAULT_GROSS_WEIGHT);
        assertThat(testDelivery.getNetWeight()).isEqualByComparingTo(DEFAULT_NET_WEIGHT);
        assertThat(testDelivery.getTerms()).isEqualTo(DEFAULT_TERMS);
    }

    @Test
    @Transactional
    void createDeliveryWithExistingId() throws Exception {
        // Create the Delivery with an existing ID
        delivery.setId(1L);

        int databaseSizeBeforeCreate = deliveryRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restDeliveryMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(delivery)))
            .andExpect(status().isBadRequest());

        // Validate the Delivery in the database
        List<Delivery> deliveryList = deliveryRepository.findAll();
        assertThat(deliveryList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllDeliveries() throws Exception {
        // Initialize the database
        deliveryRepository.saveAndFlush(delivery);

        // Get all the deliveryList
        restDeliveryMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(delivery.getId().intValue())))
            .andExpect(jsonPath("$.[*].approach").value(hasItem(DEFAULT_APPROACH)))
            .andExpect(jsonPath("$.[*].packaging").value(hasItem(DEFAULT_PACKAGING)))
            .andExpect(jsonPath("$.[*].dateValidity").value(hasItem(DEFAULT_DATE_VALIDITY)))
            .andExpect(jsonPath("$.[*].exportPort").value(hasItem(DEFAULT_EXPORT_PORT)))
            .andExpect(jsonPath("$.[*].countryOfOrigin").value(hasItem(DEFAULT_COUNTRY_OF_ORIGIN)))
            .andExpect(jsonPath("$.[*].grossWeight").value(hasItem(DEFAULT_GROSS_WEIGHT)))
            .andExpect(jsonPath("$.[*].netWeight").value(hasItem(sameNumber(DEFAULT_NET_WEIGHT))))
            .andExpect(jsonPath("$.[*].terms").value(hasItem(DEFAULT_TERMS)));
    }

    @Test
    @Transactional
    void getDelivery() throws Exception {
        // Initialize the database
        deliveryRepository.saveAndFlush(delivery);

        // Get the delivery
        restDeliveryMockMvc
            .perform(get(ENTITY_API_URL_ID, delivery.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(delivery.getId().intValue()))
            .andExpect(jsonPath("$.approach").value(DEFAULT_APPROACH))
            .andExpect(jsonPath("$.packaging").value(DEFAULT_PACKAGING))
            .andExpect(jsonPath("$.dateValidity").value(DEFAULT_DATE_VALIDITY))
            .andExpect(jsonPath("$.exportPort").value(DEFAULT_EXPORT_PORT))
            .andExpect(jsonPath("$.countryOfOrigin").value(DEFAULT_COUNTRY_OF_ORIGIN))
            .andExpect(jsonPath("$.grossWeight").value(DEFAULT_GROSS_WEIGHT))
            .andExpect(jsonPath("$.netWeight").value(sameNumber(DEFAULT_NET_WEIGHT)))
            .andExpect(jsonPath("$.terms").value(DEFAULT_TERMS));
    }

    @Test
    @Transactional
    void getNonExistingDelivery() throws Exception {
        // Get the delivery
        restDeliveryMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewDelivery() throws Exception {
        // Initialize the database
        deliveryRepository.saveAndFlush(delivery);

        int databaseSizeBeforeUpdate = deliveryRepository.findAll().size();

        // Update the delivery
        Delivery updatedDelivery = deliveryRepository.findById(delivery.getId()).get();
        // Disconnect from session so that the updates on updatedDelivery are not directly saved in db
        em.detach(updatedDelivery);
        updatedDelivery
            .approach(UPDATED_APPROACH)
            .packaging(UPDATED_PACKAGING)
            .dateValidity(UPDATED_DATE_VALIDITY)
            .exportPort(UPDATED_EXPORT_PORT)
            .countryOfOrigin(UPDATED_COUNTRY_OF_ORIGIN)
            .grossWeight(UPDATED_GROSS_WEIGHT)
            .netWeight(UPDATED_NET_WEIGHT)
            .terms(UPDATED_TERMS);

        restDeliveryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedDelivery.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedDelivery))
            )
            .andExpect(status().isOk());

        // Validate the Delivery in the database
        List<Delivery> deliveryList = deliveryRepository.findAll();
        assertThat(deliveryList).hasSize(databaseSizeBeforeUpdate);
        Delivery testDelivery = deliveryList.get(deliveryList.size() - 1);
        assertThat(testDelivery.getApproach()).isEqualTo(UPDATED_APPROACH);
        assertThat(testDelivery.getPackaging()).isEqualTo(UPDATED_PACKAGING);
        assertThat(testDelivery.getDateValidity()).isEqualTo(UPDATED_DATE_VALIDITY);
        assertThat(testDelivery.getExportPort()).isEqualTo(UPDATED_EXPORT_PORT);
        assertThat(testDelivery.getCountryOfOrigin()).isEqualTo(UPDATED_COUNTRY_OF_ORIGIN);
        assertThat(testDelivery.getGrossWeight()).isEqualTo(UPDATED_GROSS_WEIGHT);
        assertThat(testDelivery.getNetWeight()).isEqualByComparingTo(UPDATED_NET_WEIGHT);
        assertThat(testDelivery.getTerms()).isEqualTo(UPDATED_TERMS);
    }

    @Test
    @Transactional
    void putNonExistingDelivery() throws Exception {
        int databaseSizeBeforeUpdate = deliveryRepository.findAll().size();
        delivery.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDeliveryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, delivery.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(delivery))
            )
            .andExpect(status().isBadRequest());

        // Validate the Delivery in the database
        List<Delivery> deliveryList = deliveryRepository.findAll();
        assertThat(deliveryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchDelivery() throws Exception {
        int databaseSizeBeforeUpdate = deliveryRepository.findAll().size();
        delivery.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDeliveryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(delivery))
            )
            .andExpect(status().isBadRequest());

        // Validate the Delivery in the database
        List<Delivery> deliveryList = deliveryRepository.findAll();
        assertThat(deliveryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamDelivery() throws Exception {
        int databaseSizeBeforeUpdate = deliveryRepository.findAll().size();
        delivery.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDeliveryMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(delivery)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Delivery in the database
        List<Delivery> deliveryList = deliveryRepository.findAll();
        assertThat(deliveryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateDeliveryWithPatch() throws Exception {
        // Initialize the database
        deliveryRepository.saveAndFlush(delivery);

        int databaseSizeBeforeUpdate = deliveryRepository.findAll().size();

        // Update the delivery using partial update
        Delivery partialUpdatedDelivery = new Delivery();
        partialUpdatedDelivery.setId(delivery.getId());

        partialUpdatedDelivery
            .approach(UPDATED_APPROACH)
            .exportPort(UPDATED_EXPORT_PORT)
            .countryOfOrigin(UPDATED_COUNTRY_OF_ORIGIN)
            .grossWeight(UPDATED_GROSS_WEIGHT)
            .netWeight(UPDATED_NET_WEIGHT);

        restDeliveryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDelivery.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDelivery))
            )
            .andExpect(status().isOk());

        // Validate the Delivery in the database
        List<Delivery> deliveryList = deliveryRepository.findAll();
        assertThat(deliveryList).hasSize(databaseSizeBeforeUpdate);
        Delivery testDelivery = deliveryList.get(deliveryList.size() - 1);
        assertThat(testDelivery.getApproach()).isEqualTo(UPDATED_APPROACH);
        assertThat(testDelivery.getPackaging()).isEqualTo(DEFAULT_PACKAGING);
        assertThat(testDelivery.getDateValidity()).isEqualTo(DEFAULT_DATE_VALIDITY);
        assertThat(testDelivery.getExportPort()).isEqualTo(UPDATED_EXPORT_PORT);
        assertThat(testDelivery.getCountryOfOrigin()).isEqualTo(UPDATED_COUNTRY_OF_ORIGIN);
        assertThat(testDelivery.getGrossWeight()).isEqualTo(UPDATED_GROSS_WEIGHT);
        assertThat(testDelivery.getNetWeight()).isEqualByComparingTo(UPDATED_NET_WEIGHT);
        assertThat(testDelivery.getTerms()).isEqualTo(DEFAULT_TERMS);
    }

    @Test
    @Transactional
    void fullUpdateDeliveryWithPatch() throws Exception {
        // Initialize the database
        deliveryRepository.saveAndFlush(delivery);

        int databaseSizeBeforeUpdate = deliveryRepository.findAll().size();

        // Update the delivery using partial update
        Delivery partialUpdatedDelivery = new Delivery();
        partialUpdatedDelivery.setId(delivery.getId());

        partialUpdatedDelivery
            .approach(UPDATED_APPROACH)
            .packaging(UPDATED_PACKAGING)
            .dateValidity(UPDATED_DATE_VALIDITY)
            .exportPort(UPDATED_EXPORT_PORT)
            .countryOfOrigin(UPDATED_COUNTRY_OF_ORIGIN)
            .grossWeight(UPDATED_GROSS_WEIGHT)
            .netWeight(UPDATED_NET_WEIGHT)
            .terms(UPDATED_TERMS);

        restDeliveryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDelivery.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDelivery))
            )
            .andExpect(status().isOk());

        // Validate the Delivery in the database
        List<Delivery> deliveryList = deliveryRepository.findAll();
        assertThat(deliveryList).hasSize(databaseSizeBeforeUpdate);
        Delivery testDelivery = deliveryList.get(deliveryList.size() - 1);
        assertThat(testDelivery.getApproach()).isEqualTo(UPDATED_APPROACH);
        assertThat(testDelivery.getPackaging()).isEqualTo(UPDATED_PACKAGING);
        assertThat(testDelivery.getDateValidity()).isEqualTo(UPDATED_DATE_VALIDITY);
        assertThat(testDelivery.getExportPort()).isEqualTo(UPDATED_EXPORT_PORT);
        assertThat(testDelivery.getCountryOfOrigin()).isEqualTo(UPDATED_COUNTRY_OF_ORIGIN);
        assertThat(testDelivery.getGrossWeight()).isEqualTo(UPDATED_GROSS_WEIGHT);
        assertThat(testDelivery.getNetWeight()).isEqualByComparingTo(UPDATED_NET_WEIGHT);
        assertThat(testDelivery.getTerms()).isEqualTo(UPDATED_TERMS);
    }

    @Test
    @Transactional
    void patchNonExistingDelivery() throws Exception {
        int databaseSizeBeforeUpdate = deliveryRepository.findAll().size();
        delivery.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDeliveryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, delivery.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(delivery))
            )
            .andExpect(status().isBadRequest());

        // Validate the Delivery in the database
        List<Delivery> deliveryList = deliveryRepository.findAll();
        assertThat(deliveryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchDelivery() throws Exception {
        int databaseSizeBeforeUpdate = deliveryRepository.findAll().size();
        delivery.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDeliveryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(delivery))
            )
            .andExpect(status().isBadRequest());

        // Validate the Delivery in the database
        List<Delivery> deliveryList = deliveryRepository.findAll();
        assertThat(deliveryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamDelivery() throws Exception {
        int databaseSizeBeforeUpdate = deliveryRepository.findAll().size();
        delivery.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDeliveryMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(delivery)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Delivery in the database
        List<Delivery> deliveryList = deliveryRepository.findAll();
        assertThat(deliveryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteDelivery() throws Exception {
        // Initialize the database
        deliveryRepository.saveAndFlush(delivery);

        int databaseSizeBeforeDelete = deliveryRepository.findAll().size();

        // Delete the delivery
        restDeliveryMockMvc
            .perform(delete(ENTITY_API_URL_ID, delivery.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Delivery> deliveryList = deliveryRepository.findAll();
        assertThat(deliveryList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
