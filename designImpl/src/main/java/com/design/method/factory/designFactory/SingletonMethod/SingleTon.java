package com.design.method.factory.designFactory.SingletonMethod;

/**
 * avoid space change time and thread safe issue
 */
public class SingleTon {


    private SingleTon(){}

    private static class SingleTonHolder {
        private static SingleTon singleTon = new SingleTon();
    }

    public SingleTon getInstance() {
        return SingleTonHolder.singleTon;
    }


}
