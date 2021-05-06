package com.whoisacat.edu.coursework.bookSharingProvider.repository;

import com.whoisacat.edu.coursework.bookSharingProvider.domain.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

public class BookCustomRepositoryImpl
        implements BookCustomRepository {

    private final EntityManager em;

    public BookCustomRepositoryImpl(EntityManager em) {
        this.em = em;
    }

    @Override
    public Page<Book> getWithRelativePlaces(Pageable pageable, String username) {
        TypedQuery<Book> query = em
                .createQuery("select distinct b from User u " +
                             " left join u.visitingPlaces vp " +
                             " left join u.books b " +
                             " left join fetch b.author " +
                             " left join fetch b.genre " +
                             " where u.email <> :email " +
                             " and vp.city in (select vp.city from User u" +
                             " left join u.visitingPlaces vp " +
                             " where u.email like :email) ", Book.class);
        query.setParameter("email", username);
        query.setFirstResult(pageable.getPageNumber() * pageable.getPageSize());
        query.setMaxResults(pageable.getPageSize());
        List<Book> books = query.getResultList();
        long total = countWithRelativePlaces(username);
        return new PageImpl<>(books, pageable, total);
    }

    private long countWithRelativePlaces(String username) {
        TypedQuery<Long> query = em.createQuery("select count(distinct b) from User u " +
                       " left join u.visitingPlaces vp " +
                       " left join u.books b " +
                       " where u.email <> :email " +
                        " and vp.city in (select vp.city from User u" +
                        " left join u.visitingPlaces vp " +
                        " where u.email like :email) ", Long.class);
        query.setParameter("email", username);
        return query.getSingleResult();
    }
}
