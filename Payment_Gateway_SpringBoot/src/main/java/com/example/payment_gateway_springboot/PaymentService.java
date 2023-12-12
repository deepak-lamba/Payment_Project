package com.example.payment_gateway_springboot;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

@Service
@Transactional
public class PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private MerchantService merchantService;

    public String processPayment(PaymentRequest paymentRequest, String serverKey) {
        try {
            // Perform payment processing logic (e.g., validation, charging the card, etc.)
            // Fetch merchant_id using server_key
            String merchantId = merchantService.getMerchantIdByServerKey(serverKey);
            // Save payment details to the database
            // Perform card payment processing logic
            validateCardDetails(paymentRequest);

            // Save payment details to the database
            Payment payment = new Payment(
                    paymentRequest.getCardNumber(),
                    paymentRequest.getCardHolderName(),
                    paymentRequest.getExpiryDate(),
                    PaymentStatus.PROCESSING,
                    paymentRequest.getCvv(),
                    paymentRequest.getAmount(),
                    merchantId
            );
            paymentRepository.save(payment);
            payment.setPaymentReference(generatePaymentReference(payment.getId()));
            // Generate and return payment reference
            return generatePaymentReference(payment.getId());
        } catch (PaymentValidationException e) {
            // Handle validation errors
            throw new PaymentValidationException(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            // Handle other exceptions
            throw new RuntimeException("Internal Server Error", e);
        }
    }

    public PaymentStatus checkPaymentStatus(String paymentReference) {
        // Retrieve payment details from the database using payment reference
        Payment payment = paymentRepository.findByPaymentReference(paymentReference);

        if (payment == null) {
            throw new PaymentNotFoundException("Payment not found");
        }

        return payment.getStatus();
    }

    private void validateCardDetails(PaymentRequest paymentRequest) throws PaymentValidationException {
        try {
            validateLuhnAlgorithm(paymentRequest.getCardNumber());
            validateExpirationDate(paymentRequest.getExpiryDate());
            // Add any other necessary validations...

            // If any validation fails, throw a PaymentValidationException
        } catch (PaymentValidationException e) {
            throw e; // Re-throw PaymentValidationException to maintain the HTTP status code
        } catch (Exception e) {
            throw new RuntimeException("Internal Server Error", e);
            // Add any other necessary validations...

            // If any validation fails, throw a PaymentValidationException
        }
    }
        private void validateLuhnAlgorithm(String cardNumber) throws PaymentValidationException {
            // Implement Luhn algorithm for credit card number validation
            // Example: https://en.wikipedia.org/wiki/Luhn_algorithm

            // Convert the card number to an array of integers
            int[] digits = new int[cardNumber.length()];
            for (int i = 0; i < cardNumber.length(); i++) {
                digits[i] = Character.getNumericValue(cardNumber.charAt(i));
            }

            // Starting from the rightmost digit, double every second digit
            for (int i = digits.length - 2; i >= 0; i -= 2) {
                int doubledDigit = digits[i] * 2;
                // If doubling results in a two-digit number, subtract 9
                digits[i] = (doubledDigit > 9) ? (doubledDigit - 9) : doubledDigit;
            }

            // Sum all the digits
            int sum = 0;
            for (int digit : digits) {
                sum += digit;
            }

            // The card number is valid if the sum is a multiple of 10
            if (sum % 10 != 0) {
                throw new PaymentValidationException("Invalid credit card number", HttpStatus.BAD_REQUEST);
            }
        }

        private void validateExpirationDate(String expiryDate) throws PaymentValidationException {
            try {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/yy");
                YearMonth expirationYearMonth = YearMonth.parse(expiryDate, formatter);

                if (expirationYearMonth.isBefore(YearMonth.now())) {
                    throw new PaymentValidationException("Credit card has expired", HttpStatus.BAD_REQUEST);
                }
            } catch (DateTimeParseException e) {
                throw new PaymentValidationException("Invalid expiration date format", HttpStatus.BAD_REQUEST);
            }
        }
        private String generatePaymentReference(Long paymentId) {
            // Generate a unique payment reference based on paymentId
            // This can be more sophisticated based on your requirements
            return "PAY-" + paymentId;
        }

        public Payment findPaymentByReference(String paymentReference) {
            return paymentRepository.findByPaymentReference(paymentReference);
        }
    }
