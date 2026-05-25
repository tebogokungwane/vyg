package com.vyg.dsa.porlym.SOLID.single;

import com.vyg.dsa.porlym.SOLID.liskov.Refundable;

public class CreditPayment implements PaymentMethod, Refundable {
    @Override
    public void pay(double amount) {
        System.out.println("Paying "+ amount + " using Credit");
    }

    @Override
    public void processRefund(double amount) {
        System.out.println("Refunding "+ amount + " using Credit");
    }
}
