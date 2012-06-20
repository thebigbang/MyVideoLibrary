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
import myvideolibrary.db.entity.Realisateur;

/**
 *
 * @author thebigbang
 */
public class RealisateursCRUD {
    @PersistenceContext
    EntityManager manager;
    
    public List<Realisateur> getAll() {
        try {
            CriteriaBuilder criteraiBuilder = manager.getCriteriaBuilder();
            CriteriaQuery<Realisateur> query = criteraiBuilder.createQuery(Realisateur.class);
            query.from(Realisateur.class);
            return manager.createQuery(query).getResultList();
        } catch (Exception e) {
            return null;
        }
    }
    public void create(Realisateur r)
    {
        manager.persist(r);
    }
    
    /**@deprecated   */
     public List<Film> getFilms(Long id) {
        try {
            CriteriaBuilder criteraiBuilder = manager.getCriteriaBuilder();
            CriteriaQuery<Film> query = criteraiBuilder.createQuery(Film.class);
            
            List<Predicate> pre = new ArrayList<Predicate>();
            
            pre.add(criteraiBuilder.equal(query.from(Film.class).get("realisateurs").get("id"), id));
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
            
            pre.add(criteraiBuilder.equal(query.from(Genre.class).get("realisateurs").get("nom"), name));
            query.where(pre.toArray(new Predicate[pre.size()]));
            
            return manager.createQuery(query).getResultList();
        } catch (Exception e) {
            return null;
        }
    }
}
