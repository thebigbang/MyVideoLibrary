/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package myvideolibrary.db.crud;

import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import myvideolibrary.db.entity.Films;

/**
 *
 * @author thebigbang
 */
public class FilmsCRUD {

    @PersistenceContext
    EntityManager manager;
    
    public List<Films> getAll() {
        try {
            CriteriaBuilder criteraiBuilder = manager.getCriteriaBuilder();
            CriteriaQuery<Films> query = criteraiBuilder.createQuery(Films.class);
            query.from(Films.class);
            return manager.createQuery(query).getResultList();
        } catch (Exception e) {
            return null;
        }
    }
}
