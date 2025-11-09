package com.smu.gateway;


import io.javalin.Javalin;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;


public class App {
    public static void main(String[] args) {


//        Javalin app = Javalin.create(cfg -> {
//            cfg.http.defaultContentType = "application/json";
//        }).start(8080);

        Javalin app = Javalin.create(cfg -> {
            cfg.http.defaultContentType = "application/json";

            // ✅ Official supported CORS pattern for Javalin 5.6.x
            cfg.plugins.enableCors(cors -> {
                cors.add(it -> {
                    it.anyHost(); // allow all origins
                });
            });
        }).start(8080);







        app.get("/health", ctx -> ctx.result("{\"status\":\"ok\"}"));

        //events rout--------------------------------------------------
        // proxy all events requests to event-service (port 8081)
        app.get("/events", ctx -> {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest req = HttpRequest.newBuilder(
                    URI.create("http://localhost:8081/events")
            ).build();
            HttpResponse<String> resp = client.send(req, HttpResponse.BodyHandlers.ofString());
            ctx.status(resp.statusCode()).result(resp.body());
        });

        app.get("/events/{id}", ctx -> {
            String id = ctx.pathParam("id");
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest req = HttpRequest.newBuilder(
                    URI.create("http://localhost:8081/events/" + id)
            ).build();
            HttpResponse<String> resp = client.send(req, HttpResponse.BodyHandlers.ofString());
            ctx.status(resp.statusCode()).result(resp.body());
        });

        //auth service route------------------------------
        // AUTH SERVICE PROXY (port 8082)
        app.post("/auth/register", ctx -> {
            try {
                String body = ctx.body();
                System.out.println("[Gateway] Forwarding /auth/register to Auth Service...");
                HttpClient client = HttpClient.newHttpClient();

                HttpRequest req = HttpRequest.newBuilder()
                        .uri(URI.create("http://localhost:8082/auth/register"))
                        .header("Content-Type", "application/json")
                        .POST(HttpRequest.BodyPublishers.ofString(body))
                        .build();

                HttpResponse<String> resp = client.send(req, HttpResponse.BodyHandlers.ofString());
                ctx.status(resp.statusCode()).result(resp.body());
            } catch (Exception e) {
                e.printStackTrace();
                ctx.status(500).result("{\"error\":\"Gateway proxy error: " + e.toString() + "\"}");
            }
        });

        app.post("/auth/login", ctx -> {
            try {
                String body = ctx.body();
                System.out.println("[Gateway] Forwarding /auth/login to Auth Service...");
                HttpClient client = HttpClient.newHttpClient();

                HttpRequest req = HttpRequest.newBuilder()
                        .uri(URI.create("http://localhost:8082/auth/login"))
                        .header("Content-Type", "application/json")
                        .POST(HttpRequest.BodyPublishers.ofString(body))
                        .build();

                HttpResponse<String> resp = client.send(req, HttpResponse.BodyHandlers.ofString());
                ctx.status(resp.statusCode()).result(resp.body());
            } catch (Exception e) {
                e.printStackTrace();
                ctx.status(500).result("{\"error\":\"Gateway proxy error: " + e.toString() + "\"}");
            }
        });

        //Booking service route
        // BOOKING SERVICE PROXY (port 8083)
        app.post("/booking/book", ctx -> {
            try {
                String body = ctx.body();
                HttpClient client = HttpClient.newHttpClient();

                HttpRequest req = HttpRequest.newBuilder()
                        .uri(URI.create("http://localhost:8083/booking/book"))
                        .header("Content-Type", "application/json")
                        .POST(HttpRequest.BodyPublishers.ofString(body))
                        .build();

                HttpResponse<String> resp = client.send(req, HttpResponse.BodyHandlers.ofString());
                ctx.status(resp.statusCode()).result(resp.body());
            } catch (Exception e) {
                e.printStackTrace();
                ctx.status(500).result("{\"error\":\"Gateway proxy error: " + e.getMessage() + "\"}");
            }
        });

        app.post("/booking/confirm/{id}", ctx -> {
            try {
                String id = ctx.pathParam("id");
                HttpClient client = HttpClient.newHttpClient();

                HttpRequest req = HttpRequest.newBuilder()
                        .uri(URI.create("http://localhost:8083/booking/confirm/" + id))
                        .POST(HttpRequest.BodyPublishers.noBody())
                        .build();

                HttpResponse<String> resp = client.send(req, HttpResponse.BodyHandlers.ofString());
                ctx.status(resp.statusCode()).result(resp.body());
            } catch (Exception e) {
                e.printStackTrace();
                ctx.status(500).result("{\"error\":\"Gateway proxy error: " + e.getMessage() + "\"}");
            }
        });

        app.get("/booking/{id}", ctx -> {
            try {
                String id = ctx.pathParam("id");
                HttpClient client = HttpClient.newHttpClient();

                HttpRequest req = HttpRequest.newBuilder()
                        .uri(URI.create("http://localhost:8083/booking/" + id))
                        .build();

                HttpResponse<String> resp = client.send(req, HttpResponse.BodyHandlers.ofString());
                ctx.status(resp.statusCode()).result(resp.body());
            } catch (Exception e) {
                e.printStackTrace();
                ctx.status(500).result("{\"error\":\"Gateway proxy error: " + e.getMessage() + "\"}");
            }
        });

        //Payment rout
        // PAYMENT SERVICE PROXY (port 8084)
        app.post("/payment/pay", ctx -> {
            try {
                String body = ctx.body();
                HttpClient client = HttpClient.newHttpClient();

                HttpRequest req = HttpRequest.newBuilder()
                        .uri(URI.create("http://localhost:8084/payment/pay"))
                        .header("Content-Type", "application/json")
                        .POST(HttpRequest.BodyPublishers.ofString(body))
                        .build();

                HttpResponse<String> resp = client.send(req, HttpResponse.BodyHandlers.ofString());
                ctx.status(resp.statusCode()).result(resp.body());
            } catch (Exception e) {
                e.printStackTrace();
                ctx.status(500).result("{\"error\":\"Gateway proxy error: " + e.getMessage() + "\"}");
            }
        });


    }
}
