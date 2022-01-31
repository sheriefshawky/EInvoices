package eg.inv.domain;

import static org.assertj.core.api.Assertions.assertThat;

import eg.inv.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ValueTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Value.class);
        Value value1 = new Value();
        value1.setId(1L);
        Value value2 = new Value();
        value2.setId(value1.getId());
        assertThat(value1).isEqualTo(value2);
        value2.setId(2L);
        assertThat(value1).isNotEqualTo(value2);
        value1.setId(null);
        assertThat(value1).isNotEqualTo(value2);
    }
}
