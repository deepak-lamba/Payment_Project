package com.example.payment_gateway_springboot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payment")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @PostMapping("/make-payment")
    public ResponseEntity<ApiResponse> makePayment(@RequestBody PaymentRequest paymentRequest) {
        try {
            String paymentReference = paymentService.processPayment(paymentRequest);
            ApiResponse response = new ApiResponse("Payment successful", paymentReference);
            return ResponseEntity.ok(response);
        } catch (PaymentValidationException e) {
            // Handle validation errors
            ApiResponse response = new ApiResponse(e.getMessage(), null);
            return ResponseEntity.status(e.getStatus()).body(response);
        } catch (PaymentNotFoundException e) {
            // Handle payment not found errors
            ApiResponse response = new ApiResponse(e.getMessage(), null);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } catch (Exception e) {
            // Handle other exceptions
            ApiResponse response = new ApiResponse("Internal Server Error", null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }


    @GetMapping("/check-payment-status/{paymentReference}")
    public ResponseEntity<PaymentStatusResponse> checkPaymentStatus(@PathVariable String paymentReference) {
        try {
            PaymentStatus paymentStatus = paymentService.checkPaymentStatus(paymentReference);
            PaymentStatusResponse response = new PaymentStatusResponse(paymentReference, paymentStatus);
            return ResponseEntity.ok(response);
        } catch (PaymentNotFoundException e) {
            // Handle the exception and return a response with a 404 status
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new PaymentStatusResponse(paymentReference, PaymentStatus.NOT_FOUND));
        }
    }

}
