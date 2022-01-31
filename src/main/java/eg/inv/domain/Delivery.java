package eg.inv.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Delivery.
 */
@Entity
@Table(name = "delivery")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Delivery implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "approach")
    private String approach;

    @Column(name = "packaging")
    private String packaging;

    @Column(name = "date_validity")
    private String dateValidity;

    @Column(name = "export_port")
    private String exportPort;

    @Column(name = "country_of_origin")
    private String countryOfOrigin;

    @Column(name = "gross_weight")
    private String grossWeight;

    @Column(name = "net_weight", precision = 21, scale = 2)
    private BigDecimal netWeight;

    @Column(name = "terms")
    private String terms;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Delivery id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getApproach() {
        return this.approach;
    }

    public Delivery approach(String approach) {
        this.setApproach(approach);
        return this;
    }

    public void setApproach(String approach) {
        this.approach = approach;
    }

    public String getPackaging() {
        return this.packaging;
    }

    public Delivery packaging(String packaging) {
        this.setPackaging(packaging);
        return this;
    }

    public void setPackaging(String packaging) {
        this.packaging = packaging;
    }

    public String getDateValidity() {
        return this.dateValidity;
    }

    public Delivery dateValidity(String dateValidity) {
        this.setDateValidity(dateValidity);
        return this;
    }

    public void setDateValidity(String dateValidity) {
        this.dateValidity = dateValidity;
    }

    public String getExportPort() {
        return this.exportPort;
    }

    public Delivery exportPort(String exportPort) {
        this.setExportPort(exportPort);
        return this;
    }

    public void setExportPort(String exportPort) {
        this.exportPort = exportPort;
    }

    public String getCountryOfOrigin() {
        return this.countryOfOrigin;
    }

    public Delivery countryOfOrigin(String countryOfOrigin) {
        this.setCountryOfOrigin(countryOfOrigin);
        return this;
    }

    public void setCountryOfOrigin(String countryOfOrigin) {
        this.countryOfOrigin = countryOfOrigin;
    }

    public String getGrossWeight() {
        return this.grossWeight;
    }

    public Delivery grossWeight(String grossWeight) {
        this.setGrossWeight(grossWeight);
        return this;
    }

    public void setGrossWeight(String grossWeight) {
        this.grossWeight = grossWeight;
    }

    public BigDecimal getNetWeight() {
        return this.netWeight;
    }

    public Delivery netWeight(BigDecimal netWeight) {
        this.setNetWeight(netWeight);
        return this;
    }

    public void setNetWeight(BigDecimal netWeight) {
        this.netWeight = netWeight;
    }

    public String getTerms() {
        return this.terms;
    }

    public Delivery terms(String terms) {
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
        if (!(o instanceof Delivery)) {
            return false;
        }
        return id != null && id.equals(((Delivery) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Delivery{" +
            "id=" + getId() +
            ", approach='" + getApproach() + "'" +
            ", packaging='" + getPackaging() + "'" +
            ", dateValidity='" + getDateValidity() + "'" +
            ", exportPort='" + getExportPort() + "'" +
            ", countryOfOrigin='" + getCountryOfOrigin() + "'" +
            ", grossWeight='" + getGrossWeight() + "'" +
            ", netWeight=" + getNetWeight() +
            ", terms='" + getTerms() + "'" +
            "}";
    }
}
