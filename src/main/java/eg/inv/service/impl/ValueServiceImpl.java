package eg.inv.service.impl;

import eg.inv.domain.Value;
import eg.inv.repository.ValueRepository;
import eg.inv.service.ValueService;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Value}.
 */
@Service
@Transactional
public class ValueServiceImpl implements ValueService {

    private final Logger log = LoggerFactory.getLogger(ValueServiceImpl.class);

    private final ValueRepository valueRepository;

    public ValueServiceImpl(ValueRepository valueRepository) {
        this.valueRepository = valueRepository;
    }

    @Override
    public Value save(Value value) {
        log.debug("Request to save Value : {}", value);
        return valueRepository.save(value);
    }

    @Override
    public Optional<Value> partialUpdate(Value value) {
        log.debug("Request to partially update Value : {}", value);

        return valueRepository
            .findById(value.getId())
            .map(existingValue -> {
                if (value.getCurrencySold() != null) {
                    existingValue.setCurrencySold(value.getCurrencySold());
                }
                if (value.getAmountEGP() != null) {
                    existingValue.setAmountEGP(value.getAmountEGP());
                }
                if (value.getAmountSold() != null) {
                    existingValue.setAmountSold(value.getAmountSold());
                }
                if (value.getCurrencyExchangeRate() != null) {
                    existingValue.setCurrencyExchangeRate(value.getCurrencyExchangeRate());
                }

                return existingValue;
            })
            .map(valueRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Value> findAll() {
        log.debug("Request to get all Values");
        return valueRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Value> findOne(Long id) {
        log.debug("Request to get Value : {}", id);
        return valueRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Value : {}", id);
        valueRepository.deleteById(id);
    }
}
