package eg.inv.domain;

import static org.assertj.core.api.Assertions.assertThat;

import eg.inv.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class IssuerAddressTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(IssuerAddress.class);
        IssuerAddress issuerAddress1 = new IssuerAddress();
        issuerAddress1.setId(1L);
        IssuerAddress issuerAddress2 = new IssuerAddress();
        issuerAddress2.setId(issuerAddress1.getId());
        assertThat(issuerAddress1).isEqualTo(issuerAddress2);
        issuerAddress2.setId(2L);
        assertThat(issuerAddress1).isNotEqualTo(issuerAddress2);
        issuerAddress1.setId(null);
        assertThat(issuerAddress1).isNotEqualTo(issuerAddress2);
    }
}
