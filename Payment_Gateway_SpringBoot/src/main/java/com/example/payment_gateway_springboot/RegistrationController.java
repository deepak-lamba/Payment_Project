package com.example.payment_gateway_springboot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/registration")
public class RegistrationController {

    @Autowired
    private MerchantServerRepository merchantServerRepository;

    @Value("${merchant.credentials}")
    private String validMerchantCredentials;

    @GetMapping("/register-server")
    public ResponseEntity<String> registerServer(@RequestHeader("Authorization") String authorization) {
        // Validate merchant credentials before allowing registration
        if (!isValidMerchantCredentials(authorization)) {
            return new ResponseEntity<>("Unauthorized. Invalid merchant credentials.", HttpStatus.UNAUTHORIZED);
        }

        // Generate a unique merchant ID and server key
        String merchantId = generateUniqueMerchantId();
        String serverKey = generateUniqueServerKey();

        // Save the merchant ID and server key in the database
        saveMerchantServer(merchantId, serverKey);

        return new ResponseEntity<>("Server registration successful. Merchant ID: " + merchantId +
                ", Server Key: " + serverKey, HttpStatus.OK);
    }

    private boolean isValidMerchantCredentials(String authorization) {
        // Implement validation logic, e.g., check against a predefined set of merchant credentials
        // Return true if credentials are valid, false otherwise
        // Note: In a production scenario, consider using more secure mechanisms like OAuth or API tokens.
        return validMerchantCredentials.equals(authorization);
    }


    private String generateUniqueMerchantId() {
        // Implement a method to generate a unique merchant ID
        return UUID.randomUUID().toString();
    }

    private String generateUniqueServerKey() {
        // Implement a method to generate a unique server key
        return UUID.randomUUID().toString();
    }

    private void saveMerchantServer(String merchantId, String serverKey) {
        // Save the merchant ID and server key in the database
        MerchantServer merchantServer = new MerchantServer();
        merchantServer.setMerchantId(merchantId);
        merchantServer.setServerKey(serverKey);
        merchantServerRepository.save(merchantServer);
    }
}
