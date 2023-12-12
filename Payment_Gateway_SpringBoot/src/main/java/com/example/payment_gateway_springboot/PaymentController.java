package com.example.payment_gateway_springboot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payment")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private MerchantService merchantService;

    @PostMapping("/make-payment")
    public ResponseEntity<ApiResponse> makePayment(
            @RequestHeader("Authorization") String serverKey,
            @RequestBody PaymentRequest paymentRequest
    ) {
        try {
            // Validate server key (you can implement a method to validate the key)
            if (!merchantService.isValidServerKey(serverKey)) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ApiResponse("Unauthorized. Invalid server key.", null));
            }
            // Process payment and associate merchant_id with payment data
            String paymentReference = paymentService.processPayment(paymentRequest, serverKey);

            ApiResponse response = new ApiResponse("Payment successful", paymentReference);
            return ResponseEntity.ok(response);
        } catch (PaymentValidationException e) {
            ApiResponse response = new ApiResponse(e.getMessage(), null);
            return ResponseEntity.status(e.getStatus()).body(response);
        } catch (PaymentNotFoundException e) {
            ApiResponse response = new ApiResponse(e.getMessage(), null);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } catch (Exception e) {
            ApiResponse response = new ApiResponse("Internal Server Error", null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping("/check-payment-status/{paymentReference}")
    public ResponseEntity<PaymentStatusResponse> checkPaymentStatus(
            @RequestHeader("Authorization") String serverKey,
            @PathVariable String paymentReference
    ) {
        try {
            // Validate server key (you can implement a method to validate the key)
            if (!merchantService.isValidServerKey(serverKey)) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new PaymentStatusResponse("Unauthorized. Invalid server key.", null));
            }

            // Check payment status
            PaymentStatus paymentStatus = paymentService.checkPaymentStatus(paymentReference);

            // Return the response
            PaymentStatusResponse response = new PaymentStatusResponse(paymentReference, paymentStatus);
            return ResponseEntity.ok(response);
        } catch (PaymentNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new PaymentStatusResponse(paymentReference, PaymentStatus.NOT_FOUND));
        }
    }
}
