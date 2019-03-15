package com.mycompany.portafolio.domain;


import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A AboutMe.
 */
@Entity
@Table(name = "about_me")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class AboutMe implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "resume", nullable = false)
    private String resume;

    @OneToOne(optional = false)    @NotNull

    @JoinColumn(unique = true)
    private Personal personal;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getResume() {
        return resume;
    }

    public AboutMe resume(String resume) {
        this.resume = resume;
        return this;
    }

    public void setResume(String resume) {
        this.resume = resume;
    }

    public Personal getPersonal() {
        return personal;
    }

    public AboutMe personal(Personal personal) {
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
        AboutMe aboutMe = (AboutMe) o;
        if (aboutMe.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), aboutMe.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "AboutMe{" +
            "id=" + getId() +
            ", resume='" + getResume() + "'" +
            "}";
    }
}
