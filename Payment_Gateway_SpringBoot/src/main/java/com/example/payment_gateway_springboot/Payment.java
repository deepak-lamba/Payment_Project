package com.example.payment_gateway_springboot;

import jakarta.persistence.*;

@Entity
@Table(name = "payment")
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String cardNumber;
    private String cardHolderName;
    private String expiryDate;
    private String cvv;
    private int amount;

    @Enumerated(EnumType.STRING)
    private PaymentStatus status;

    private String paymentReference;

    public Payment(String cardNumber, String cardHolderName, String expiryDate, PaymentStatus status, String cvv, int amount) {
        this.cardNumber = cardNumber;
        this.cardHolderName = cardHolderName;
        this.expiryDate = expiryDate;
        this.cvv = cvv;
        this.amount = amount;
        this.status = status;
        // other initializations if needed
    }

    public Payment() {

    }

    public Long getId() {
        return id;
    }

    public PaymentStatus getStatus() {
        return status;
    }

    public String getPaymentReference(){
        return paymentReference;
    }

    public void setPaymentReference(String paymentReference) {
        this.paymentReference=paymentReference;
    }

    // Constructors, getters, and setters
}
