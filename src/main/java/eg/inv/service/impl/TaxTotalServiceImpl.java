package eg.inv.service.impl;

import eg.inv.domain.TaxTotal;
import eg.inv.repository.TaxTotalRepository;
import eg.inv.service.TaxTotalService;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link TaxTotal}.
 */
@Service
@Transactional
public class TaxTotalServiceImpl implements TaxTotalService {

    private final Logger log = LoggerFactory.getLogger(TaxTotalServiceImpl.class);

    private final TaxTotalRepository taxTotalRepository;

    public TaxTotalServiceImpl(TaxTotalRepository taxTotalRepository) {
        this.taxTotalRepository = taxTotalRepository;
    }

    @Override
    public TaxTotal save(TaxTotal taxTotal) {
        log.debug("Request to save TaxTotal : {}", taxTotal);
        return taxTotalRepository.save(taxTotal);
    }

    @Override
    public Optional<TaxTotal> partialUpdate(TaxTotal taxTotal) {
        log.debug("Request to partially update TaxTotal : {}", taxTotal);

        return taxTotalRepository
            .findById(taxTotal.getId())
            .map(existingTaxTotal -> {
                if (taxTotal.getTaxType() != null) {
                    existingTaxTotal.setTaxType(taxTotal.getTaxType());
                }
                if (taxTotal.getAmount() != null) {
                    existingTaxTotal.setAmount(taxTotal.getAmount());
                }

                return existingTaxTotal;
            })
            .map(taxTotalRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TaxTotal> findAll() {
        log.debug("Request to get all TaxTotals");
        return taxTotalRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<TaxTotal> findOne(Long id) {
        log.debug("Request to get TaxTotal : {}", id);
        return taxTotalRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete TaxTotal : {}", id);
        taxTotalRepository.deleteById(id);
    }
}
