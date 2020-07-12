package com.whoisacat.edu.book.jpa.catalogue.dao;

import com.whoisacat.edu.book.jpa.catalogue.domain.Author;
import com.whoisacat.edu.book.jpa.catalogue.domain.Book;
import com.whoisacat.edu.book.jpa.catalogue.domain.Genre;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@SuppressWarnings({"SqlNoDataSourceInspection", "ConstantConditions", "SqlDialectInspection"})
@Repository
public class BookDaoJdbc implements BookDao{

    private final NamedParameterJdbcOperations jdbc;

    public BookDaoJdbc(NamedParameterJdbcOperations namedParameterJdbcOperations)
    {
        this.jdbc = namedParameterJdbcOperations;
    }

    @Override
    public long count() {
        return jdbc.getJdbcOperations().queryForObject("select count(*) from book",Integer.class);
    }

    @Override
    public Long insert(Book book) {
        SqlParameterSource params = new MapSqlParameterSource(Map.of(
                "name",book.getName(),
                "author_id",book.getAuthor().getId(),
                "genre_id",book.getGenre().getId()));
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbc.update("insert into book (\"name\",author_id,genre_id) " +
                "values (:name,:author_id,:genre_id)", params, keyHolder);
        return keyHolder.getKey().longValue();
    }

    @Override
    public Book getById(long id) {
        MapSqlParameterSource params = new MapSqlParameterSource("id", id);
        try{
            return jdbc.queryForObject(
                    "select bk.id as bkid,bk.\"name\" as bkname, " +
                            "au.id as auid,au.\"name\" as auname, " +
                            "gr.id as grid,gr.\"name\" as grname " +
                            "from book bk " +
                            "left join author au on bk.author_id = au.id " +
                            "left join genre gr on bk.genre_id = gr.id " +
                            "where bk.id = :id;",params,new BookMapper());
        } catch(EmptyResultDataAccessException e){
            return null;
        }
    }

    @Override
    public List<Book> getByName(String name) {
        MapSqlParameterSource params = new MapSqlParameterSource("name", name);
        return jdbc.query(
                "select bk.id as bkid,bk.\"name\" as bkname, " +
                            "au.id as auid,au.\"name\" as auname, " +
                            "gr.id as grid,gr.\"name\" as grname " +
                        "from book bk " +
                            "left join author au on bk.author_id = au.id " +
                            "left join genre gr on bk.genre_id = gr.id " +
                        "where bk.\"name\" like '%' || :name || '%';", params, new BookMapper());
    }

    @Override
    public List<Book> getByAuthor(long author_id) {
        MapSqlParameterSource params = new MapSqlParameterSource("author_id", author_id);
        return jdbc.query(
                "select bk.id as bkid,bk.\"name\" as bkname, " +
                            "au.id as auid,au.\"name\" as auname, " +
                            "gr.id as grid,gr.\"name\" as grname " +
                        "from book bk " +
                            "left join author au on bk.author_id = au.id " +
                            "left join genre gr on bk.genre_id = gr.id " +
                        "where au.id = :author_id;", params, new BookMapper());
    }

    @Override
    public List<Book> getAll() {
        return jdbc.query(
                "select bk.id as bkid,bk.\"name\" as bkname, " +
                            "au.id as auid,au.\"name\" as auname, " +
                            "gr.id as grid,gr.\"name\" as grname " +
                        "from book bk " +
                            "left join author au on bk.author_id = au.id " +
                            "left join genre gr on bk.genre_id = gr.id;", new BookMapper());
    }

    @Override
    public void deleteById(long id) {
        Map<String, Object> params = Collections.singletonMap("id", id);
        jdbc.update(
                "delete from book where id = :id", params
        );
    }

    @Override
    public void deleteByName(String name) {
        Map<String, Object> params = Collections.singletonMap("name", name);
        jdbc.update(
                "delete from book where \"name\" = :name", params
        );
    }

    @Override public List<Book> findByNameAndAuthorIdAndGenreId(String bookName,long authorId,long genreId){
        Map<String, Object> params = Map.of("bookName",bookName,
                "authorId",authorId,
                "genreId",genreId);
        return jdbc.query(
                "select bk.id as bkid,bk.\"name\" as bkname, " +
                            "au.id as auid,au.\"name\" as auname, " +
                            "gr.id as grid,gr.\"name\" as grname " +
                        "from book bk " +
                            "left join author au on bk.author_id = au.id " +
                            "left join genre gr on bk.genre_id = gr.id " +
                        "where bk.\"name\" like '%' || :bookName || '%' " +
                            "and au.id = :authorId and gr.id = :genreId", params, new BookMapper());
    }

    private static class BookMapper implements RowMapper<Book> {

        @Override
        public Book mapRow(ResultSet resultSet, int i) throws SQLException {
            long aId = resultSet.getLong("auid");
            String aName = resultSet.getString("auname");
            Author author = new Author(aId,aName,new ArrayList<>());
            long gId = resultSet.getLong("grid");
            String gName = resultSet.getString("grname");
            Genre genre = new Genre(gId,gName);
            long id = resultSet.getLong("bkid");
            String name = resultSet.getString("bkname");
            return new Book(id, name,author,genre);
        }
    }
}
