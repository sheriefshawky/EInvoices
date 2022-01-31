package eg.inv.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A TaxableItem.
 */
@Entity
@Table(name = "taxable_item")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class TaxableItem implements Serializable {

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

    @Column(name = "sub_type")
    private String subType;

    @Column(name = "rate", precision = 21, scale = 2)
    private BigDecimal rate;

    @ManyToOne
    @JsonIgnoreProperties(value = { "unitValue", "discount", "taxableItems", "document" }, allowSetters = true)
    private InvoiceLine invoiceLine;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public TaxableItem id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTaxType() {
        return this.taxType;
    }

    public TaxableItem taxType(String taxType) {
        this.setTaxType(taxType);
        return this;
    }

    public void setTaxType(String taxType) {
        this.taxType = taxType;
    }

    public BigDecimal getAmount() {
        return this.amount;
    }

    public TaxableItem amount(BigDecimal amount) {
        this.setAmount(amount);
        return this;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getSubType() {
        return this.subType;
    }

    public TaxableItem subType(String subType) {
        this.setSubType(subType);
        return this;
    }

    public void setSubType(String subType) {
        this.subType = subType;
    }

    public BigDecimal getRate() {
        return this.rate;
    }

    public TaxableItem rate(BigDecimal rate) {
        this.setRate(rate);
        return this;
    }

    public void setRate(BigDecimal rate) {
        this.rate = rate;
    }

    public InvoiceLine getInvoiceLine() {
        return this.invoiceLine;
    }

    public void setInvoiceLine(InvoiceLine invoiceLine) {
        this.invoiceLine = invoiceLine;
    }

    public TaxableItem invoiceLine(InvoiceLine invoiceLine) {
        this.setInvoiceLine(invoiceLine);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TaxableItem)) {
            return false;
        }
        return id != null && id.equals(((TaxableItem) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TaxableItem{" +
            "id=" + getId() +
            ", taxType='" + getTaxType() + "'" +
            ", amount=" + getAmount() +
            ", subType='" + getSubType() + "'" +
            ", rate=" + getRate() +
            "}";
    }
}
