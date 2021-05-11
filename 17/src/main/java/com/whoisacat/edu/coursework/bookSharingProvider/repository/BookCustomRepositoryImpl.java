package com.whoisacat.edu.coursework.bookSharingProvider.repository;

import com.whoisacat.edu.coursework.bookSharingProvider.dto.BookAndUserDTO;
import com.whoisacat.edu.coursework.bookSharingProvider.dto.WHOPageImpl;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

public class BookCustomRepositoryImpl
        implements BookCustomRepository {

    private final EntityManager em;

    public BookCustomRepositoryImpl(EntityManager em) {
        this.em = em;
    }

    @Override public Page<BookAndUserDTO> getBooksInUsersCities(Pageable pageable, String username, String text,
            boolean own) {
        TypedQuery<BookAndUserDTO> query = em
                .createQuery(
                        "select distinct new com.whoisacat.edu.coursework.bookSharingProvider.dto.BookAndUserDTO(" +
                        "b.id,b.title,b.author.title,b.genre.title,concat(u.firstName,' ',u.lastName),u.email) " +
                        " from User u " +
                        " left join u.visitingPlaces vp " +
                        " left join u.books b " +
                        " left join b.author " +
                        " left join b.genre " +
                        " where (LOWER(b.title) like :text " +
                        " or LOWER(b.author.title) like :text " +
                        " or LOWER(b.genre.title) like :text) " +
                        " and u.email " + (own ? "like" : "<>") + " :email " +
                        (own ? "" : " and vp.city in (select vp.city from User u" +
                                    " left join u.visitingPlaces vp " +
                                    " where u.email like :email) ") +
                        " order by b.title, b.author.title, b.genre.title", BookAndUserDTO.class);
        query.setParameter("email", username);
        query.setParameter("text", normaliseWithWildSearch(text));
        query.setFirstResult(pageable.getPageNumber() * pageable.getPageSize());
        query.setMaxResults(pageable.getPageSize());
        long total = countOtherPeoplesBooksInUsersCities(username, text, own);
        return new WHOPageImpl<>(query.getResultList(), pageable, total, text);
    }

    private String normaliseWithWildSearch(String text) {
        return "%" + (text == null ? "" : text.toLowerCase()) + "%";
    }

    private long countOtherPeoplesBooksInUsersCities(String username, String text, boolean own) {
        TypedQuery<Long> query = em
                .createQuery("select count(distinct b) " +
                             " from User u " +
                             " left join u.visitingPlaces vp " +
                             " left join u.books b " +
                             " left join b.author " +
                             " left join b.genre " +
                             " where (LOWER(b.title) like :text " +
                             " or LOWER(b.author.title) like :text " +
                             " or LOWER(b.genre.title) like :text) " +
                             " and u.email " + (own ? "like" : "<>") + " :email " +
                             (own ? "" : " and vp.city in (select vp.city from User u" +
                                         " left join u.visitingPlaces vp " +
                                         " where u.email like :email) "), Long.class);
        query.setParameter("email", username);
        query.setParameter("text", normaliseWithWildSearch(text));
        return query.getSingleResult();
    }
}
