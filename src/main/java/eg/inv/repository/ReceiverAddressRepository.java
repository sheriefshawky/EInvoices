package eg.inv.repository;

import eg.inv.domain.ReceiverAddress;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the ReceiverAddress entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ReceiverAddressRepository extends JpaRepository<ReceiverAddress, Long> {}
