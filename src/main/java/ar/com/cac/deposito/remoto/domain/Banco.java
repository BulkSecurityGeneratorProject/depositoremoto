package ar.com.cac.deposito.remoto.domain;


import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

import ar.com.cac.deposito.remoto.domain.enumeration.Acepta;

/**
 * A Banco.
 */
@Entity
@Table(name = "banco")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Banco implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "nombre_de_fantasia")
    private String nombreDeFantasia;

    @Column(name = "numero")
    private Integer numero;

    @Column(name = "razon_social")
    private String razonSocial;

    @Column(name = "cuit")
    private String cuit;

    @Column(name = "otros_datos")
    private String otrosDatos;

    @Column(name = "activo")
    private Boolean activo;

    @Enumerated(EnumType.STRING)
    @Column(name = "diferidos")
    private Acepta diferidos;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombreDeFantasia() {
        return nombreDeFantasia;
    }

    public Banco nombreDeFantasia(String nombreDeFantasia) {
        this.nombreDeFantasia = nombreDeFantasia;
        return this;
    }

    public void setNombreDeFantasia(String nombreDeFantasia) {
        this.nombreDeFantasia = nombreDeFantasia;
    }

    public Integer getNumero() {
        return numero;
    }

    public Banco numero(Integer numero) {
        this.numero = numero;
        return this;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    public String getRazonSocial() {
        return razonSocial;
    }

    public Banco razonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
        return this;
    }

    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }

    public String getCuit() {
        return cuit;
    }

    public Banco cuit(String cuit) {
        this.cuit = cuit;
        return this;
    }

    public void setCuit(String cuit) {
        this.cuit = cuit;
    }

    public String getOtrosDatos() {
        return otrosDatos;
    }

    public Banco otrosDatos(String otrosDatos) {
        this.otrosDatos = otrosDatos;
        return this;
    }

    public void setOtrosDatos(String otrosDatos) {
        this.otrosDatos = otrosDatos;
    }

    public Boolean isActivo() {
        return activo;
    }

    public Banco activo(Boolean activo) {
        this.activo = activo;
        return this;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    public Acepta getDiferidos() {
        return diferidos;
    }

    public Banco diferidos(Acepta diferidos) {
        this.diferidos = diferidos;
        return this;
    }

    public void setDiferidos(Acepta diferidos) {
        this.diferidos = diferidos;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Banco)) {
            return false;
        }
        return id != null && id.equals(((Banco) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Banco{" +
            "id=" + getId() +
            ", nombreDeFantasia='" + getNombreDeFantasia() + "'" +
            ", numero=" + getNumero() +
            ", razonSocial='" + getRazonSocial() + "'" +
            ", cuit='" + getCuit() + "'" +
            ", otrosDatos='" + getOtrosDatos() + "'" +
            ", activo='" + isActivo() + "'" +
            ", diferidos='" + getDiferidos() + "'" +
            "}";
    }
}
