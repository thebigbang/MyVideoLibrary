/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package myvideolibrary.db.entity;

import java.io.Serializable;
import java.util.List;
import javax.persistence.*;
import javax.xml.bind.annotation.XmlTransient;
import myvideolibrary.db.crud.FilmsCRUD;

/**
 *
 * @author thebigbang
 */
@Entity
public class Film implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String titre;
    private String description;
    private String numerotation;
    @ManyToMany
    private List<Realisateur> realisateurList;
    @ManyToMany
    private List<Genre> genreList;

    public Film() {
    }

    public Film(Integer id) {
        this.id = id;
    }

    public Film(Integer id, String titre, String description, String numerotation) {
        this.id = id;
        this.titre = titre;
        this.description = description;
        this.numerotation = numerotation;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getNumerotation() {
        return numerotation;
    }

    public void setNumerotation() {
        String t = getTitre();
        if (!"".equals(t)) {
            setNumerotation(t);
        }
    }

    public void setNumerotation(String nomFilm) {
        FilmsCRUD f = new FilmsCRUD();
        int d = f.getAll().size();
        Long index = 0l;
        if (d > 0) {
            Film fi = f.getAll().get(d - 1);
            index = Long.parseLong(fi.numerotation.substring(1)) + 1;
        }
        String Sindex = nomFilm.substring(0, 1);
        this.numerotation = Sindex.concat(index.toString());
    }

    @XmlTransient
    public List<Realisateur> getRealisateurList() {
        return realisateurList;
    }

    public void setRealisateurList(List<Realisateur> realisateurList) {
        this.realisateurList = realisateurList;
    }

    @XmlTransient
    public List<Genre> getGenreList() {
        return genreList;
    }

    public void setGenreList(List<Genre> genreList) {
        this.genreList = genreList;
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
        if (!(object instanceof Film)) {
            return false;
        }
        Film other = (Film) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "myvideolibrary.db.entity.Film[ id=" + id + " ]";
    }
}
