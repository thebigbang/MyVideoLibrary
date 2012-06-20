/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package myvideolibrary.db.entity;

import java.io.Serializable;
import java.util.List;
import javax.persistence.*;
import myvideolibrary.db.crud.FilmsCRUD;

/**
 *
 * @author thebigbang
 */
@Entity
public class Films implements Serializable{
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    private String titre, description, auteur;
    
    @ManyToMany
    private List<Genre> genres;
    
    @ManyToMany
    private List<Realisateur> realisateurs;
    
    private String numerotation;

    public String getNumerotation() {
        return numerotation;
    }

    public void setNumerotation(String nomFilm) {
        FilmsCRUD f = new FilmsCRUD();
        int d = f.getAll().size();
        Films fi = f.getAll().get(d - 1);
        Long index = Long.parseLong(fi.numerotation.substring(1))+1;
        String Sindex=nomFilm.substring(0, 1);
        this.numerotation = Sindex.concat(index.toString());
    }
    
    
    
    // <editor-fold defaultstate="collapsed" desc="Getter & Setters">
     public String getAuteur() {
        return auteur;
    }

    public void setAuteur(String auteur) {
        this.auteur = auteur;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Genre> getGenres() {
        return genres;
    }

    public void setGenres(List<Genre> genres) {
        this.genres = genres;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Realisateur> getRealisateur() {
        return realisateurs;
    }

    public void setRealisateur(List<Realisateur> realisateur) {
        this.realisateurs = realisateur;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }
    //</editor-fold>
}
