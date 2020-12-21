package com.example.sb.ds;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class JdbcBookRepository implements BookRepository {

    private final JdbcTemplate jdbcTemplate;

    private final RowMapper<Book> bookRowMapper =  (resultSet, rowNum) ->
        Book.of(resultSet.getLong("id"), resultSet.getString("title"), resultSet.getString("author"));

    public JdbcBookRepository(JdbcTemplate jdbcTeamplate) {
        this.jdbcTemplate = jdbcTeamplate;
    }

    @Override
    public Book find(long id) {
        return jdbcTemplate.queryForObject("select id, title, author from book where id = ?", bookRowMapper, id);
    }

    @Override
    @Transactional
    public int insert(Book book) {
        return jdbcTemplate.update("insert into book values (?, ?, ?)", book.getId(), book.getTitle(), book.getAuthor());
    }
}
