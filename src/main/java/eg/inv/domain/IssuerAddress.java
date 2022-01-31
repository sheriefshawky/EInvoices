package eg.inv.domain;

import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A IssuerAddress.
 */
@Entity
@Table(name = "issuer_address")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class IssuerAddress implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "branch_id")
    private String branchId;

    @Column(name = "country")
    private String country;

    @Column(name = "governate")
    private String governate;

    @Column(name = "region_city")
    private String regionCity;

    @Column(name = "street")
    private String street;

    @Column(name = "building_number")
    private String buildingNumber;

    @Column(name = "postal_code")
    private String postalCode;

    @Column(name = "floor")
    private String floor;

    @Column(name = "room")
    private String room;

    @Column(name = "landmark")
    private String landmark;

    @Column(name = "additional_information")
    private String additionalInformation;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public IssuerAddress id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBranchId() {
        return this.branchId;
    }

    public IssuerAddress branchId(String branchId) {
        this.setBranchId(branchId);
        return this;
    }

    public void setBranchId(String branchId) {
        this.branchId = branchId;
    }

    public String getCountry() {
        return this.country;
    }

    public IssuerAddress country(String country) {
        this.setCountry(country);
        return this;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getGovernate() {
        return this.governate;
    }

    public IssuerAddress governate(String governate) {
        this.setGovernate(governate);
        return this;
    }

    public void setGovernate(String governate) {
        this.governate = governate;
    }

    public String getRegionCity() {
        return this.regionCity;
    }

    public IssuerAddress regionCity(String regionCity) {
        this.setRegionCity(regionCity);
        return this;
    }

    public void setRegionCity(String regionCity) {
        this.regionCity = regionCity;
    }

    public String getStreet() {
        return this.street;
    }

    public IssuerAddress street(String street) {
        this.setStreet(street);
        return this;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getBuildingNumber() {
        return this.buildingNumber;
    }

    public IssuerAddress buildingNumber(String buildingNumber) {
        this.setBuildingNumber(buildingNumber);
        return this;
    }

    public void setBuildingNumber(String buildingNumber) {
        this.buildingNumber = buildingNumber;
    }

    public String getPostalCode() {
        return this.postalCode;
    }

    public IssuerAddress postalCode(String postalCode) {
        this.setPostalCode(postalCode);
        return this;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getFloor() {
        return this.floor;
    }

    public IssuerAddress floor(String floor) {
        this.setFloor(floor);
        return this;
    }

    public void setFloor(String floor) {
        this.floor = floor;
    }

    public String getRoom() {
        return this.room;
    }

    public IssuerAddress room(String room) {
        this.setRoom(room);
        return this;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public String getLandmark() {
        return this.landmark;
    }

    public IssuerAddress landmark(String landmark) {
        this.setLandmark(landmark);
        return this;
    }

    public void setLandmark(String landmark) {
        this.landmark = landmark;
    }

    public String getAdditionalInformation() {
        return this.additionalInformation;
    }

    public IssuerAddress additionalInformation(String additionalInformation) {
        this.setAdditionalInformation(additionalInformation);
        return this;
    }

    public void setAdditionalInformation(String additionalInformation) {
        this.additionalInformation = additionalInformation;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof IssuerAddress)) {
            return false;
        }
        return id != null && id.equals(((IssuerAddress) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "IssuerAddress{" +
            "id=" + getId() +
            ", branchId='" + getBranchId() + "'" +
            ", country='" + getCountry() + "'" +
            ", governate='" + getGovernate() + "'" +
            ", regionCity='" + getRegionCity() + "'" +
            ", street='" + getStreet() + "'" +
            ", buildingNumber='" + getBuildingNumber() + "'" +
            ", postalCode='" + getPostalCode() + "'" +
            ", floor='" + getFloor() + "'" +
            ", room='" + getRoom() + "'" +
            ", landmark='" + getLandmark() + "'" +
            ", additionalInformation='" + getAdditionalInformation() + "'" +
            "}";
    }
}
