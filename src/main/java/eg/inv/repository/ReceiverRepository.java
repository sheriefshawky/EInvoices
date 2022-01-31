package eg.inv.repository;

import eg.inv.domain.Receiver;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Receiver entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ReceiverRepository extends JpaRepository<Receiver, Long> {}
