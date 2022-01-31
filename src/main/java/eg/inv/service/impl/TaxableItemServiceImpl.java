package eg.inv.service.impl;

import eg.inv.domain.TaxableItem;
import eg.inv.repository.TaxableItemRepository;
import eg.inv.service.TaxableItemService;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link TaxableItem}.
 */
@Service
@Transactional
public class TaxableItemServiceImpl implements TaxableItemService {

    private final Logger log = LoggerFactory.getLogger(TaxableItemServiceImpl.class);

    private final TaxableItemRepository taxableItemRepository;

    public TaxableItemServiceImpl(TaxableItemRepository taxableItemRepository) {
        this.taxableItemRepository = taxableItemRepository;
    }

    @Override
    public TaxableItem save(TaxableItem taxableItem) {
        log.debug("Request to save TaxableItem : {}", taxableItem);
        return taxableItemRepository.save(taxableItem);
    }

    @Override
    public Optional<TaxableItem> partialUpdate(TaxableItem taxableItem) {
        log.debug("Request to partially update TaxableItem : {}", taxableItem);

        return taxableItemRepository
            .findById(taxableItem.getId())
            .map(existingTaxableItem -> {
                if (taxableItem.getTaxType() != null) {
                    existingTaxableItem.setTaxType(taxableItem.getTaxType());
                }
                if (taxableItem.getAmount() != null) {
                    existingTaxableItem.setAmount(taxableItem.getAmount());
                }
                if (taxableItem.getSubType() != null) {
                    existingTaxableItem.setSubType(taxableItem.getSubType());
                }
                if (taxableItem.getRate() != null) {
                    existingTaxableItem.setRate(taxableItem.getRate());
                }

                return existingTaxableItem;
            })
            .map(taxableItemRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TaxableItem> findAll() {
        log.debug("Request to get all TaxableItems");
        return taxableItemRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<TaxableItem> findOne(Long id) {
        log.debug("Request to get TaxableItem : {}", id);
        return taxableItemRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete TaxableItem : {}", id);
        taxableItemRepository.deleteById(id);
    }
}
