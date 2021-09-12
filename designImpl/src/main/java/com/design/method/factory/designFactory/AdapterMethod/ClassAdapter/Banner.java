package com.design.method.factory.designFactory.AdapterMethod.ClassAdapter;

public class Banner {
    private String name;

    public Banner(String name) {
        this.name = name;
    }

    public void showWName() {
        System.out.println("(" + this.name + ")");
    }

    public void showSName() {
        System.out.println("*" + this.name + "*");
    }
}
