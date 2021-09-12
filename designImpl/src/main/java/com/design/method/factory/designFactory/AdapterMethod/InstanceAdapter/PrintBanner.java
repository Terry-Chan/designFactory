package com.design.method.factory.designFactory.AdapterMethod.InstanceAdapter;

public class PrintBanner implements Print {

    private Banner banner;

    public PrintBanner(String name) {
        this.banner = new Banner(name);
    }


    @Override
    public void weekPrint() {
        banner.showWName();
    }

    @Override
    public void strongPrint() {
        banner.showSName();
    }
}
