package eg.inv.repository;

import eg.inv.domain.Value;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Value entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ValueRepository extends JpaRepository<Value, Long> {}
