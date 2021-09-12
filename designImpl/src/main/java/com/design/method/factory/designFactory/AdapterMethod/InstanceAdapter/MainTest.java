package com.design.method.factory.designFactory.AdapterMethod.InstanceAdapter;

import com.design.method.factory.designFactory.AdapterMethod.ClassAdapter.BannerPrinter;
import com.design.method.factory.designFactory.AdapterMethod.ClassAdapter.Printer;

public class MainTest {

    public static void main(String[] args) {
        Printer printer = new BannerPrinter("Hello_World");
        printer.strongPrint();
        printer.weakPrint();

    }
}
