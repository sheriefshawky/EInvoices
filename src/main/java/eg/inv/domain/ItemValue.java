package eg.inv.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A ItemValue.
 */
@Entity
@Table(name = "item_value")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ItemValue implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "currency_sold")
    private String currencySold;

    @Column(name = "amount_egp", precision = 21, scale = 2)
    private BigDecimal amountEGP;

    @Column(name = "amount_sold", precision = 21, scale = 2)
    private BigDecimal amountSold;

    @Column(name = "currency_exchange_rate", precision = 21, scale = 2)
    private BigDecimal currencyExchangeRate;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public ItemValue id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCurrencySold() {
        return this.currencySold;
    }

    public ItemValue currencySold(String currencySold) {
        this.setCurrencySold(currencySold);
        return this;
    }

    public void setCurrencySold(String currencySold) {
        this.currencySold = currencySold;
    }

    public BigDecimal getAmountEGP() {
        return this.amountEGP;
    }

    public ItemValue amountEGP(BigDecimal amountEGP) {
        this.setAmountEGP(amountEGP);
        return this;
    }

    public void setAmountEGP(BigDecimal amountEGP) {
        this.amountEGP = amountEGP;
    }

    public BigDecimal getAmountSold() {
        return this.amountSold;
    }

    public ItemValue amountSold(BigDecimal amountSold) {
        this.setAmountSold(amountSold);
        return this;
    }

    public void setAmountSold(BigDecimal amountSold) {
        this.amountSold = amountSold;
    }

    public BigDecimal getCurrencyExchangeRate() {
        return this.currencyExchangeRate;
    }

    public ItemValue currencyExchangeRate(BigDecimal currencyExchangeRate) {
        this.setCurrencyExchangeRate(currencyExchangeRate);
        return this;
    }

    public void setCurrencyExchangeRate(BigDecimal currencyExchangeRate) {
        this.currencyExchangeRate = currencyExchangeRate;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ItemValue)) {
            return false;
        }
        return id != null && id.equals(((ItemValue) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ItemValue{" +
            "id=" + getId() +
            ", currencySold='" + getCurrencySold() + "'" +
            ", amountEGP=" + getAmountEGP() +
            ", amountSold=" + getAmountSold() +
            ", currencyExchangeRate=" + getCurrencyExchangeRate() +
            "}";
    }
}
