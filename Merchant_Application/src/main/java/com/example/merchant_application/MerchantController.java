package com.example.merchant_application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

@Controller
@CrossOrigin(origins = "http://localhost:3000") // Allow requests from localhost:3000
public class MerchantController {

    private final WebClient webClient;

    @Value("${merchant-server.merchantId}")
    private String merchantId;

    @Value("${merchant-server.serverKey}")
    private String serverKey;

    @Autowired
    public MerchantController(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("http://localhost:8080").build();
    }

    @PostMapping("/make-payment")
    public Mono<ResponseEntity<String>> makePayment(@RequestBody PaymentRequest paymentRequest) {
        // Make a reactive HTTP request to the API Gateway's MakePayment API with Authorization header
        return webClient.post()
                .uri("/api/payment/make-payment")
                .header(HttpHeaders.AUTHORIZATION, serverKey)
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
        // Make a reactive HTTP request to the API Gateway's CheckPaymentStatus API with Authorization header
        return webClient.get()
                .uri("/api/payment/check-payment-status/{paymentReference}", paymentReference)
                .header(HttpHeaders.AUTHORIZATION, serverKey)
                .retrieve()
                .bodyToMono(String.class)
                .map(ResponseEntity::ok)
                .onErrorResume(WebClientResponseException.class, e -> {
                    return Mono.just(ResponseEntity.status(e.getRawStatusCode()).body(e.getResponseBodyAsString()));
                });
    }
}
