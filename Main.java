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
        }
    }
}