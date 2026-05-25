package com.vyg.dsa.porlym;

public class RUn {
    public static void main(String[] args) {

        Runnable runComputer = new Computer();
        runComputer.run();

        Runnable runComputer2 = new Human();
        runComputer2.run();
    }
}
