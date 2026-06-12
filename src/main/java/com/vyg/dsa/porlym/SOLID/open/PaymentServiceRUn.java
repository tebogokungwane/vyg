package com.vyg.dsa.porlym.SOLID.open;

import com.vyg.dsa.porlym.SOLID.single.CreditPayment;

public class PaymentServiceRUn {

    public static void main(String[] args) {

        CreditPayment creditPayment = new CreditPayment();
        PaymentService paymentService = new PaymentService(creditPayment);
        paymentService.processPayment(500);
    }
}
