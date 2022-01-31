package eg.inv.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A WorkflowParameters.
 */
@Entity
@Table(name = "workflow_parameters")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class WorkflowParameters implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "parameter")
    private String parameter;

    @Column(name = "value")
    private Long value;

    @Column(name = "active_from")
    private Instant activeFrom;

    @Column(name = "active_to")
    private Instant activeTo;

    @ManyToOne
    @JsonIgnoreProperties(value = { "workflowParameters", "documentType" }, allowSetters = true)
    private DocumentTypeVersion documentTypeVersion;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public WorkflowParameters id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getParameter() {
        return this.parameter;
    }

    public WorkflowParameters parameter(String parameter) {
        this.setParameter(parameter);
        return this;
    }

    public void setParameter(String parameter) {
        this.parameter = parameter;
    }

    public Long getValue() {
        return this.value;
    }

    public WorkflowParameters value(Long value) {
        this.setValue(value);
        return this;
    }

    public void setValue(Long value) {
        this.value = value;
    }

    public Instant getActiveFrom() {
        return this.activeFrom;
    }

    public WorkflowParameters activeFrom(Instant activeFrom) {
        this.setActiveFrom(activeFrom);
        return this;
    }

    public void setActiveFrom(Instant activeFrom) {
        this.activeFrom = activeFrom;
    }

    public Instant getActiveTo() {
        return this.activeTo;
    }

    public WorkflowParameters activeTo(Instant activeTo) {
        this.setActiveTo(activeTo);
        return this;
    }

    public void setActiveTo(Instant activeTo) {
        this.activeTo = activeTo;
    }

    public DocumentTypeVersion getDocumentTypeVersion() {
        return this.documentTypeVersion;
    }

    public void setDocumentTypeVersion(DocumentTypeVersion documentTypeVersion) {
        this.documentTypeVersion = documentTypeVersion;
    }

    public WorkflowParameters documentTypeVersion(DocumentTypeVersion documentTypeVersion) {
        this.setDocumentTypeVersion(documentTypeVersion);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof WorkflowParameters)) {
            return false;
        }
        return id != null && id.equals(((WorkflowParameters) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "WorkflowParameters{" +
            "id=" + getId() +
            ", parameter='" + getParameter() + "'" +
            ", value=" + getValue() +
            ", activeFrom='" + getActiveFrom() + "'" +
            ", activeTo='" + getActiveTo() + "'" +
            "}";
    }
}
