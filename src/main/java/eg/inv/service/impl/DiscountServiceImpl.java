package eg.inv.service.impl;

import eg.inv.domain.Discount;
import eg.inv.repository.DiscountRepository;
import eg.inv.service.DiscountService;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Discount}.
 */
@Service
@Transactional
public class DiscountServiceImpl implements DiscountService {

    private final Logger log = LoggerFactory.getLogger(DiscountServiceImpl.class);

    private final DiscountRepository discountRepository;

    public DiscountServiceImpl(DiscountRepository discountRepository) {
        this.discountRepository = discountRepository;
    }

    @Override
    public Discount save(Discount discount) {
        log.debug("Request to save Discount : {}", discount);
        return discountRepository.save(discount);
    }

    @Override
    public Optional<Discount> partialUpdate(Discount discount) {
        log.debug("Request to partially update Discount : {}", discount);

        return discountRepository
            .findById(discount.getId())
            .map(existingDiscount -> {
                if (discount.getRate() != null) {
                    existingDiscount.setRate(discount.getRate());
                }
                if (discount.getAmount() != null) {
                    existingDiscount.setAmount(discount.getAmount());
                }

                return existingDiscount;
            })
            .map(discountRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Discount> findAll() {
        log.debug("Request to get all Discounts");
        return discountRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Discount> findOne(Long id) {
        log.debug("Request to get Discount : {}", id);
        return discountRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Discount : {}", id);
        discountRepository.deleteById(id);
    }
}
