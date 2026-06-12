package com.vyg.dsa.porlym.SOLID.single;

public class PaymentMethodRun {

    public static void main(String[] args) {
        PaymentMethod  creditPayment = new CreditPayment();
        creditPayment.pay(500);
    }
}
