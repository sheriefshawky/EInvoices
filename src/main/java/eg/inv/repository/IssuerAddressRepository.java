package eg.inv.repository;

import eg.inv.domain.IssuerAddress;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the IssuerAddress entity.
 */
@SuppressWarnings("unused")
@Repository
public interface IssuerAddressRepository extends JpaRepository<IssuerAddress, Long> {}
