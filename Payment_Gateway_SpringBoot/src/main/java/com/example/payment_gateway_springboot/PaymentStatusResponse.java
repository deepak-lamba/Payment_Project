package com.example.payment_gateway_springboot;

public class PaymentStatusResponse {

    private String paymentReference;
    private PaymentStatus status;

    public PaymentStatusResponse(String paymentReference, PaymentStatus paymentStatus) {
        this.paymentReference=paymentReference;
        this.status=paymentStatus;
    }

    // Constructors, getters, and setters

    // Getter for 'paymentReference'
    public String getPaymentReference() {
        return paymentReference;
    }

    // Getter for 'PaymentStatus'
    public PaymentStatus getPaymentStatus() {
        return status;
    }
}
