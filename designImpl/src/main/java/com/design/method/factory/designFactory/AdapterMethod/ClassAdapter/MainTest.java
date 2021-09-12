package com.design.method.factory.designFactory.AdapterMethod.ClassAdapter;

public class MainTest {

    public static void main(String[] args) {
        Printer printer = new BannerPrinter("Hello_World");
        printer.strongPrint();
        printer.weakPrint();

    }
}
