package com.whoisacat.edu.book.catalogue.dao;

import com.whoisacat.edu.book.catalogue.domain.Author;
import com.whoisacat.edu.book.catalogue.domain.Book;
import com.whoisacat.edu.book.catalogue.domain.Genre;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@SuppressWarnings({"SqlNoDataSourceInspection", "ConstantConditions", "SqlDialectInspection"})
@Repository
public class AuthorDaoJdbc implements AuthorDao{

    private final NamedParameterJdbcOperations jdbc;


    public AuthorDaoJdbc(NamedParameterJdbcOperations namedParameterJdbcOperations){
        this.jdbc = namedParameterJdbcOperations;
    }

    @Override
    public long count() {
        return jdbc.getJdbcOperations().queryForObject("select count(*) from author",Integer.class);
    }

    @Override
    public Long insert(Author author) {
        SqlParameterSource params = new MapSqlParameterSource(Map.of("name",author.getName()));
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbc.update("insert into author (\"name\") values (:name)", params, keyHolder);
        return keyHolder.getKey().longValue();
    }

    @Override
    public Author getById(long id) {
        MapSqlParameterSource params = new MapSqlParameterSource("id", id);
        return jdbc.queryForObject("select * from author where id = :id",
                params,new AuthorMapper());
    }

    @Override
    public List<Author> getByName(String name) {
        MapSqlParameterSource params = new MapSqlParameterSource("name", name);
        return jdbc.query("select * from author where \"name\" like '%' || :name || '%'",
                params, new AuthorMapper());
    }

    @Override
    public List<Author> getAll() {
        Map<Long, Author> authors = jdbc.query(
                "select bk.id as bkid,bk.\"name\" as bkname, " +
                            "au.id as auid,au.\"name\" as auname, " +
                            "gr.id as grid,gr.\"name\" as grname " +
                        "from book bk " +
                            "left join author au on bk.author_id = au.id " +
                            "left join genre gr on bk.genre_id = gr.id;",
                new AuthorResultExtractor());
        return new ArrayList<>(authors.values());
    }

    @Override
    public void deleteById(long id) {
        Map<String, Object> params = Collections.singletonMap("id", id);
        jdbc.update(
                "delete from author where id = :id", params
        );
    }

    private static class AuthorMapper implements RowMapper<Author> {

        @Override
        public Author mapRow(ResultSet resultSet, int i) throws SQLException {
            long id = resultSet.getLong("id");
            String name = resultSet.getString("name");
            return new Author(id, name,new ArrayList<>());
        }
    }

    private static class AuthorResultExtractor implements ResultSetExtractor<Map<Long, Author>>{

        @Override public Map<Long, Author> extractData(ResultSet rs) throws SQLException, DataAccessException{
            Map<Long, Author> authors = new HashMap<>();
            while(rs.next()){
                long id = rs.getLong("auid");
                Author author = authors.get(id);
                if(author == null){
                    author = new Author(id,rs.getString("auname"),new ArrayList<Book>());
                    authors.put(id,author);
                }
                author.getBooks().add(new Book(rs.getLong("bkid"),rs.getString("bkname"),
                        author,
                        new Genre(rs.getLong("grid"),rs.getString("grname"))));
            }
            return authors;
        }
    }
}
