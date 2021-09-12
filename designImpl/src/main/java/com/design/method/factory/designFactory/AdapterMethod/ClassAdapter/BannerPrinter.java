package com.design.method.factory.designFactory.AdapterMethod.ClassAdapter;

public class BannerPrinter extends Banner implements Printer {

    public BannerPrinter(String name) {
        super(name);
    }

    @Override
    public void weakPrint() {
      showWName();
    }

    @Override
    public void strongPrint() {
        showSName();
    }
}
