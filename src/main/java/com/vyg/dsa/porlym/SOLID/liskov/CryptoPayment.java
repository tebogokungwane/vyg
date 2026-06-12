package com.vyg.dsa.porlym.SOLID.liskov;

import com.vyg.dsa.porlym.SOLID.single.PaymentMethod;

public class CryptoPayment implements PaymentMethod {


    @Override
    public void pay(double amount) {
        System.out.println("Paid "+ amount + " using Crypto Payment");
    }
}
