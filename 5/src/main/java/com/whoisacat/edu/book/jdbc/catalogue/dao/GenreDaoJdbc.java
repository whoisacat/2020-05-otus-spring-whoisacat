package com.whoisacat.edu.book.jdbc.catalogue.dao;

import com.whoisacat.edu.book.jdbc.catalogue.domain.Genre;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@SuppressWarnings({"SqlNoDataSourceInspection", "ConstantConditions", "SqlDialectInspection"})
@Repository
public class GenreDaoJdbc implements GenreDao{

    private final NamedParameterJdbcOperations jdbc;

    public GenreDaoJdbc(NamedParameterJdbcOperations namedParameterJdbcOperations)
    {
        this.jdbc = namedParameterJdbcOperations;
    }

    @Override
    public long count() {
        return jdbc.getJdbcOperations().queryForObject("select count(*) from genre",Integer.class);
    }

    @Override
    public Long insert(Genre genre) {
        if(!getByName(genre.getName()).isEmpty()){
            return null;
        }
        SqlParameterSource params = new MapSqlParameterSource(Map.of("name",genre.getName()));
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbc.update("insert into genre (\"name\") values (:name)", params, keyHolder);
        return keyHolder.getKey().longValue();
    }

    @Override
    public Genre getById(long id) {
        MapSqlParameterSource params = new MapSqlParameterSource("id", id);
        return jdbc.queryForObject("select * from genre where id = :id",
                params, new GenreMapper());
    }

    @Override
    public List<Genre> getByName(String name) {
        MapSqlParameterSource params = new MapSqlParameterSource("name", name);
        return jdbc.query("select * from genre where \"name\" = :name",
                params, new GenreMapper());
    }

    @Override
    public List<Genre> getAll() {
        return jdbc.query("select * from genre", new GenreMapper());
    }

    @Override
    public void deleteById(long id) {
        Map<String, Object> params = Collections.singletonMap("id", id);
        jdbc.update(
                "delete from genre where id = :id", params
        );
    }

    private static class GenreMapper implements RowMapper<Genre> {

        @Override
        public Genre mapRow(ResultSet resultSet, int i) throws SQLException {
            long id = resultSet.getLong("id");
            String name = resultSet.getString("name");
            return new Genre(id, name);
        }
    }
}
