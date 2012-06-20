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
import myvideolibrary.db.entity.Genre;
import myvideolibrary.db.entity.JPA.exceptions.NonexistentEntityException;

/**
 *
 * @author thebigbang
 */
public class GenreJpaController implements Serializable {

    public GenreJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Genre genre) {
        if (genre.getFilmList() == null) {
            genre.setFilmList(new ArrayList<Film>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Film> attachedFilmList = new ArrayList<Film>();
            for (Film filmListFilmToAttach : genre.getFilmList()) {
                filmListFilmToAttach = em.getReference(filmListFilmToAttach.getClass(), filmListFilmToAttach.getId());
                attachedFilmList.add(filmListFilmToAttach);
            }
            genre.setFilmList(attachedFilmList);
            em.persist(genre);
            for (Film filmListFilm : genre.getFilmList()) {
                filmListFilm.getGenreList().add(genre);
                filmListFilm = em.merge(filmListFilm);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Genre genre) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Genre persistentGenre = em.find(Genre.class, genre.getId());
            List<Film> filmListOld = persistentGenre.getFilmList();
            List<Film> filmListNew = genre.getFilmList();
            List<Film> attachedFilmListNew = new ArrayList<Film>();
            for (Film filmListNewFilmToAttach : filmListNew) {
                filmListNewFilmToAttach = em.getReference(filmListNewFilmToAttach.getClass(), filmListNewFilmToAttach.getId());
                attachedFilmListNew.add(filmListNewFilmToAttach);
            }
            filmListNew = attachedFilmListNew;
            genre.setFilmList(filmListNew);
            genre = em.merge(genre);
            for (Film filmListOldFilm : filmListOld) {
                if (!filmListNew.contains(filmListOldFilm)) {
                    filmListOldFilm.getGenreList().remove(genre);
                    filmListOldFilm = em.merge(filmListOldFilm);
                }
            }
            for (Film filmListNewFilm : filmListNew) {
                if (!filmListOld.contains(filmListNewFilm)) {
                    filmListNewFilm.getGenreList().add(genre);
                    filmListNewFilm = em.merge(filmListNewFilm);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = genre.getId();
                if (findGenre(id) == null) {
                    throw new NonexistentEntityException("The genre with id " + id + " no longer exists.");
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
            Genre genre;
            try {
                genre = em.getReference(Genre.class, id);
                genre.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The genre with id " + id + " no longer exists.", enfe);
            }
            List<Film> filmList = genre.getFilmList();
            for (Film filmListFilm : filmList) {
                filmListFilm.getGenreList().remove(genre);
                filmListFilm = em.merge(filmListFilm);
            }
            em.remove(genre);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Genre> findGenreEntities() {
        return findGenreEntities(true, -1, -1);
    }

    public List<Genre> findGenreEntities(int maxResults, int firstResult) {
        return findGenreEntities(false, maxResults, firstResult);
    }

    private List<Genre> findGenreEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Genre.class));
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

    public Genre findGenre(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Genre.class, id);
        } finally {
            em.close();
        }
    }

    public int getGenreCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Genre> rt = cq.from(Genre.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
