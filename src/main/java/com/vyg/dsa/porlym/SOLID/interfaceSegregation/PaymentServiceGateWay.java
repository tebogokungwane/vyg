package com.vyg.dsa.porlym.SOLID.interfaceSegregation;

import com.vyg.dsa.porlym.SOLID.open.PaymentService;
import com.vyg.dsa.porlym.SOLID.single.PaymentMethod;

public class PaymentServiceGateWay {

    private final PaymentMethod paymentMethod;

    public PaymentServiceGateWay(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public void pay(double amount) {
        paymentMethod.pay(amount);
    }
}
