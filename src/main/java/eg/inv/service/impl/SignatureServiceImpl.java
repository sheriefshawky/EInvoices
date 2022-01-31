package eg.inv.service.impl;

import eg.inv.domain.Signature;
import eg.inv.repository.SignatureRepository;
import eg.inv.service.SignatureService;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Signature}.
 */
@Service
@Transactional
public class SignatureServiceImpl implements SignatureService {

    private final Logger log = LoggerFactory.getLogger(SignatureServiceImpl.class);

    private final SignatureRepository signatureRepository;

    public SignatureServiceImpl(SignatureRepository signatureRepository) {
        this.signatureRepository = signatureRepository;
    }

    @Override
    public Signature save(Signature signature) {
        log.debug("Request to save Signature : {}", signature);
        return signatureRepository.save(signature);
    }

    @Override
    public Optional<Signature> partialUpdate(Signature signature) {
        log.debug("Request to partially update Signature : {}", signature);

        return signatureRepository
            .findById(signature.getId())
            .map(existingSignature -> {
                if (signature.getType() != null) {
                    existingSignature.setType(signature.getType());
                }
                if (signature.getValue() != null) {
                    existingSignature.setValue(signature.getValue());
                }

                return existingSignature;
            })
            .map(signatureRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Signature> findAll() {
        log.debug("Request to get all Signatures");
        return signatureRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Signature> findOne(Long id) {
        log.debug("Request to get Signature : {}", id);
        return signatureRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Signature : {}", id);
        signatureRepository.deleteById(id);
    }
}
