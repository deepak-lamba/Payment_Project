package com.example.payment_gateway_springboot;

public class ApiResponse {

    private String message;
    private String paymentReference;

    // Constructor
    public ApiResponse(String paymentSuccessful, String paymentReference) {
        this.message = paymentSuccessful;
        this.paymentReference = paymentReference;
    }

    // Getter for 'message'
    public String getMessage() {
        return message;
    }

    // Setter for 'message'
    public void setMessage(String message) {
        this.message = message;
    }

    // Getter for 'paymentReference'
    public String getPaymentReference() {
        return paymentReference;
    }

    // Setter for 'paymentReference'
    public void setPaymentReference(String paymentReference) {
        this.paymentReference = paymentReference;
    }
}
