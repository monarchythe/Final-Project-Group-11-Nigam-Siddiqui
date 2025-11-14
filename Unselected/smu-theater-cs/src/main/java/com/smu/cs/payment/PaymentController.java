package com.smu.cs.payment;

import com.smu.cs.models.Payment;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/payment")
public class PaymentController {

    @PostMapping("/pay")
    public Payment processPayment(@RequestBody Payment paymentRequest) {

        // Generate a fake transaction ID for this payment
        String transactionId = UUID.randomUUID().toString();

        // Build a successful payment response.
        // ⚠️ This order matches your constructor:
        // Payment(String transactionId, double amount, String method, String bookingId, String status)
        return new Payment(
                transactionId,
                paymentRequest.amount,
                paymentRequest.method,
                paymentRequest.bookingId,
                "success"
        );
    }
}
