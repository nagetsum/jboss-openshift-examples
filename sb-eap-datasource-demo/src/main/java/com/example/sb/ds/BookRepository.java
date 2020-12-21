package com.example.sb.ds;

public interface BookRepository {
    public Book find(long id);
    public int insert(Book book);
}
