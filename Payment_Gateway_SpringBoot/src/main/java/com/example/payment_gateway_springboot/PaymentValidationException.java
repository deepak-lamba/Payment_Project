package com.example.payment_gateway_springboot;

import org.springframework.http.HttpStatus;

public class PaymentValidationException extends RuntimeException {
    private final HttpStatus httpStatus;

    public PaymentValidationException(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public int getStatus() {
        return httpStatus.value();
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }
}
