package com.whoisacat.edu.book.jpa.catalogue.repository;

import com.whoisacat.edu.book.jpa.catalogue.domain.Author;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.*;

@Repository
public class AuthorRepositoryImpl implements AuthorRepository{

    @PersistenceContext
    private EntityManager em;

    @Override
    public Long count() {
        return em.createQuery("select count(a) from Author a",Long.class).getSingleResult();
    }

    @Override
    public Author save(Author author) {
        if(author.getId() == null){
            em.persist(author);
        }else {
            em.merge(author);
        }
        return author;
    }

    @Override
    public Author getById(long id) {
        TypedQuery<Author> query = em.createQuery("select a from Author a where a.id = :id",Author.class);
        query.setParameter("id",id);
        return query.getSingleResult();
    }

    @Override
    public List<Author> getByName(String name) {
        TypedQuery<Author> query = em.createQuery("select a from Author a where a.title like CONCAT('%', :aname, '%')",
                Author.class);
        query.setParameter("aname",name);
        return query.getResultList();
    }

    @Override
    public List<Author> getAll() {
        TypedQuery<Author> query = em.createQuery("select a from Author a",Author.class);
        return query.getResultList();
    }

    @Override
    public int deleteById(long id){
        Query query = em.createQuery("delete from Author a where a.id = :id");
        query.setParameter("id",id);
        return query.executeUpdate();
    }
}
