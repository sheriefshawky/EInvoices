package eg.inv.domain;

import static org.assertj.core.api.Assertions.assertThat;

import eg.inv.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TaxableItemTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TaxableItem.class);
        TaxableItem taxableItem1 = new TaxableItem();
        taxableItem1.setId(1L);
        TaxableItem taxableItem2 = new TaxableItem();
        taxableItem2.setId(taxableItem1.getId());
        assertThat(taxableItem1).isEqualTo(taxableItem2);
        taxableItem2.setId(2L);
        assertThat(taxableItem1).isNotEqualTo(taxableItem2);
        taxableItem1.setId(null);
        assertThat(taxableItem1).isNotEqualTo(taxableItem2);
    }
}
