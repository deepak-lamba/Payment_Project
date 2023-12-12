package com.example.payment_gateway_springboot;

import jakarta.persistence.*;

@Entity
@Table(name = "merchant")
public class MerchantServer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String merchantId;
    private String serverKey;
    public void setServerKey(String serverKey) {
        this.serverKey=serverKey;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId=merchantId;
    }

    // Getters and setters
    public String getMerchantId() {
        return merchantId;
    }
}
