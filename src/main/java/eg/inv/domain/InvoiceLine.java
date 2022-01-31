package eg.inv.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A InvoiceLine.
 */
@Entity
@Table(name = "invoice_line")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class InvoiceLine implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "description")
    private String description;

    @Column(name = "item_type")
    private String itemType;

    @Column(name = "item_code")
    private String itemCode;

    @Column(name = "unit_type")
    private String unitType;

    @Column(name = "quantity", precision = 21, scale = 2)
    private BigDecimal quantity;

    @Column(name = "sales_total", precision = 21, scale = 2)
    private BigDecimal salesTotal;

    @Column(name = "total", precision = 21, scale = 2)
    private BigDecimal total;

    @Column(name = "value_difference", precision = 21, scale = 2)
    private BigDecimal valueDifference;

    @Column(name = "total_taxable_fees", precision = 21, scale = 2)
    private BigDecimal totalTaxableFees;

    @Column(name = "net_total", precision = 21, scale = 2)
    private BigDecimal netTotal;

    @Column(name = "items_discount", precision = 21, scale = 2)
    private BigDecimal itemsDiscount;

    @Column(name = "internal_code")
    private String internalCode;

    @OneToOne
    @JoinColumn(unique = true)
    private Value unitValue;

    @OneToOne
    @JoinColumn(unique = true)
    private Discount discount;

    @OneToMany(mappedBy = "invoiceLine")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "invoiceLine" }, allowSetters = true)
    private Set<TaxableItem> taxableItems = new HashSet<>();

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

    public InvoiceLine id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return this.description;
    }

    public InvoiceLine description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getItemType() {
        return this.itemType;
    }

    public InvoiceLine itemType(String itemType) {
        this.setItemType(itemType);
        return this;
    }

    public void setItemType(String itemType) {
        this.itemType = itemType;
    }

    public String getItemCode() {
        return this.itemCode;
    }

    public InvoiceLine itemCode(String itemCode) {
        this.setItemCode(itemCode);
        return this;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public String getUnitType() {
        return this.unitType;
    }

    public InvoiceLine unitType(String unitType) {
        this.setUnitType(unitType);
        return this;
    }

    public void setUnitType(String unitType) {
        this.unitType = unitType;
    }

    public BigDecimal getQuantity() {
        return this.quantity;
    }

    public InvoiceLine quantity(BigDecimal quantity) {
        this.setQuantity(quantity);
        return this;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getSalesTotal() {
        return this.salesTotal;
    }

    public InvoiceLine salesTotal(BigDecimal salesTotal) {
        this.setSalesTotal(salesTotal);
        return this;
    }

    public void setSalesTotal(BigDecimal salesTotal) {
        this.salesTotal = salesTotal;
    }

    public BigDecimal getTotal() {
        return this.total;
    }

    public InvoiceLine total(BigDecimal total) {
        this.setTotal(total);
        return this;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public BigDecimal getValueDifference() {
        return this.valueDifference;
    }

    public InvoiceLine valueDifference(BigDecimal valueDifference) {
        this.setValueDifference(valueDifference);
        return this;
    }

    public void setValueDifference(BigDecimal valueDifference) {
        this.valueDifference = valueDifference;
    }

    public BigDecimal getTotalTaxableFees() {
        return this.totalTaxableFees;
    }

    public InvoiceLine totalTaxableFees(BigDecimal totalTaxableFees) {
        this.setTotalTaxableFees(totalTaxableFees);
        return this;
    }

    public void setTotalTaxableFees(BigDecimal totalTaxableFees) {
        this.totalTaxableFees = totalTaxableFees;
    }

    public BigDecimal getNetTotal() {
        return this.netTotal;
    }

    public InvoiceLine netTotal(BigDecimal netTotal) {
        this.setNetTotal(netTotal);
        return this;
    }

    public void setNetTotal(BigDecimal netTotal) {
        this.netTotal = netTotal;
    }

    public BigDecimal getItemsDiscount() {
        return this.itemsDiscount;
    }

    public InvoiceLine itemsDiscount(BigDecimal itemsDiscount) {
        this.setItemsDiscount(itemsDiscount);
        return this;
    }

    public void setItemsDiscount(BigDecimal itemsDiscount) {
        this.itemsDiscount = itemsDiscount;
    }

    public String getInternalCode() {
        return this.internalCode;
    }

    public InvoiceLine internalCode(String internalCode) {
        this.setInternalCode(internalCode);
        return this;
    }

    public void setInternalCode(String internalCode) {
        this.internalCode = internalCode;
    }

    public Value getUnitValue() {
        return this.unitValue;
    }

    public void setUnitValue(Value value) {
        this.unitValue = value;
    }

    public InvoiceLine unitValue(Value value) {
        this.setUnitValue(value);
        return this;
    }

    public Discount getDiscount() {
        return this.discount;
    }

    public void setDiscount(Discount discount) {
        this.discount = discount;
    }

    public InvoiceLine discount(Discount discount) {
        this.setDiscount(discount);
        return this;
    }

    public Set<TaxableItem> getTaxableItems() {
        return this.taxableItems;
    }

    public void setTaxableItems(Set<TaxableItem> taxableItems) {
        if (this.taxableItems != null) {
            this.taxableItems.forEach(i -> i.setInvoiceLine(null));
        }
        if (taxableItems != null) {
            taxableItems.forEach(i -> i.setInvoiceLine(this));
        }
        this.taxableItems = taxableItems;
    }

    public InvoiceLine taxableItems(Set<TaxableItem> taxableItems) {
        this.setTaxableItems(taxableItems);
        return this;
    }

    public InvoiceLine addTaxableItems(TaxableItem taxableItem) {
        this.taxableItems.add(taxableItem);
        taxableItem.setInvoiceLine(this);
        return this;
    }

    public InvoiceLine removeTaxableItems(TaxableItem taxableItem) {
        this.taxableItems.remove(taxableItem);
        taxableItem.setInvoiceLine(null);
        return this;
    }

    public Document getDocument() {
        return this.document;
    }

    public void setDocument(Document document) {
        this.document = document;
    }

    public InvoiceLine document(Document document) {
        this.setDocument(document);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof InvoiceLine)) {
            return false;
        }
        return id != null && id.equals(((InvoiceLine) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "InvoiceLine{" +
            "id=" + getId() +
            ", description='" + getDescription() + "'" +
            ", itemType='" + getItemType() + "'" +
            ", itemCode='" + getItemCode() + "'" +
            ", unitType='" + getUnitType() + "'" +
            ", quantity=" + getQuantity() +
            ", salesTotal=" + getSalesTotal() +
            ", total=" + getTotal() +
            ", valueDifference=" + getValueDifference() +
            ", totalTaxableFees=" + getTotalTaxableFees() +
            ", netTotal=" + getNetTotal() +
            ", itemsDiscount=" + getItemsDiscount() +
            ", internalCode='" + getInternalCode() + "'" +
            "}";
    }
}
