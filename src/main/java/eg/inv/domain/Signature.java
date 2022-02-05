package eg.inv.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Signature.
 */
@Entity
@Table(name = "signature")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Signature implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "type")
    private String type;

    @Column(name = "sig_value")
    private String sigValue;

    @ManyToOne
    @JsonIgnoreProperties(
        value = { "issuer", "receiver", "payment", "delivery", "invoiceLines", "taxTotals", "signatures" },
        allowSetters = true
    )
    private Document document;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Signature id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return this.type;
    }

    public Signature type(String type) {
        this.setType(type);
        return this;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSigValue() {
        return this.sigValue;
    }

    public Signature sigValue(String sigValue) {
        this.setSigValue(sigValue);
        return this;
    }

    public void setSigValue(String sigValue) {
        this.sigValue = sigValue;
    }

    public Document getDocument() {
        return this.document;
    }

    public void setDocument(Document document) {
        this.document = document;
    }

    public Signature document(Document document) {
        this.setDocument(document);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Signature)) {
            return false;
        }
        return id != null && id.equals(((Signature) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Signature{" +
            "id=" + getId() +
            ", type='" + getType() + "'" +
            ", sigValue='" + getSigValue() + "'" +
            "}";
    }
}
