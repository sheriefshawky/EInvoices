package eg.inv.domain;

import static org.assertj.core.api.Assertions.assertThat;

import eg.inv.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class DocumentTypeVersionTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DocumentTypeVersion.class);
        DocumentTypeVersion documentTypeVersion1 = new DocumentTypeVersion();
        documentTypeVersion1.setId(1L);
        DocumentTypeVersion documentTypeVersion2 = new DocumentTypeVersion();
        documentTypeVersion2.setId(documentTypeVersion1.getId());
        assertThat(documentTypeVersion1).isEqualTo(documentTypeVersion2);
        documentTypeVersion2.setId(2L);
        assertThat(documentTypeVersion1).isNotEqualTo(documentTypeVersion2);
        documentTypeVersion1.setId(null);
        assertThat(documentTypeVersion1).isNotEqualTo(documentTypeVersion2);
    }
}
