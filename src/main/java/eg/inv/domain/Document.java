package eg.inv.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Document.
 */
@Entity
@Table(name = "document")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Document implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "document_type")
    private String documentType;

    @Column(name = "document_type_version")
    private String documentTypeVersion;

    @Column(name = "date_time_issued")
    private Instant dateTimeIssued;

    @Column(name = "taxpayer_activity_code")
    private String taxpayerActivityCode;

    @Column(name = "internal_id")
    private String internalId;

    @Column(name = "purchase_order_reference")
    private String purchaseOrderReference;

    @Column(name = "purchase_order_description")
    private String purchaseOrderDescription;

    @Column(name = "sales_order_reference")
    private String salesOrderReference;

    @Column(name = "sales_order_description")
    private String salesOrderDescription;

    @Column(name = "proforma_invoice_number")
    private String proformaInvoiceNumber;

    @Column(name = "total_sales_amount", precision = 21, scale = 2)
    private BigDecimal totalSalesAmount;

    @Column(name = "total_discount_amount", precision = 21, scale = 2)
    private BigDecimal totalDiscountAmount;

    @Column(name = "net_amount", precision = 21, scale = 2)
    private BigDecimal netAmount;

    @Column(name = "extra_discount_amount", precision = 21, scale = 2)
    private BigDecimal extraDiscountAmount;

    @Column(name = "total_items_discount_amount", precision = 21, scale = 2)
    private BigDecimal totalItemsDiscountAmount;

    @Column(name = "total_amount", precision = 21, scale = 2)
    private BigDecimal totalAmount;

    @JsonIgnoreProperties(value = { "address" }, allowSetters = true)
    @OneToOne
    @JoinColumn(unique = true)
    private Issuer issuer;

    @JsonIgnoreProperties(value = { "address" }, allowSetters = true)
    @OneToOne
    @JoinColumn(unique = true)
    private Receiver receiver;

    @OneToOne
    @JoinColumn(unique = true)
    private Payment payment;

    @OneToOne
    @JoinColumn(unique = true)
    private Delivery delivery;

    @OneToMany(mappedBy = "document")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "unitValue", "discount", "taxableItems", "document" }, allowSetters = true)
    private Set<InvoiceLine> invoiceLines = new HashSet<>();

    @OneToMany(mappedBy = "document")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "document" }, allowSetters = true)
    private Set<TaxTotal> taxTotals = new HashSet<>();

    @OneToMany(mappedBy = "document")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "document" }, allowSetters = true)
    private Set<Signature> signatures = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Document id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDocumentType() {
        return this.documentType;
    }

    public Document documentType(String documentType) {
        this.setDocumentType(documentType);
        return this;
    }

    public void setDocumentType(String documentType) {
        this.documentType = documentType;
    }

    public String getDocumentTypeVersion() {
        return this.documentTypeVersion;
    }

    public Document documentTypeVersion(String documentTypeVersion) {
        this.setDocumentTypeVersion(documentTypeVersion);
        return this;
    }

    public void setDocumentTypeVersion(String documentTypeVersion) {
        this.documentTypeVersion = documentTypeVersion;
    }

    public Instant getDateTimeIssued() {
        return this.dateTimeIssued;
    }

    public Document dateTimeIssued(Instant dateTimeIssued) {
        this.setDateTimeIssued(dateTimeIssued);
        return this;
    }

    public void setDateTimeIssued(Instant dateTimeIssued) {
        this.dateTimeIssued = dateTimeIssued;
    }

    public String getTaxpayerActivityCode() {
        return this.taxpayerActivityCode;
    }

    public Document taxpayerActivityCode(String taxpayerActivityCode) {
        this.setTaxpayerActivityCode(taxpayerActivityCode);
        return this;
    }

    public void setTaxpayerActivityCode(String taxpayerActivityCode) {
        this.taxpayerActivityCode = taxpayerActivityCode;
    }

    public String getInternalId() {
        return this.internalId;
    }

    public Document internalId(String internalId) {
        this.setInternalId(internalId);
        return this;
    }

    public void setInternalId(String internalId) {
        this.internalId = internalId;
    }

    public String getPurchaseOrderReference() {
        return this.purchaseOrderReference;
    }

    public Document purchaseOrderReference(String purchaseOrderReference) {
        this.setPurchaseOrderReference(purchaseOrderReference);
        return this;
    }

    public void setPurchaseOrderReference(String purchaseOrderReference) {
        this.purchaseOrderReference = purchaseOrderReference;
    }

    public String getPurchaseOrderDescription() {
        return this.purchaseOrderDescription;
    }

    public Document purchaseOrderDescription(String purchaseOrderDescription) {
        this.setPurchaseOrderDescription(purchaseOrderDescription);
        return this;
    }

    public void setPurchaseOrderDescription(String purchaseOrderDescription) {
        this.purchaseOrderDescription = purchaseOrderDescription;
    }

    public String getSalesOrderReference() {
        return this.salesOrderReference;
    }

    public Document salesOrderReference(String salesOrderReference) {
        this.setSalesOrderReference(salesOrderReference);
        return this;
    }

    public void setSalesOrderReference(String salesOrderReference) {
        this.salesOrderReference = salesOrderReference;
    }

    public String getSalesOrderDescription() {
        return this.salesOrderDescription;
    }

    public Document salesOrderDescription(String salesOrderDescription) {
        this.setSalesOrderDescription(salesOrderDescription);
        return this;
    }

    public void setSalesOrderDescription(String salesOrderDescription) {
        this.salesOrderDescription = salesOrderDescription;
    }

    public String getProformaInvoiceNumber() {
        return this.proformaInvoiceNumber;
    }

    public Document proformaInvoiceNumber(String proformaInvoiceNumber) {
        this.setProformaInvoiceNumber(proformaInvoiceNumber);
        return this;
    }

    public void setProformaInvoiceNumber(String proformaInvoiceNumber) {
        this.proformaInvoiceNumber = proformaInvoiceNumber;
    }

    public BigDecimal getTotalSalesAmount() {
        return this.totalSalesAmount;
    }

    public Document totalSalesAmount(BigDecimal totalSalesAmount) {
        this.setTotalSalesAmount(totalSalesAmount);
        return this;
    }

    public void setTotalSalesAmount(BigDecimal totalSalesAmount) {
        this.totalSalesAmount = totalSalesAmount;
    }

    public BigDecimal getTotalDiscountAmount() {
        return this.totalDiscountAmount;
    }

    public Document totalDiscountAmount(BigDecimal totalDiscountAmount) {
        this.setTotalDiscountAmount(totalDiscountAmount);
        return this;
    }

    public void setTotalDiscountAmount(BigDecimal totalDiscountAmount) {
        this.totalDiscountAmount = totalDiscountAmount;
    }

    public BigDecimal getNetAmount() {
        return this.netAmount;
    }

    public Document netAmount(BigDecimal netAmount) {
        this.setNetAmount(netAmount);
        return this;
    }

    public void setNetAmount(BigDecimal netAmount) {
        this.netAmount = netAmount;
    }

    public BigDecimal getExtraDiscountAmount() {
        return this.extraDiscountAmount;
    }

    public Document extraDiscountAmount(BigDecimal extraDiscountAmount) {
        this.setExtraDiscountAmount(extraDiscountAmount);
        return this;
    }

    public void setExtraDiscountAmount(BigDecimal extraDiscountAmount) {
        this.extraDiscountAmount = extraDiscountAmount;
    }

    public BigDecimal getTotalItemsDiscountAmount() {
        return this.totalItemsDiscountAmount;
    }

    public Document totalItemsDiscountAmount(BigDecimal totalItemsDiscountAmount) {
        this.setTotalItemsDiscountAmount(totalItemsDiscountAmount);
        return this;
    }

    public void setTotalItemsDiscountAmount(BigDecimal totalItemsDiscountAmount) {
        this.totalItemsDiscountAmount = totalItemsDiscountAmount;
    }

    public BigDecimal getTotalAmount() {
        return this.totalAmount;
    }

    public Document totalAmount(BigDecimal totalAmount) {
        this.setTotalAmount(totalAmount);
        return this;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Issuer getIssuer() {
        return this.issuer;
    }

    public void setIssuer(Issuer issuer) {
        this.issuer = issuer;
    }

    public Document issuer(Issuer issuer) {
        this.setIssuer(issuer);
        return this;
    }

    public Receiver getReceiver() {
        return this.receiver;
    }

    public void setReceiver(Receiver receiver) {
        this.receiver = receiver;
    }

    public Document receiver(Receiver receiver) {
        this.setReceiver(receiver);
        return this;
    }

    public Payment getPayment() {
        return this.payment;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }

    public Document payment(Payment payment) {
        this.setPayment(payment);
        return this;
    }

    public Delivery getDelivery() {
        return this.delivery;
    }

    public void setDelivery(Delivery delivery) {
        this.delivery = delivery;
    }

    public Document delivery(Delivery delivery) {
        this.setDelivery(delivery);
        return this;
    }

    public Set<InvoiceLine> getInvoiceLines() {
        return this.invoiceLines;
    }

    public void setInvoiceLines(Set<InvoiceLine> invoiceLines) {
        if (this.invoiceLines != null) {
            this.invoiceLines.forEach(i -> i.setDocument(null));
        }
        if (invoiceLines != null) {
            invoiceLines.forEach(i -> i.setDocument(this));
        }
        this.invoiceLines = invoiceLines;
    }

    public Document invoiceLines(Set<InvoiceLine> invoiceLines) {
        this.setInvoiceLines(invoiceLines);
        return this;
    }

    public Document addInvoiceLines(InvoiceLine invoiceLine) {
        this.invoiceLines.add(invoiceLine);
        invoiceLine.setDocument(this);
        return this;
    }

    public Document removeInvoiceLines(InvoiceLine invoiceLine) {
        this.invoiceLines.remove(invoiceLine);
        invoiceLine.setDocument(null);
        return this;
    }

    public Set<TaxTotal> getTaxTotals() {
        return this.taxTotals;
    }

    public void setTaxTotals(Set<TaxTotal> taxTotals) {
        if (this.taxTotals != null) {
            this.taxTotals.forEach(i -> i.setDocument(null));
        }
        if (taxTotals != null) {
            taxTotals.forEach(i -> i.setDocument(this));
        }
        this.taxTotals = taxTotals;
    }

    public Document taxTotals(Set<TaxTotal> taxTotals) {
        this.setTaxTotals(taxTotals);
        return this;
    }

    public Document addTaxTotals(TaxTotal taxTotal) {
        this.taxTotals.add(taxTotal);
        taxTotal.setDocument(this);
        return this;
    }

    public Document removeTaxTotals(TaxTotal taxTotal) {
        this.taxTotals.remove(taxTotal);
        taxTotal.setDocument(null);
        return this;
    }

    public Set<Signature> getSignatures() {
        return this.signatures;
    }

    public void setSignatures(Set<Signature> signatures) {
        if (this.signatures != null) {
            this.signatures.forEach(i -> i.setDocument(null));
        }
        if (signatures != null) {
            signatures.forEach(i -> i.setDocument(this));
        }
        this.signatures = signatures;
    }

    public Document signatures(Set<Signature> signatures) {
        this.setSignatures(signatures);
        return this;
    }

    public Document addSignatures(Signature signature) {
        this.signatures.add(signature);
        signature.setDocument(this);
        return this;
    }

    public Document removeSignatures(Signature signature) {
        this.signatures.remove(signature);
        signature.setDocument(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Document)) {
            return false;
        }
        return id != null && id.equals(((Document) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Document{" +
            "id=" + getId() +
            ", documentType='" + getDocumentType() + "'" +
            ", documentTypeVersion='" + getDocumentTypeVersion() + "'" +
            ", dateTimeIssued='" + getDateTimeIssued() + "'" +
            ", taxpayerActivityCode='" + getTaxpayerActivityCode() + "'" +
            ", internalId='" + getInternalId() + "'" +
            ", purchaseOrderReference='" + getPurchaseOrderReference() + "'" +
            ", purchaseOrderDescription='" + getPurchaseOrderDescription() + "'" +
            ", salesOrderReference='" + getSalesOrderReference() + "'" +
            ", salesOrderDescription='" + getSalesOrderDescription() + "'" +
            ", proformaInvoiceNumber='" + getProformaInvoiceNumber() + "'" +
            ", totalSalesAmount=" + getTotalSalesAmount() +
            ", totalDiscountAmount=" + getTotalDiscountAmount() +
            ", netAmount=" + getNetAmount() +
            ", extraDiscountAmount=" + getExtraDiscountAmount() +
            ", totalItemsDiscountAmount=" + getTotalItemsDiscountAmount() +
            ", totalAmount=" + getTotalAmount() +
            "}";
    }
}
