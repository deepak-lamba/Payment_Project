// AppRouter.js
import React from 'react';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import PaymentForm from './components/PaymentForm';
import PaymentStatusCheck from './components/PaymentStatusCheck';

const AppRouter = () => {
    return (
        <Router>
            <Routes>
                <Route path="/" element={<PaymentForm />} />
                <Route path="/payment-status" element={<PaymentStatusCheck />} />
            </Routes>
        </Router>
    );
};

export default AppRouter;
