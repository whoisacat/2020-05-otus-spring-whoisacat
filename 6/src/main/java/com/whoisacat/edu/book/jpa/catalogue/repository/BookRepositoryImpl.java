package com.whoisacat.edu.book.jpa.catalogue.repository;

import com.whoisacat.edu.book.jpa.catalogue.domain.Book;
import org.springframework.stereotype.Repository;

import javax.persistence.*;
import java.util.List;

@Repository
public class BookRepositoryImpl implements BookRepository{

    @PersistenceContext
    private EntityManager em;

    @Override
    public long count() {
        return em.createQuery("select count(b) from Book b",Long.class).getSingleResult();
    }

    @Override
    public Book save(Book book) {
        if(book.getId() == null){
            em.persist(book);
        }else {
            em.merge(book);
        }
        return book;
    }

    @Override
    public Book getById(long id) {
        TypedQuery<Book> query = em.createQuery("select b from Book b " +
                "join fetch Author a on b.author = a.id " +
                "join fetch Genre g on b.genre = g.id " +
                "where b.id = :id",Book.class);
        query.setParameter("id",id);
        try{
            return query.getSingleResult();
        }catch(NoResultException e){
            return null;
        }
    }

    @Override
    public List<Book> getByName(String title) {
        TypedQuery<Book> query = em.createQuery("select b from Book b " +
                "join fetch Author a on b.author = a.id " +
                "join fetch Genre g on b.genre = g.id " +
                "where b.title like CONCAT('%',:title,'%')",Book.class);
        query.setParameter("title",title);
        return query.getResultList();
    }

    @Override
    public List<Book> getByAuthor(long author_id) {
        TypedQuery<Book> query = em.createQuery("select b from Book b " +
                "join fetch Author a on b.author = a.id " +
                "join fetch Genre g on b.genre = g.id " +
                "where a.id = :id",Book.class);
        query.setParameter("id",author_id);
        return query.getResultList();
    }

    @Override
    public List<Book> getAll() {
        TypedQuery<Book> query = em.createQuery("select b from Book b " +
                "join fetch Author a on b.author = a.id " +
                "join fetch Genre g on b.genre = g.id",Book.class);
        return query.getResultList();
    }

    @Override
    public int deleteById(long id) {
        Query query = em.createQuery("delete from Book b where b.id = :id");
        query.setParameter("id",id);
        return query.executeUpdate();
    }

    @Override
    public int deleteByName(String title) {
        Query query = em.createQuery("delete from Book b where b.title like CONCAT('%',:title,'%')");
        query.setParameter("title",title);
        return query.executeUpdate();
    }

    @Override public List<Book> findByNameAndAuthorIdAndGenreId(String bookName,long authorId,long genreId){
        TypedQuery<Book> query = em.createQuery("select b from Book b " +
                "join fetch Author a on b.author = a.id " +
                "join fetch Genre g on b.genre = g.id " +
                "where a.id = :authorId and b.title like CONCAT('%',:bookName,'%')",Book.class);
        query.setParameter("bookName",bookName);
        query.setParameter("authorId",authorId);
        return query.getResultList();
    }
}
