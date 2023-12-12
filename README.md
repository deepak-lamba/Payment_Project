# Payment System

This repository contains three components of a simple payment system: Payment Gateway Server, Merchant Application Server, and Payment UI Web.

### Note: Running the Components

All three components of the payment system should be run independently on separate ports.

## Table of Contents
- [Prerequisites](#prerequisites)
- [Project setup](##Projectsetup)
    - [MySQL database setup and connection](###MySQLdatabasesetupandconnection)
    - [Payment Gateway Server setup](###PaymentGatewayServer)
    - [Merchant Application Server](###MerchantApplicationServer)
    - [Payment Web Interface](##PaymentWebInterface)

## Prerequisites
Before you begin, make sure you have the following tools and software installed:
1) Node.js
2) npm
3) MySQL
4) Java
5) Maven

Also, ensure that paths to 'mvn' and 'java' binaries are added to your system's PATH environment variable.

## Project setup
Clone the repository and navigate to the project root:
   ```bash
   git clone https://github.com/deepak-lamba/Payment_Project.git;
   cd Payment_Project;
```

### MySQL database setup and connection
The payment Gateway uses a MySQL database to store payment-related information.
1. Ensure you have MySQL installed on your machine or accessible in your network.
2. Create a new database for the payment system. You can use a MySQL client or the command line:
   ```sql
   CREATE DATABASE payment_gateway_db;
   ```
   Replace `your-mysql-username` and `your-mysql-password` with your MySQL username and password.
3. Update the database configuration settings in the [application.properties](Payment_Gateway_SpringBoot/src/main/resources/application.properties) file of Payment_Gateway_SpringBoot dir.


### Payment Gateway Server
The Payment Gateway Server handles payment processing logic and communication between the Merchant Application and Payment UI.

##### Installation and Setup
1) Navigate to the Payment Gateway Server dir: `cd Payment_Gateway_SpringBoot`
2) Start Payment_Gateway_SpringBoot Server: `mvn spring-boot:run`
   The server will be running on http://localhost:8080 by default.


### Merchant Application Server
The Merchant Application Server is responsible for handling merchant-specific logic and interactions.

##### Installation and Setup
1) Navigate to the Merchant Application Server directory: `cd Merchant_Application`


##### Merchant Application Server Registration
1. Ensure that the Payment Gateway Server is running and accessible before initiating the registration process.
2. To integrate your Merchant Application Server with the Payment Gateway, you need to register your server with the Payment Gateway Server. Follow the steps below:
    1. Server Registration:
       To register your Merchant Application Server on the Payment Gateway Server, use the following endpoint with specified authorization header in your HTTP request :
        ```
        curl http://localhost:8080/api/registration/register-server 
        -H "Authorization: hash@access@register"
        ```
    2. Server credentials update:
       Upon successful registration, the Payment Gateway Server will respond with the following information:

   `{"merchantId": "your-merchant-id", "serverKey": "your-server-key"}`

   Add these values in the [application.properties](Merchant_Application/src/main/resources/application.properties) file of your Merchant SpringBoot Application against `payment.gateway.merchant.id` and `payment.gateway.server.key` resp.

2) Start Merchant_Application Server: `mvn spring-boot:run`
   The server will be running on http://localhost:8081 by default.


## Payment Web Interface
The Payment UI Web is a React-based web application for users to make payments and check payment status.
1) Navigate to the Merchant Application Server directory: `cd web_payment_ui`
2) Install dependencies and run: `npm install && npm start` (you may need to fix some package installation with `npm audit fix --force` as suggested by npm)
   The web application will be accessible at http://localhost:3000 by default.
3) Congratulations, you can attempt to make payments now!!
   There is card number validation in place, so an example of a successful and fake card number is: 5555555555554444.
   Also check the payment status after making a successful payment.
