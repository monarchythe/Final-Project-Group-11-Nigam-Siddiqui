package com.smu.payment;

import io.javalin.Javalin;
import java.util.UUID;

public class PaymentApp {
    public static void main(String[] args) {
        Javalin app = Javalin.create(cfg -> cfg.http.defaultContentType = "application/json")
                .start(8084);

        app.post("/payment/pay", ctx -> {
            Payment p = ctx.bodyAsClass(Payment.class);
            p.transactionId = UUID.randomUUID().toString();
            p.status = "success";
            ctx.status(200).json(p);
        });
    }
}
