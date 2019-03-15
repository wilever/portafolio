package com.mycompany.portafolio.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

import com.mycompany.portafolio.domain.enumeration.LanguagueLevel;

/**
 * A Languague.
 */
@Entity
@Table(name = "languague")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Languague implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Column(name = "is_active", nullable = false)
    private Boolean isActive;

    @Enumerated(EnumType.STRING)
    @Column(name = "jhi_level")
    private LanguagueLevel level;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("languagues")
    private Personal personal;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Languague name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean isIsActive() {
        return isActive;
    }

    public Languague isActive(Boolean isActive) {
        this.isActive = isActive;
        return this;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public LanguagueLevel getLevel() {
        return level;
    }

    public Languague level(LanguagueLevel level) {
        this.level = level;
        return this;
    }

    public void setLevel(LanguagueLevel level) {
        this.level = level;
    }

    public Personal getPersonal() {
        return personal;
    }

    public Languague personal(Personal personal) {
        this.personal = personal;
        return this;
    }

    public void setPersonal(Personal personal) {
        this.personal = personal;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Languague languague = (Languague) o;
        if (languague.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), languague.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Languague{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", isActive='" + isIsActive() + "'" +
            ", level='" + getLevel() + "'" +
            "}";
    }
}
