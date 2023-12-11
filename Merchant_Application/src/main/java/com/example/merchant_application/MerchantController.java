package com.example.merchant_application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;


@RestController
@CrossOrigin(origins = "http://localhost:3000") // Allow requests from localhost:3000
public class MerchantController {

    private final WebClient webClient;

    @Autowired
    public MerchantController(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("http://localhost:8080").build();
    }

    @PostMapping("/make-payment")
    public Mono<ResponseEntity<String>> makePayment(@RequestBody PaymentRequest paymentRequest) {
        // Make a reactive HTTP request to the API Gateway's MakePayment API
        return webClient.post()
                .uri("/api/payment/make-payment")
                .body(Mono.just(paymentRequest), PaymentRequest.class)
                .retrieve()
                .bodyToMono(String.class)
                .map(ResponseEntity::ok)
                .onErrorResume(WebClientResponseException.class, e -> {
                    return Mono.just(ResponseEntity.status(e.getRawStatusCode()).body(e.getResponseBodyAsString()));
                });
    }

    @GetMapping("/check-payment-status/{paymentReference}")
    public Mono<ResponseEntity<String>> checkPaymentStatus(@PathVariable String paymentReference) {
        return webClient.get()
                .uri("/api/payment/check-payment-status/{paymentReference}", paymentReference)
                .retrieve()
                .bodyToMono(String.class)
                .map(ResponseEntity::ok)
                .onErrorResume(WebClientResponseException.class, e -> {
                    return Mono.just(ResponseEntity.status(e.getRawStatusCode()).body(e.getResponseBodyAsString()));
                });
    }
}


