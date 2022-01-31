package eg.inv.domain;

import static org.assertj.core.api.Assertions.assertThat;

import eg.inv.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TaxTotalTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TaxTotal.class);
        TaxTotal taxTotal1 = new TaxTotal();
        taxTotal1.setId(1L);
        TaxTotal taxTotal2 = new TaxTotal();
        taxTotal2.setId(taxTotal1.getId());
        assertThat(taxTotal1).isEqualTo(taxTotal2);
        taxTotal2.setId(2L);
        assertThat(taxTotal1).isNotEqualTo(taxTotal2);
        taxTotal1.setId(null);
        assertThat(taxTotal1).isNotEqualTo(taxTotal2);
    }
}
