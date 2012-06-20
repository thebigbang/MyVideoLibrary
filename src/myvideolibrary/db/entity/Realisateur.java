/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package myvideolibrary.db.entity;

import java.io.Serializable;
import java.util.List;
import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author thebigbang
 */
@Entity
@Table(name = "realisateurset")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Realisateur.findAll", query = "SELECT r FROM Realisateur r"),
    @NamedQuery(name = "Realisateur.findById", query = "SELECT r FROM Realisateur r WHERE r.id = :id")})
public class Realisateur implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Lob
    @Column(name = "name")
    private String name;
    @Basic(optional = false)
    @Lob
    @Column(name = "surname")
    private String surname;
    @Basic(optional = false)
    @Lob
    @Column(name = "biography")
    private String biography;
    @JoinTable(name = "filmsrealisateur", joinColumns = {
        @JoinColumn(name = "Realisateur_id", referencedColumnName = "id")}, inverseJoinColumns = {
        @JoinColumn(name = "Films_id", referencedColumnName = "id")})
    @ManyToMany
    private List<Film> filmList;

    public Realisateur() {
    }

    public Realisateur(Integer id) {
        this.id = id;
    }

    public Realisateur(Integer id, String name, String surname, String biography) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.biography = biography;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getBiography() {
        return biography;
    }

    public void setBiography(String biography) {
        this.biography = biography;
    }

    @XmlTransient
    public List<Film> getFilmList() {
        return filmList;
    }

    public void setFilmList(List<Film> filmList) {
        this.filmList = filmList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Realisateur)) {
            return false;
        }
        Realisateur other = (Realisateur) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "myvideolibrary.db.entity.Realisateur[ id=" + id + " ]";
    }
    
}
