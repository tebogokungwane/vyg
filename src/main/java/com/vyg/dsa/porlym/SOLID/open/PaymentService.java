package com.vyg.dsa.porlym.SOLID.open;

import com.vyg.dsa.porlym.SOLID.single.PaymentMethod;

public class PaymentService {

    private final PaymentMethod paymentMethod;

    public PaymentService(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public void processPayment(double amount) {
        paymentMethod.pay(amount);
    }
}
