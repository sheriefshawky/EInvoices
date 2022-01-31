package eg.inv.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A TaxTotal.
 */
@Entity
@Table(name = "tax_total")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class TaxTotal implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "tax_type")
    private String taxType;

    @Column(name = "amount", precision = 21, scale = 2)
    private BigDecimal amount;

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

    public TaxTotal id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTaxType() {
        return this.taxType;
    }

    public TaxTotal taxType(String taxType) {
        this.setTaxType(taxType);
        return this;
    }

    public void setTaxType(String taxType) {
        this.taxType = taxType;
    }

    public BigDecimal getAmount() {
        return this.amount;
    }

    public TaxTotal amount(BigDecimal amount) {
        this.setAmount(amount);
        return this;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Document getDocument() {
        return this.document;
    }

    public void setDocument(Document document) {
        this.document = document;
    }

    public TaxTotal document(Document document) {
        this.setDocument(document);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TaxTotal)) {
            return false;
        }
        return id != null && id.equals(((TaxTotal) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TaxTotal{" +
            "id=" + getId() +
            ", taxType='" + getTaxType() + "'" +
            ", amount=" + getAmount() +
            "}";
    }
}
