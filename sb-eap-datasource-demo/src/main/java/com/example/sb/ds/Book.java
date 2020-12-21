package com.example.sb.ds;

public class Book {
    private long id;
    private String title;
    private String author;

    public Book() {
        // no-op
    }

    public static Book of(long id, String title, String author) {
        Book newOne = new Book();
        newOne.id = id;
        newOne.title = title;
        newOne.author = author;
        return newOne;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}
