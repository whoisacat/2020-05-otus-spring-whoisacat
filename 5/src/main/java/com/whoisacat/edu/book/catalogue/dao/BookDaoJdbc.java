package com.whoisacat.edu.book.catalogue.dao;

import com.whoisacat.edu.book.catalogue.domain.Author;
import com.whoisacat.edu.book.catalogue.domain.Book;
import com.whoisacat.edu.book.catalogue.domain.Genre;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
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
    public int countAll() {
        return jdbc.getJdbcOperations().queryForObject("select count(*) from book",Integer.class);
    }

    @Override
    public int insert(Book book) {
        Map<String, Object> params = Map.of("id",getNewId(),
                "name", book.getName(),
                "author_id",book.getAuthor().getId(),
                "genre_id",book.getGenre().getId());
        return jdbc.update("insert into book (id, \"name\",author_id,genre_id) " +
                "values (:id, :name,:author_id,:genre_id)", params);
    }

    private long getNewId(){
        return jdbc.getJdbcOperations().queryForObject("select max(id) + 1 from book",Integer.class);
    }

    @Override
    public Book getById(long id) {
        MapSqlParameterSource params = new MapSqlParameterSource("id", id);
        Book book = null;
        try{

            book = jdbc.queryForObject(
                    "select bk.id as bkid,bk.\"name\" as bkname, " +
                            "au.id as auid,au.\"name\" as auname, " +
                            "gr.id as grid,gr.\"name\" as grname " +
                            "from book bk " +
                            "left join author au on bk.author_id = au.id " +
                            "left join genre gr on bk.genre_id = gr.id " +
                            "where bk.id = :id;", params, new BookMapper());
        }finally{
            return book;
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
