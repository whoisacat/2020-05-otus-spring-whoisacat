package com.whoisacat.edu.book.jpa.catalogue.repository;

import com.whoisacat.edu.book.jpa.catalogue.domain.Genre;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class GenreRepositoryImpl implements GenreDao{

    @PersistenceContext
    EntityManager em;

    @Override
    public long count() {
        return em.createQuery("select count(g) from Genre g",Long.class).getSingleResult();
    }

    @Override
    public Genre save(Genre genre){
        if(!getByName(genre.getTitle()).isEmpty()){
            return null;
        } else{
            if(genre.getId() == null){
                em.persist(genre);
            } else{
                em.merge(genre);
            }
            return genre;
        }
    }

    @Override
    public Genre getById(long id) {
        TypedQuery<Genre> query = em.createQuery("select g from Genre g where g.id = :id",Genre.class);
        query.setParameter("id",id);
        return query.getSingleResult();
    }

    @Override
    public List<Genre> getByName(String title) {
        TypedQuery<Genre> query = em.createQuery("select g from Genre g where g.title = :title",Genre.class);
        query.setParameter("title",title);
        return query.getResultList();
    }

    @Override
    public List<Genre> getAll() {
        return em.createQuery("select g from Genre g",Genre.class).getResultList();
    }

    @Override
    public int deleteById(long id) {
        Query query = em.createQuery("delete from Genre g where g.id = :id");
        query.setParameter("id",id);
        return query.executeUpdate();
    }
}
