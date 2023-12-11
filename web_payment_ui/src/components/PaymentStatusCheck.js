// PaymentStatusCheck.js
import React, {useEffect, useState} from 'react';
import { Link } from 'react-router-dom';
import '../PaymentStatusCheck.css'; // Import the CSS file for styling

const PaymentStatusCheck = () => {
    useEffect(() => {
        document.title = 'Payments';
        return () => {
            // Cleanup if necessary
        };
    }, []);
    const [paymentReference, setPaymentReference] = useState('');
    const [paymentStatus, setPaymentStatus] = useState('');
    const [error, setError] = useState(null);

    const checkPaymentStatus = async () => {
        try {
            // Clear previous errors
            setError(null);

            // Validate form fields before checking payment status
            if (!paymentReference) {
                setError({ message: 'Payment Reference is required' });
                return;
            }

            // Make a GET request to the Merchant Application Backend to check payment status
            const response = await fetch(`http://localhost:8081/check-payment-status/${paymentReference}`);

            if (!response.ok) {
                // Handle 404 Not Found separately
                if (response.status === 404) {
                    const data = await response.json();
                    setPaymentStatus(data.paymentStatus);
                } else {
                    throw await response.json();
                }
            } else {
                const data = await response.json();
                setPaymentStatus(data.paymentStatus);
            }
        } catch (error) {
            setError(error);
        }
    };

    return (
        <div className="payment-status-check-container">
            <h2>Payment Check</h2>
            <label>Enter Payment Reference:</label>
            <input
                type="text"
                value={paymentReference}
                onChange={(e) => setPaymentReference(e.target.value)}
            />
            <button onClick={checkPaymentStatus}>Check Payment Status</button>

            {/* Display payment status or error if any */}
            {paymentStatus && (
                <p>Payment Status: {paymentStatus}</p>
            )}

            {/* Add Link to Homepage */}
            <Link to="/">Go to Homepage</Link>

            {error && (
                <div style={{ color: 'red' }}>
                    <p>Error Message: {error.message}</p>
                </div>
            )}
        </div>
    );
};

export default PaymentStatusCheck;
