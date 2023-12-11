package com.example.payment_gateway_springboot;

public class PaymentRequest {

    private int amount;
    private String cardNumber;
    private String cardHolderName;
    private String expiryDate;
    private String cvv;

    public PaymentRequest(int amount, String cardNumber, String cardHolderName, String expiryDate, String cvv) {
        this.amount = amount;
        this.cardNumber = cardNumber;
        this.cardHolderName = cardHolderName;
        this.expiryDate = expiryDate;
        this.cvv = cvv;
    }

    public int getAmount() {
        return amount;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public String getCardHolderName() {
        return cardHolderName;
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    public String getCvv() {
        return cvv;
    }

    // Setters (you should include setters for each field)
    public void setAmount(int amount) {
        this.amount = amount;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public void setCardHolderName(String cardHolderName) {
        this.cardHolderName = cardHolderName;
    }

    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }

    public void setCvv(String cvv) {
        this.cvv = cvv;
    }
}
