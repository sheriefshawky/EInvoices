package eg.inv.repository;

import eg.inv.domain.TaxTotal;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the TaxTotal entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TaxTotalRepository extends JpaRepository<TaxTotal, Long> {}
