import com.sun.net.httpserver.HttpServer;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;

public class Main {
    public static void main(String[] args) {
        // Render கொடுக்கும் போர்ட்டை எடுக்கும், இல்லைனா 10000 போர்ட் எடுக்கும்
        String portStr = System.getenv("PORT");
        int port = (portStr != null) ? Integer.parseInt(portStr) : 10000;

        try {
            // ஒரு சின்ன HTTP சர்வரை கிரியேட் பண்றோம்
            HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);
            
            // ப்ரௌசர்ல லிங்க்கை போட்டா இந்த மெசேஜ் அவுட்புட்டா காட்டும்
            server.createContext("/", exchange -> {
                String response = "Java Backend Server is Live! Caching Module Initialized Successfully.";
                exchange.sendResponseHeaders(200, response.length());
                try (OutputStream os = exchange.getResponseBody()) {
                    os.write(response.getBytes());
                }
            });

            server.setExecutor(null); 
            server.start();
            System.out.println("Server started successfully on port " + port);

        } catch (IOException e) {
            System.out.println("Server failed to start: " + e.getMessage());
        }
    }
}