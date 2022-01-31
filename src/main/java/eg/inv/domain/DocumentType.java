package eg.inv.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A DocumentType.
 */
@Entity
@Table(name = "document_type")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class DocumentType implements Serializable {

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

    @Column(name = "active_from")
    private Instant activeFrom;

    @Column(name = "active_to")
    private Instant activeTo;

    @OneToMany(mappedBy = "documentType")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "workflowParameters", "documentType" }, allowSetters = true)
    private Set<DocumentTypeVersion> documentTypeVersions = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public DocumentType id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public DocumentType name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return this.description;
    }

    public DocumentType description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Instant getActiveFrom() {
        return this.activeFrom;
    }

    public DocumentType activeFrom(Instant activeFrom) {
        this.setActiveFrom(activeFrom);
        return this;
    }

    public void setActiveFrom(Instant activeFrom) {
        this.activeFrom = activeFrom;
    }

    public Instant getActiveTo() {
        return this.activeTo;
    }

    public DocumentType activeTo(Instant activeTo) {
        this.setActiveTo(activeTo);
        return this;
    }

    public void setActiveTo(Instant activeTo) {
        this.activeTo = activeTo;
    }

    public Set<DocumentTypeVersion> getDocumentTypeVersions() {
        return this.documentTypeVersions;
    }

    public void setDocumentTypeVersions(Set<DocumentTypeVersion> documentTypeVersions) {
        if (this.documentTypeVersions != null) {
            this.documentTypeVersions.forEach(i -> i.setDocumentType(null));
        }
        if (documentTypeVersions != null) {
            documentTypeVersions.forEach(i -> i.setDocumentType(this));
        }
        this.documentTypeVersions = documentTypeVersions;
    }

    public DocumentType documentTypeVersions(Set<DocumentTypeVersion> documentTypeVersions) {
        this.setDocumentTypeVersions(documentTypeVersions);
        return this;
    }

    public DocumentType addDocumentTypeVersions(DocumentTypeVersion documentTypeVersion) {
        this.documentTypeVersions.add(documentTypeVersion);
        documentTypeVersion.setDocumentType(this);
        return this;
    }

    public DocumentType removeDocumentTypeVersions(DocumentTypeVersion documentTypeVersion) {
        this.documentTypeVersions.remove(documentTypeVersion);
        documentTypeVersion.setDocumentType(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DocumentType)) {
            return false;
        }
        return id != null && id.equals(((DocumentType) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DocumentType{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", activeFrom='" + getActiveFrom() + "'" +
            ", activeTo='" + getActiveTo() + "'" +
            "}";
    }
}
