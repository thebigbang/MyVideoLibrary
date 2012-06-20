/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package myvideolibrary.db.entity.JPA;

import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import myvideolibrary.db.entity.Film;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;
import myvideolibrary.db.entity.JPA.exceptions.NonexistentEntityException;
import myvideolibrary.db.entity.Realisateur;

/**
 *
 * @author thebigbang
 */
public class RealisateurJpaController implements Serializable {

    public RealisateurJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Realisateur realisateur) {
        if (realisateur.getFilmList() == null) {
            realisateur.setFilmList(new ArrayList<Film>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Film> attachedFilmList = new ArrayList<Film>();
            for (Film filmListFilmToAttach : realisateur.getFilmList()) {
                filmListFilmToAttach = em.getReference(filmListFilmToAttach.getClass(), filmListFilmToAttach.getId());
                attachedFilmList.add(filmListFilmToAttach);
            }
            realisateur.setFilmList(attachedFilmList);
            em.persist(realisateur);
            for (Film filmListFilm : realisateur.getFilmList()) {
                filmListFilm.getRealisateurList().add(realisateur);
                filmListFilm = em.merge(filmListFilm);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Realisateur realisateur) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Realisateur persistentRealisateur = em.find(Realisateur.class, realisateur.getId());
            List<Film> filmListOld = persistentRealisateur.getFilmList();
            List<Film> filmListNew = realisateur.getFilmList();
            List<Film> attachedFilmListNew = new ArrayList<Film>();
            for (Film filmListNewFilmToAttach : filmListNew) {
                filmListNewFilmToAttach = em.getReference(filmListNewFilmToAttach.getClass(), filmListNewFilmToAttach.getId());
                attachedFilmListNew.add(filmListNewFilmToAttach);
            }
            filmListNew = attachedFilmListNew;
            realisateur.setFilmList(filmListNew);
            realisateur = em.merge(realisateur);
            for (Film filmListOldFilm : filmListOld) {
                if (!filmListNew.contains(filmListOldFilm)) {
                    filmListOldFilm.getRealisateurList().remove(realisateur);
                    filmListOldFilm = em.merge(filmListOldFilm);
                }
            }
            for (Film filmListNewFilm : filmListNew) {
                if (!filmListOld.contains(filmListNewFilm)) {
                    filmListNewFilm.getRealisateurList().add(realisateur);
                    filmListNewFilm = em.merge(filmListNewFilm);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = realisateur.getId();
                if (findRealisateur(id) == null) {
                    throw new NonexistentEntityException("The realisateur with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Realisateur realisateur;
            try {
                realisateur = em.getReference(Realisateur.class, id);
                realisateur.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The realisateur with id " + id + " no longer exists.", enfe);
            }
            List<Film> filmList = realisateur.getFilmList();
            for (Film filmListFilm : filmList) {
                filmListFilm.getRealisateurList().remove(realisateur);
                filmListFilm = em.merge(filmListFilm);
            }
            em.remove(realisateur);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Realisateur> findRealisateurEntities() {
        return findRealisateurEntities(true, -1, -1);
    }

    public List<Realisateur> findRealisateurEntities(int maxResults, int firstResult) {
        return findRealisateurEntities(false, maxResults, firstResult);
    }

    private List<Realisateur> findRealisateurEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Realisateur.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Realisateur findRealisateur(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Realisateur.class, id);
        } finally {
            em.close();
        }
    }

    public int getRealisateurCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Realisateur> rt = cq.from(Realisateur.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
