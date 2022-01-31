package eg.inv.domain;

import static org.assertj.core.api.Assertions.assertThat;

import eg.inv.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ReceiverAddressTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ReceiverAddress.class);
        ReceiverAddress receiverAddress1 = new ReceiverAddress();
        receiverAddress1.setId(1L);
        ReceiverAddress receiverAddress2 = new ReceiverAddress();
        receiverAddress2.setId(receiverAddress1.getId());
        assertThat(receiverAddress1).isEqualTo(receiverAddress2);
        receiverAddress2.setId(2L);
        assertThat(receiverAddress1).isNotEqualTo(receiverAddress2);
        receiverAddress1.setId(null);
        assertThat(receiverAddress1).isNotEqualTo(receiverAddress2);
    }
}
