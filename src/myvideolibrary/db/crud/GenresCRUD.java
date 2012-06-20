/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package myvideolibrary.db.crud;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import myvideolibrary.db.entity.Film;
import myvideolibrary.db.entity.Genre;

/**
 *
 * @author thebigbang
 */
public class GenresCRUD {
    @PersistenceContext
    EntityManager manager;
    
    public List<Genre> getAll() {
        try {
            CriteriaBuilder criteraiBuilder = manager.getCriteriaBuilder();
            CriteriaQuery<Genre> query = criteraiBuilder.createQuery(Genre.class);
            query.from(Film.class);
            return manager.createQuery(query).getResultList();
        } catch (Exception e) {
            return null;
        }
    }
    public void create(Genre g)
    {
        manager.persist(g);
    }
    /**@deprecated   */
    public List<Film> getFilms(Long id) {
        try {
            CriteriaBuilder criteraiBuilder = manager.getCriteriaBuilder();
            CriteriaQuery<Film> query = criteraiBuilder.createQuery(Film.class);
            
            List<Predicate> pre = new ArrayList<Predicate>();
            
            pre.add(criteraiBuilder.equal(query.from(Genre.class).get("Genre").get("id"), id));
            query.where(pre.toArray(new Predicate[pre.size()]));
            
            return manager.createQuery(query).getResultList();
        } catch (Exception e) {
            return null;
        }
    }
        /**@deprecated   */
    public List<Film> getFilms(String name) {
        try {
            CriteriaBuilder criteraiBuilder = manager.getCriteriaBuilder();
            CriteriaQuery<Film> query = criteraiBuilder.createQuery(Film.class);
            
            List<Predicate> pre = new ArrayList<Predicate>();
            
            pre.add(criteraiBuilder.equal(query.from(Genre.class).get("Genre").get("nom"), name));
            query.where(pre.toArray(new Predicate[pre.size()]));
            
            return manager.createQuery(query).getResultList();
        } catch (Exception e) {
            return null;
        }
    }
    
}
