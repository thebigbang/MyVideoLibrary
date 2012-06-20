/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package myvideolibrary.db.crud;

import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import myvideolibrary.db.entity.Film;

/**
 *
 * @author thebigbang
 */
public class FilmsCRUD {

   @PersistenceContext(unitName="MyVideoLibraryPU")
    EntityManager manager;
    
    public FilmsCRUD()
    {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("MyVideoLibraryPU");  
        manager = emf.createEntityManager();
    }
    public List<Film> getAll() {
        try {
            CriteriaBuilder criteraiBuilder = manager.getCriteriaBuilder();
            CriteriaQuery<Film> query = criteraiBuilder.createQuery(Film.class);
            query.from(Film.class);
            return manager.createQuery(query).getResultList();
        } catch (Exception e) {
            return null;
        }
    }
    public void Create(Film f)
    {
        
        manager.getTransaction().begin();
        manager.persist(f);      
        manager.getTransaction().commit();
    }
}
