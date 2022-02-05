package eg.inv.service.impl;

import eg.inv.domain.ItemValue;
import eg.inv.repository.ItemValueRepository;
import eg.inv.service.ItemValueService;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link ItemValue}.
 */
@Service
@Transactional
public class ItemValueServiceImpl implements ItemValueService {

    private final Logger log = LoggerFactory.getLogger(ItemValueServiceImpl.class);

    private final ItemValueRepository itemValueRepository;

    public ItemValueServiceImpl(ItemValueRepository itemValueRepository) {
        this.itemValueRepository = itemValueRepository;
    }

    @Override
    public ItemValue save(ItemValue itemValue) {
        log.debug("Request to save ItemValue : {}", itemValue);
        return itemValueRepository.save(itemValue);
    }

    @Override
    public Optional<ItemValue> partialUpdate(ItemValue itemValue) {
        log.debug("Request to partially update ItemValue : {}", itemValue);

        return itemValueRepository
            .findById(itemValue.getId())
            .map(existingItemValue -> {
                if (itemValue.getCurrencySold() != null) {
                    existingItemValue.setCurrencySold(itemValue.getCurrencySold());
                }
                if (itemValue.getAmountEGP() != null) {
                    existingItemValue.setAmountEGP(itemValue.getAmountEGP());
                }
                if (itemValue.getAmountSold() != null) {
                    existingItemValue.setAmountSold(itemValue.getAmountSold());
                }
                if (itemValue.getCurrencyExchangeRate() != null) {
                    existingItemValue.setCurrencyExchangeRate(itemValue.getCurrencyExchangeRate());
                }

                return existingItemValue;
            })
            .map(itemValueRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ItemValue> findAll() {
        log.debug("Request to get all ItemValues");
        return itemValueRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ItemValue> findOne(Long id) {
        log.debug("Request to get ItemValue : {}", id);
        return itemValueRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete ItemValue : {}", id);
        itemValueRepository.deleteById(id);
    }
}
