package com.design.method.factory.designFactory.IteratorMethod;

public class BookShelf implements Aggreatevie<Book> {

    public  Book[] books;

    public  int index = 0;


    public BookShelf (int max) {
        this.books = new Book[max];
    }

    public Book getBook(int index) {
        return books[index];
    }

    public void addBook(Book book) {
        this.books[index] = book;
        index ++;
    }

    public int getLength() {
        return index;
    }


    @Override
    public DefineIterator<Book> iterator() {
        return new BookSehlfIterator(this);
    }


}
