package eg.inv.service.impl;

import eg.inv.domain.Delivery;
import eg.inv.repository.DeliveryRepository;
import eg.inv.service.DeliveryService;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Delivery}.
 */
@Service
@Transactional
public class DeliveryServiceImpl implements DeliveryService {

    private final Logger log = LoggerFactory.getLogger(DeliveryServiceImpl.class);

    private final DeliveryRepository deliveryRepository;

    public DeliveryServiceImpl(DeliveryRepository deliveryRepository) {
        this.deliveryRepository = deliveryRepository;
    }

    @Override
    public Delivery save(Delivery delivery) {
        log.debug("Request to save Delivery : {}", delivery);
        return deliveryRepository.save(delivery);
    }

    @Override
    public Optional<Delivery> partialUpdate(Delivery delivery) {
        log.debug("Request to partially update Delivery : {}", delivery);

        return deliveryRepository
            .findById(delivery.getId())
            .map(existingDelivery -> {
                if (delivery.getApproach() != null) {
                    existingDelivery.setApproach(delivery.getApproach());
                }
                if (delivery.getPackaging() != null) {
                    existingDelivery.setPackaging(delivery.getPackaging());
                }
                if (delivery.getDateValidity() != null) {
                    existingDelivery.setDateValidity(delivery.getDateValidity());
                }
                if (delivery.getExportPort() != null) {
                    existingDelivery.setExportPort(delivery.getExportPort());
                }
                if (delivery.getCountryOfOrigin() != null) {
                    existingDelivery.setCountryOfOrigin(delivery.getCountryOfOrigin());
                }
                if (delivery.getGrossWeight() != null) {
                    existingDelivery.setGrossWeight(delivery.getGrossWeight());
                }
                if (delivery.getNetWeight() != null) {
                    existingDelivery.setNetWeight(delivery.getNetWeight());
                }
                if (delivery.getTerms() != null) {
                    existingDelivery.setTerms(delivery.getTerms());
                }

                return existingDelivery;
            })
            .map(deliveryRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Delivery> findAll() {
        log.debug("Request to get all Deliveries");
        return deliveryRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Delivery> findOne(Long id) {
        log.debug("Request to get Delivery : {}", id);
        return deliveryRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Delivery : {}", id);
        deliveryRepository.deleteById(id);
    }
}
