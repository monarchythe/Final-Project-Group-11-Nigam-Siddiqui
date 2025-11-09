package com.smu.payment;

public class Payment {
    public String bookingId;
    public double amount;
    public String method;
    public String transactionId;
    public String status;

    public Payment() {}

    public Payment(String bookingId, double amount, String method, String transactionId, String status) {
        this.bookingId = bookingId;
        this.amount = amount;
        this.method = method;
        this.transactionId = transactionId;
        this.status = status;
    }
}
