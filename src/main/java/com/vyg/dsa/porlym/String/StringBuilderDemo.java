package com.vyg.dsa.porlym.String;

public class StringBuilderDemo {

    public static void main(String[] args) throws InterruptedException {

        StringBuilder sb = new StringBuilder("Hello");

        Thread t1 = new Thread(() -> sb.append(" World"));
        Thread t2 = new Thread(() -> sb.append(" Java"));

        t1.start();
        t2.start();

        t1.join();
        t2.join();

        System.out.println(sb);
    }
}
