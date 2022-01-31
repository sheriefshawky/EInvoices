package eg.inv.repository;

import eg.inv.domain.TaxableItem;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the TaxableItem entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TaxableItemRepository extends JpaRepository<TaxableItem, Long> {}
