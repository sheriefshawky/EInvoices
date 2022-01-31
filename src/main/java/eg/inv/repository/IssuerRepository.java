package eg.inv.repository;

import eg.inv.domain.Issuer;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Issuer entity.
 */
@SuppressWarnings("unused")
@Repository
public interface IssuerRepository extends JpaRepository<Issuer, Long> {}
