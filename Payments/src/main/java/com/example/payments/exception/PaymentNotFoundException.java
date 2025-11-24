package com.example.payments.exception;

public class PaymentNotFoundException extends RuntimeException{

    public PaymentNotFoundException(String message) { super(message); }
}
