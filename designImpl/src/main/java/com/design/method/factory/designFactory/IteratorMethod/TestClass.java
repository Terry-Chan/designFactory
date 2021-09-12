package com.design.method.factory.designFactory.IteratorMethod;

public class TestClass {

    public static void main(String[] args) {
        BookShelf bookShelf = new BookShelf(4);
        bookShelf.addBook(new Book("AA"));
        bookShelf.addBook(new Book("BB"));
        bookShelf.addBook(new Book("cc"));
        bookShelf.addBook(new Book("dd"));
        DefineIterator<Book> it = bookShelf.iterator();

        while (it.haveNext()) {
            System.out.println(it.next().getName());
        }


    }
}
