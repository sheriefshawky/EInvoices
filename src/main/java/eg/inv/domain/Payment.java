package eg.inv.domain;

import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Payment.
 */
@Entity
@Table(name = "payment")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Payment implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "bank_name")
    private String bankName;

    @Column(name = "bank_address")
    private String bankAddress;

    @Column(name = "bank_account_no")
    private String bankAccountNo;

    @Column(name = "bank_account_iban")
    private String bankAccountIBAN;

    @Column(name = "swift_code")
    private String swiftCode;

    @Column(name = "terms")
    private String terms;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Payment id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBankName() {
        return this.bankName;
    }

    public Payment bankName(String bankName) {
        this.setBankName(bankName);
        return this;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBankAddress() {
        return this.bankAddress;
    }

    public Payment bankAddress(String bankAddress) {
        this.setBankAddress(bankAddress);
        return this;
    }

    public void setBankAddress(String bankAddress) {
        this.bankAddress = bankAddress;
    }

    public String getBankAccountNo() {
        return this.bankAccountNo;
    }

    public Payment bankAccountNo(String bankAccountNo) {
        this.setBankAccountNo(bankAccountNo);
        return this;
    }

    public void setBankAccountNo(String bankAccountNo) {
        this.bankAccountNo = bankAccountNo;
    }

    public String getBankAccountIBAN() {
        return this.bankAccountIBAN;
    }

    public Payment bankAccountIBAN(String bankAccountIBAN) {
        this.setBankAccountIBAN(bankAccountIBAN);
        return this;
    }

    public void setBankAccountIBAN(String bankAccountIBAN) {
        this.bankAccountIBAN = bankAccountIBAN;
    }

    public String getSwiftCode() {
        return this.swiftCode;
    }

    public Payment swiftCode(String swiftCode) {
        this.setSwiftCode(swiftCode);
        return this;
    }

    public void setSwiftCode(String swiftCode) {
        this.swiftCode = swiftCode;
    }

    public String getTerms() {
        return this.terms;
    }

    public Payment terms(String terms) {
        this.setTerms(terms);
        return this;
    }

    public void setTerms(String terms) {
        this.terms = terms;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Payment)) {
            return false;
        }
        return id != null && id.equals(((Payment) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Payment{" +
            "id=" + getId() +
            ", bankName='" + getBankName() + "'" +
            ", bankAddress='" + getBankAddress() + "'" +
            ", bankAccountNo='" + getBankAccountNo() + "'" +
            ", bankAccountIBAN='" + getBankAccountIBAN() + "'" +
            ", swiftCode='" + getSwiftCode() + "'" +
            ", terms='" + getTerms() + "'" +
            "}";
    }
}
