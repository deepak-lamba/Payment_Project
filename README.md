# Payment System

This repository contains three components of a simple payment system: Payment Gateway Server, Merchant Application Server, and Payment UI Web.

## Payment Gateway Server

The Payment Gateway Server handles payment processing logic and communication between the Merchant Application and Payment UI.

### Installation and Setup

1. Clone the repository:

   ```bash
   git clone https://github.com/deepak-lamba/Payment_Project.git

1) Navigate to the Payment Gateway Server directory:

cd Payment_Project/Payment_Gateway_SpringBoot

2) Install dependencies:

npm install

## MySQL DB Schema and Connection

## MySQL Database

The payment Gateway uses a MySQL database to store payment-related information.

### Database Connection

1. Ensure you have MySQL installed on your machine or accessible in your network.

2. Create a new database for the payment system. You can use a MySQL client or the command line:

   ```sql
   CREATE DATABASE payment_gateway_db;

Replace `your-mysql-username` and `your-mysql-password` with your MySQL username and password. Make sure to update the database configuration settings in the `config.js` files of Payment_Gateway_SpringBoot

#### The server will be running on http://localhost:8080 by default.

## Merchant Application Server
The Merchant Application Server is responsible for handling merchant-specific logic and interactions.

1) Navigate to the Merchant Application Server directory:

cd Payment_Project/Merchant_Application

2) Install dependencies:

npm install

#### The server will be running on http://localhost:8081 by default.

## Merchant Server Registration

To integrate your Merchant Application Server with the Payment Gateway, you need to register your server with the Payment Gateway Server. Follow the steps below:

1. **Server Registration Endpoint:**

   Use the following endpoint to register your Merchant Application Server on the Payment Gateway Server:

GET http://localhost:8080/api/registration/register-server

2. **Authorization Header:**

Include the following Authorization header in your HTTP request:

Authorization: hash@access@register


This header is required for authentication.

3. **Response:**

Upon successful registration, the Payment Gateway Server will respond with the following information:

{
  "merchantId": "your-merchant-id",
  "serverKey": "your-server-key"
}

Make sure to store these values in the application.properties file of your Merchant SpringBoot Application.
### application.properties

#### payment.gateway.merchant.id=your-merchant-id,
#### payment.gateway.server.key=your-server-key

Note: Ensure that the Payment Gateway Server is running and accessible before initiating the registration process.

## Payment UI Web
The Payment UI Web is a React-based web application for users to make payments and check payment status.

1) Start the Payment UI Web

npm start

#### The web application will be accessible at http://localhost:3000 by default.