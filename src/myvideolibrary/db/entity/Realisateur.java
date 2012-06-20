/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package myvideolibrary.db.entity;

import java.io.Serializable;
import java.util.List;
import javax.persistence.*;

/**
 * @author thebigbang
 */
@Entity
public class Realisateur implements Serializable{
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    private String name,surname,biography;
    
    @ManyToMany
    private List<Films> films;

    
    
    // <editor-fold defaultstate="collapsed" desc="Getter & Setters">
    public String getBiography() {
        return biography;
    }

    public void setBiography(String biography) {
        this.biography = biography;
    }

    public List<Films> getFilms() {
        return films;
    }

    public void setFilms(List<Films> films) {
        this.films = films;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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
    
    //</editor-fold>
}
