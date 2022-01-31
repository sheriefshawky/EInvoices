package eg.inv.service.impl;

import eg.inv.domain.ReceiverAddress;
import eg.inv.repository.ReceiverAddressRepository;
import eg.inv.service.ReceiverAddressService;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link ReceiverAddress}.
 */
@Service
@Transactional
public class ReceiverAddressServiceImpl implements ReceiverAddressService {

    private final Logger log = LoggerFactory.getLogger(ReceiverAddressServiceImpl.class);

    private final ReceiverAddressRepository receiverAddressRepository;

    public ReceiverAddressServiceImpl(ReceiverAddressRepository receiverAddressRepository) {
        this.receiverAddressRepository = receiverAddressRepository;
    }

    @Override
    public ReceiverAddress save(ReceiverAddress receiverAddress) {
        log.debug("Request to save ReceiverAddress : {}", receiverAddress);
        return receiverAddressRepository.save(receiverAddress);
    }

    @Override
    public Optional<ReceiverAddress> partialUpdate(ReceiverAddress receiverAddress) {
        log.debug("Request to partially update ReceiverAddress : {}", receiverAddress);

        return receiverAddressRepository
            .findById(receiverAddress.getId())
            .map(existingReceiverAddress -> {
                if (receiverAddress.getCountry() != null) {
                    existingReceiverAddress.setCountry(receiverAddress.getCountry());
                }
                if (receiverAddress.getGovernate() != null) {
                    existingReceiverAddress.setGovernate(receiverAddress.getGovernate());
                }
                if (receiverAddress.getRegionCity() != null) {
                    existingReceiverAddress.setRegionCity(receiverAddress.getRegionCity());
                }
                if (receiverAddress.getStreet() != null) {
                    existingReceiverAddress.setStreet(receiverAddress.getStreet());
                }
                if (receiverAddress.getBuildingNumber() != null) {
                    existingReceiverAddress.setBuildingNumber(receiverAddress.getBuildingNumber());
                }
                if (receiverAddress.getPostalCode() != null) {
                    existingReceiverAddress.setPostalCode(receiverAddress.getPostalCode());
                }
                if (receiverAddress.getFloor() != null) {
                    existingReceiverAddress.setFloor(receiverAddress.getFloor());
                }
                if (receiverAddress.getRoom() != null) {
                    existingReceiverAddress.setRoom(receiverAddress.getRoom());
                }
                if (receiverAddress.getLandmark() != null) {
                    existingReceiverAddress.setLandmark(receiverAddress.getLandmark());
                }
                if (receiverAddress.getAdditionalInformation() != null) {
                    existingReceiverAddress.setAdditionalInformation(receiverAddress.getAdditionalInformation());
                }

                return existingReceiverAddress;
            })
            .map(receiverAddressRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ReceiverAddress> findAll() {
        log.debug("Request to get all ReceiverAddresses");
        return receiverAddressRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ReceiverAddress> findOne(Long id) {
        log.debug("Request to get ReceiverAddress : {}", id);
        return receiverAddressRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete ReceiverAddress : {}", id);
        receiverAddressRepository.deleteById(id);
    }
}
