// PaymentForm.js
import React, { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';
import '../PaymentForm.css'; // Import the CSS file for styling

const PaymentForm = () => {
    useEffect(() => {
        document.title = 'Payments';
        return () => {
            // Cleanup if necessary
        };
    }, []);

    const [amount, setAmount] = useState('');
    const [cardNumber, setCardNumber] = useState('');
    const [cardHolderName, setCardHolderName] = useState('');
    const [expiryDate, setExpiryDate] = useState('');
    const [cvv, setCvv] = useState('');
    const [paymentReference, setPaymentReference] = useState('');
    const [paymentStatus, setPaymentStatus] = useState('');
    const [error, setError] = useState(null);

    const makePayment = async () => {
        try {
            setError(null);

            if (!validateForm()) {
                return;
            }

            const paymentDetails = {
                amount: parseInt(amount),
                cardNumber,
                cardHolderName,
                expiryDate,
                cvv,
            };

            const response = await fetch('http://localhost:8081/make-payment', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(paymentDetails),
            });

            if (!response.ok) {
                const errorData = await response.json();
                setError(errorData);
                return;
            }

            const data = await response.json();
            setPaymentReference(data.paymentReference);
            setPaymentStatus(data.message);
        } catch (error) {
            setError(error);
        }
    };

    const validateForm = () => {
        return amount && cardNumber && cardHolderName && expiryDate && cvv;
    };

    return (
        <div className="payment-form-container">
            <h2>Payment Form</h2>

            <label>Amount:</label>
            <input type="number" value={amount} onChange={(e) => setAmount(e.target.value)} />

            <label>Card Number:</label>
            <input type="text" value={cardNumber} onChange={(e) => setCardNumber(e.target.value)} />

            <label>Card Holder Name:</label>
            <input type="text" value={cardHolderName} onChange={(e) => setCardHolderName(e.target.value)} />

            <label>Expiry Date:</label>
            <input type="text" value={expiryDate} onChange={(e) => setExpiryDate(e.target.value)} />

            <label>CVV:</label>
            <input type="text" value={cvv} onChange={(e) => setCvv(e.target.value)} />

            <button onClick={makePayment}>Make Payment</button>
            <p>Payment Reference: {paymentReference}</p>

            <Link to="/payment-status">Check Payment Status</Link>

            {error && (
                <div className="error-message">
                    <p>Error Message: {error.message}</p>
                    <p>Payment Reference: {error.paymentReference}</p>
                </div>
            )}
        </div>
    );
};

export default PaymentForm;
