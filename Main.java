import redis.clients.jedis.Jedis;

public class Main {
    public static void main(String[] args) {
        System.out.println("Caching Module Initialized!");

        // Connecting to Redis
        try (Jedis jedis = new Jedis("localhost", 6379)) {
            jedis.set("test-key", "Hello from Caching Module!");
            System.out.println("Stored value in Redis: " + jedis.get("test-key"));
        } catch (Exception e) {
            System.err.println("Connection failed: " + e.getMessage());
            System.out.println("Keep running the backend server even if Redis is not connected...");
        }

        // சர்வர் அப்படியே ஆன்லைனில் தொடர்ந்து நீடிப்பதற்காக ஒரு இன்ஃபினிட் லூப் சேர்க்கிறோம்
        try {
            while (true) {
                Thread.sleep(10000); // 10 வினாடிகளுக்கு ஒருமுறை லூப் ஆகும்
            }
        } catch (InterruptedException ie) {
            System.out.println("Server stopped.");
        }
    }
}