package com.example.payment_gateway_springboot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MerchantService {

    @Autowired
    private MerchantServerRepository merchantServerRepository;

    public boolean isValidServerKey(String serverKey) {
        // Check if the serverKey exists in the merchant table
        return merchantServerRepository.existsByServerKey(serverKey);
    }

    public String getMerchantIdByServerKey(String serverKey) {
        return merchantServerRepository.findMerchantIdByServerKey(serverKey).getMerchantId();
    }

}
