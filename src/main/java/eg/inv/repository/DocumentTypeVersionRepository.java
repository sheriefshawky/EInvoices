package eg.inv.repository;

import eg.inv.domain.DocumentTypeVersion;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the DocumentTypeVersion entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DocumentTypeVersionRepository extends JpaRepository<DocumentTypeVersion, Long> {}
