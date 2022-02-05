package eg.inv.repository;

import eg.inv.domain.ItemValue;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the ItemValue entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ItemValueRepository extends JpaRepository<ItemValue, Long> {}
