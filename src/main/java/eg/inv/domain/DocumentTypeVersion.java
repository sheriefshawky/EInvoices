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
 * A DocumentTypeVersion.
 */
@Entity
@Table(name = "document_type_version")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class DocumentTypeVersion implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "version_number", precision = 21, scale = 2)
    private BigDecimal versionNumber;

    @Column(name = "status")
    private String status;

    @Column(name = "active_from")
    private Instant activeFrom;

    @Column(name = "active_to")
    private Instant activeTo;

    @OneToMany(mappedBy = "documentTypeVersion")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "documentTypeVersion" }, allowSetters = true)
    private Set<WorkflowParameters> workflowParameters = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = { "documentTypeVersions" }, allowSetters = true)
    private DocumentType documentType;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public DocumentTypeVersion id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public DocumentTypeVersion name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return this.description;
    }

    public DocumentTypeVersion description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getVersionNumber() {
        return this.versionNumber;
    }

    public DocumentTypeVersion versionNumber(BigDecimal versionNumber) {
        this.setVersionNumber(versionNumber);
        return this;
    }

    public void setVersionNumber(BigDecimal versionNumber) {
        this.versionNumber = versionNumber;
    }

    public String getStatus() {
        return this.status;
    }

    public DocumentTypeVersion status(String status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Instant getActiveFrom() {
        return this.activeFrom;
    }

    public DocumentTypeVersion activeFrom(Instant activeFrom) {
        this.setActiveFrom(activeFrom);
        return this;
    }

    public void setActiveFrom(Instant activeFrom) {
        this.activeFrom = activeFrom;
    }

    public Instant getActiveTo() {
        return this.activeTo;
    }

    public DocumentTypeVersion activeTo(Instant activeTo) {
        this.setActiveTo(activeTo);
        return this;
    }

    public void setActiveTo(Instant activeTo) {
        this.activeTo = activeTo;
    }

    public Set<WorkflowParameters> getWorkflowParameters() {
        return this.workflowParameters;
    }

    public void setWorkflowParameters(Set<WorkflowParameters> workflowParameters) {
        if (this.workflowParameters != null) {
            this.workflowParameters.forEach(i -> i.setDocumentTypeVersion(null));
        }
        if (workflowParameters != null) {
            workflowParameters.forEach(i -> i.setDocumentTypeVersion(this));
        }
        this.workflowParameters = workflowParameters;
    }

    public DocumentTypeVersion workflowParameters(Set<WorkflowParameters> workflowParameters) {
        this.setWorkflowParameters(workflowParameters);
        return this;
    }

    public DocumentTypeVersion addWorkflowParameters(WorkflowParameters workflowParameters) {
        this.workflowParameters.add(workflowParameters);
        workflowParameters.setDocumentTypeVersion(this);
        return this;
    }

    public DocumentTypeVersion removeWorkflowParameters(WorkflowParameters workflowParameters) {
        this.workflowParameters.remove(workflowParameters);
        workflowParameters.setDocumentTypeVersion(null);
        return this;
    }

    public DocumentType getDocumentType() {
        return this.documentType;
    }

    public void setDocumentType(DocumentType documentType) {
        this.documentType = documentType;
    }

    public DocumentTypeVersion documentType(DocumentType documentType) {
        this.setDocumentType(documentType);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DocumentTypeVersion)) {
            return false;
        }
        return id != null && id.equals(((DocumentTypeVersion) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DocumentTypeVersion{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", versionNumber=" + getVersionNumber() +
            ", status='" + getStatus() + "'" +
            ", activeFrom='" + getActiveFrom() + "'" +
            ", activeTo='" + getActiveTo() + "'" +
            "}";
    }
}
