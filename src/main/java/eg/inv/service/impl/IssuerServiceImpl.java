package eg.inv.service.impl;

import eg.inv.domain.Issuer;
import eg.inv.repository.IssuerRepository;
import eg.inv.service.IssuerService;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Issuer}.
 */
@Service
@Transactional
public class IssuerServiceImpl implements IssuerService {

    private final Logger log = LoggerFactory.getLogger(IssuerServiceImpl.class);

    private final IssuerRepository issuerRepository;

    public IssuerServiceImpl(IssuerRepository issuerRepository) {
        this.issuerRepository = issuerRepository;
    }

    @Override
    public Issuer save(Issuer issuer) {
        log.debug("Request to save Issuer : {}", issuer);
        return issuerRepository.save(issuer);
    }

    @Override
    public Optional<Issuer> partialUpdate(Issuer issuer) {
        log.debug("Request to partially update Issuer : {}", issuer);

        return issuerRepository
            .findById(issuer.getId())
            .map(existingIssuer -> {
                if (issuer.getIssuertype() != null) {
                    existingIssuer.setIssuertype(issuer.getIssuertype());
                }
                if (issuer.getName() != null) {
                    existingIssuer.setName(issuer.getName());
                }

                return existingIssuer;
            })
            .map(issuerRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Issuer> findAll() {
        log.debug("Request to get all Issuers");
        return issuerRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Issuer> findOne(Long id) {
        log.debug("Request to get Issuer : {}", id);
        return issuerRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Issuer : {}", id);
        issuerRepository.deleteById(id);
    }
}
