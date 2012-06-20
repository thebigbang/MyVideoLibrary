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
import myvideolibrary.db.entity.Realisateur;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;
import myvideolibrary.db.entity.Film;
import myvideolibrary.db.entity.Genre;
import myvideolibrary.db.entity.JPA.exceptions.NonexistentEntityException;

/**
 *
 * @author thebigbang
 */
public class FilmJpaController implements Serializable {

    public FilmJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Film film) {
        if (film.getRealisateurList() == null) {
            film.setRealisateurList(new ArrayList<Realisateur>());
        }
        if (film.getGenreList() == null) {
            film.setGenreList(new ArrayList<Genre>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Realisateur> attachedRealisateurList = new ArrayList<Realisateur>();
            for (Realisateur realisateurListRealisateurToAttach : film.getRealisateurList()) {
                realisateurListRealisateurToAttach = em.getReference(realisateurListRealisateurToAttach.getClass(), realisateurListRealisateurToAttach.getId());
                attachedRealisateurList.add(realisateurListRealisateurToAttach);
            }
            film.setRealisateurList(attachedRealisateurList);
            List<Genre> attachedGenreList = new ArrayList<Genre>();
            for (Genre genreListGenreToAttach : film.getGenreList()) {
                genreListGenreToAttach = em.getReference(genreListGenreToAttach.getClass(), genreListGenreToAttach.getId());
                attachedGenreList.add(genreListGenreToAttach);
            }
            film.setGenreList(attachedGenreList);
            em.persist(film);
            for (Realisateur realisateurListRealisateur : film.getRealisateurList()) {
                realisateurListRealisateur.getFilmList().add(film);
                realisateurListRealisateur = em.merge(realisateurListRealisateur);
            }
            for (Genre genreListGenre : film.getGenreList()) {
                genreListGenre.getFilmList().add(film);
                genreListGenre = em.merge(genreListGenre);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Film film) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Film persistentFilm = em.find(Film.class, film.getId());
            List<Realisateur> realisateurListOld = persistentFilm.getRealisateurList();
            List<Realisateur> realisateurListNew = film.getRealisateurList();
            List<Genre> genreListOld = persistentFilm.getGenreList();
            List<Genre> genreListNew = film.getGenreList();
            List<Realisateur> attachedRealisateurListNew = new ArrayList<Realisateur>();
            for (Realisateur realisateurListNewRealisateurToAttach : realisateurListNew) {
                realisateurListNewRealisateurToAttach = em.getReference(realisateurListNewRealisateurToAttach.getClass(), realisateurListNewRealisateurToAttach.getId());
                attachedRealisateurListNew.add(realisateurListNewRealisateurToAttach);
            }
            realisateurListNew = attachedRealisateurListNew;
            film.setRealisateurList(realisateurListNew);
            List<Genre> attachedGenreListNew = new ArrayList<Genre>();
            for (Genre genreListNewGenreToAttach : genreListNew) {
                genreListNewGenreToAttach = em.getReference(genreListNewGenreToAttach.getClass(), genreListNewGenreToAttach.getId());
                attachedGenreListNew.add(genreListNewGenreToAttach);
            }
            genreListNew = attachedGenreListNew;
            film.setGenreList(genreListNew);
            film = em.merge(film);
            for (Realisateur realisateurListOldRealisateur : realisateurListOld) {
                if (!realisateurListNew.contains(realisateurListOldRealisateur)) {
                    realisateurListOldRealisateur.getFilmList().remove(film);
                    realisateurListOldRealisateur = em.merge(realisateurListOldRealisateur);
                }
            }
            for (Realisateur realisateurListNewRealisateur : realisateurListNew) {
                if (!realisateurListOld.contains(realisateurListNewRealisateur)) {
                    realisateurListNewRealisateur.getFilmList().add(film);
                    realisateurListNewRealisateur = em.merge(realisateurListNewRealisateur);
                }
            }
            for (Genre genreListOldGenre : genreListOld) {
                if (!genreListNew.contains(genreListOldGenre)) {
                    genreListOldGenre.getFilmList().remove(film);
                    genreListOldGenre = em.merge(genreListOldGenre);
                }
            }
            for (Genre genreListNewGenre : genreListNew) {
                if (!genreListOld.contains(genreListNewGenre)) {
                    genreListNewGenre.getFilmList().add(film);
                    genreListNewGenre = em.merge(genreListNewGenre);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = film.getId();
                if (findFilm(id) == null) {
                    throw new NonexistentEntityException("The film with id " + id + " no longer exists.");
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
            Film film;
            try {
                film = em.getReference(Film.class, id);
                film.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The film with id " + id + " no longer exists.", enfe);
            }
            List<Realisateur> realisateurList = film.getRealisateurList();
            for (Realisateur realisateurListRealisateur : realisateurList) {
                realisateurListRealisateur.getFilmList().remove(film);
                realisateurListRealisateur = em.merge(realisateurListRealisateur);
            }
            List<Genre> genreList = film.getGenreList();
            for (Genre genreListGenre : genreList) {
                genreListGenre.getFilmList().remove(film);
                genreListGenre = em.merge(genreListGenre);
            }
            em.remove(film);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Film> findFilmEntities() {
        return findFilmEntities(true, -1, -1);
    }

    public List<Film> findFilmEntities(int maxResults, int firstResult) {
        return findFilmEntities(false, maxResults, firstResult);
    }

    private List<Film> findFilmEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Film.class));
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

    public Film findFilm(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Film.class, id);
        } finally {
            em.close();
        }
    }

    public int getFilmCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Film> rt = cq.from(Film.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
