package eg.inv.domain;

import static org.assertj.core.api.Assertions.assertThat;

import eg.inv.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ReceiverTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Receiver.class);
        Receiver receiver1 = new Receiver();
        receiver1.setId(1L);
        Receiver receiver2 = new Receiver();
        receiver2.setId(receiver1.getId());
        assertThat(receiver1).isEqualTo(receiver2);
        receiver2.setId(2L);
        assertThat(receiver1).isNotEqualTo(receiver2);
        receiver1.setId(null);
        assertThat(receiver1).isNotEqualTo(receiver2);
    }
}
