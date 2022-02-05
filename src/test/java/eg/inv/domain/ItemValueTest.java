package eg.inv.domain;

import static org.assertj.core.api.Assertions.assertThat;

import eg.inv.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ItemValueTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ItemValue.class);
        ItemValue itemValue1 = new ItemValue();
        itemValue1.setId(1L);
        ItemValue itemValue2 = new ItemValue();
        itemValue2.setId(itemValue1.getId());
        assertThat(itemValue1).isEqualTo(itemValue2);
        itemValue2.setId(2L);
        assertThat(itemValue1).isNotEqualTo(itemValue2);
        itemValue1.setId(null);
        assertThat(itemValue1).isNotEqualTo(itemValue2);
    }
}
