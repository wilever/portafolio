package com.mycompany.portafolio.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

import com.mycompany.portafolio.domain.enumeration.Network;

/**
 * A SocialAccount.
 */
@Entity
@Table(name = "social_account")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class SocialAccount implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "jhi_network", nullable = false)
    private Network network;

    @NotNull
    @Column(name = "jhi_user", nullable = false)
    private String user;

    @Column(name = "other_network")
    private String otherNetwork;

    @Lob
    @Column(name = "icon")
    private byte[] icon;

    @Column(name = "icon_content_type")
    private String iconContentType;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("socialAccounts")
    private Contact contact;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Network getNetwork() {
        return network;
    }

    public SocialAccount network(Network network) {
        this.network = network;
        return this;
    }

    public void setNetwork(Network network) {
        this.network = network;
    }

    public String getUser() {
        return user;
    }

    public SocialAccount user(String user) {
        this.user = user;
        return this;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getOtherNetwork() {
        return otherNetwork;
    }

    public SocialAccount otherNetwork(String otherNetwork) {
        this.otherNetwork = otherNetwork;
        return this;
    }

    public void setOtherNetwork(String otherNetwork) {
        this.otherNetwork = otherNetwork;
    }

    public byte[] getIcon() {
        return icon;
    }

    public SocialAccount icon(byte[] icon) {
        this.icon = icon;
        return this;
    }

    public void setIcon(byte[] icon) {
        this.icon = icon;
    }

    public String getIconContentType() {
        return iconContentType;
    }

    public SocialAccount iconContentType(String iconContentType) {
        this.iconContentType = iconContentType;
        return this;
    }

    public void setIconContentType(String iconContentType) {
        this.iconContentType = iconContentType;
    }

    public Contact getContact() {
        return contact;
    }

    public SocialAccount contact(Contact contact) {
        this.contact = contact;
        return this;
    }

    public void setContact(Contact contact) {
        this.contact = contact;
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
        SocialAccount socialAccount = (SocialAccount) o;
        if (socialAccount.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), socialAccount.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "SocialAccount{" +
            "id=" + getId() +
            ", network='" + getNetwork() + "'" +
            ", user='" + getUser() + "'" +
            ", otherNetwork='" + getOtherNetwork() + "'" +
            ", icon='" + getIcon() + "'" +
            ", iconContentType='" + getIconContentType() + "'" +
            "}";
    }
}
