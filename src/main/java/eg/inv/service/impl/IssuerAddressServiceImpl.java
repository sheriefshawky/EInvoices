package eg.inv.service.impl;

import eg.inv.domain.IssuerAddress;
import eg.inv.repository.IssuerAddressRepository;
import eg.inv.service.IssuerAddressService;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link IssuerAddress}.
 */
@Service
@Transactional
public class IssuerAddressServiceImpl implements IssuerAddressService {

    private final Logger log = LoggerFactory.getLogger(IssuerAddressServiceImpl.class);

    private final IssuerAddressRepository issuerAddressRepository;

    public IssuerAddressServiceImpl(IssuerAddressRepository issuerAddressRepository) {
        this.issuerAddressRepository = issuerAddressRepository;
    }

    @Override
    public IssuerAddress save(IssuerAddress issuerAddress) {
        log.debug("Request to save IssuerAddress : {}", issuerAddress);
        return issuerAddressRepository.save(issuerAddress);
    }

    @Override
    public Optional<IssuerAddress> partialUpdate(IssuerAddress issuerAddress) {
        log.debug("Request to partially update IssuerAddress : {}", issuerAddress);

        return issuerAddressRepository
            .findById(issuerAddress.getId())
            .map(existingIssuerAddress -> {
                if (issuerAddress.getBranchId() != null) {
                    existingIssuerAddress.setBranchId(issuerAddress.getBranchId());
                }
                if (issuerAddress.getCountry() != null) {
                    existingIssuerAddress.setCountry(issuerAddress.getCountry());
                }
                if (issuerAddress.getGovernate() != null) {
                    existingIssuerAddress.setGovernate(issuerAddress.getGovernate());
                }
                if (issuerAddress.getRegionCity() != null) {
                    existingIssuerAddress.setRegionCity(issuerAddress.getRegionCity());
                }
                if (issuerAddress.getStreet() != null) {
                    existingIssuerAddress.setStreet(issuerAddress.getStreet());
                }
                if (issuerAddress.getBuildingNumber() != null) {
                    existingIssuerAddress.setBuildingNumber(issuerAddress.getBuildingNumber());
                }
                if (issuerAddress.getPostalCode() != null) {
                    existingIssuerAddress.setPostalCode(issuerAddress.getPostalCode());
                }
                if (issuerAddress.getFloor() != null) {
                    existingIssuerAddress.setFloor(issuerAddress.getFloor());
                }
                if (issuerAddress.getRoom() != null) {
                    existingIssuerAddress.setRoom(issuerAddress.getRoom());
                }
                if (issuerAddress.getLandmark() != null) {
                    existingIssuerAddress.setLandmark(issuerAddress.getLandmark());
                }
                if (issuerAddress.getAdditionalInformation() != null) {
                    existingIssuerAddress.setAdditionalInformation(issuerAddress.getAdditionalInformation());
                }

                return existingIssuerAddress;
            })
            .map(issuerAddressRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<IssuerAddress> findAll() {
        log.debug("Request to get all IssuerAddresses");
        return issuerAddressRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<IssuerAddress> findOne(Long id) {
        log.debug("Request to get IssuerAddress : {}", id);
        return issuerAddressRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete IssuerAddress : {}", id);
        issuerAddressRepository.deleteById(id);
    }
}
