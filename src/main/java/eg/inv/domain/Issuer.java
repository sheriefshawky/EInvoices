package eg.inv.domain;

import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Issuer.
 */
@Entity
@Table(name = "issuer")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Issuer implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "issuertype")
    private String issuertype;

    @Column(name = "name")
    private String name;

    @OneToOne
    @JoinColumn(unique = true)
    private IssuerAddress address;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Issuer id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIssuertype() {
        return this.issuertype;
    }

    public Issuer issuertype(String issuertype) {
        this.setIssuertype(issuertype);
        return this;
    }

    public void setIssuertype(String issuertype) {
        this.issuertype = issuertype;
    }

    public String getName() {
        return this.name;
    }

    public Issuer name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public IssuerAddress getAddress() {
        return this.address;
    }

    public void setAddress(IssuerAddress issuerAddress) {
        this.address = issuerAddress;
    }

    public Issuer address(IssuerAddress issuerAddress) {
        this.setAddress(issuerAddress);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Issuer)) {
            return false;
        }
        return id != null && id.equals(((Issuer) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Issuer{" +
            "id=" + getId() +
            ", issuertype='" + getIssuertype() + "'" +
            ", name='" + getName() + "'" +
            "}";
    }
}
