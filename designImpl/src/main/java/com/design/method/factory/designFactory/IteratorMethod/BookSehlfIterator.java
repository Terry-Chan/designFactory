package com.design.method.factory.designFactory.IteratorMethod;

public class BookSehlfIterator implements DefineIterator<Book> {

    private BookShelf bookShelf;

    private int index;

    public BookSehlfIterator(BookShelf bookShelf) {
        this.bookShelf = bookShelf;
        this.index = 0;
    }


    @Override
    public boolean haveNext() {
        if (index < bookShelf.getLength()) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Book next() {
        Book book = bookShelf.getBook(index);
        index ++;
        return book;
    }
}
