package eg.inv.repository;

import eg.inv.domain.Signature;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Signature entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SignatureRepository extends JpaRepository<Signature, Long> {}
