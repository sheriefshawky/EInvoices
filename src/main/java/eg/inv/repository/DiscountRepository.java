package eg.inv.repository;

import eg.inv.domain.Discount;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Discount entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DiscountRepository extends JpaRepository<Discount, Long> {}
