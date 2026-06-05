const express = require('express');
const redis = require('redis');
const { Pool } = require('pg');

const app = express();
const redisClient = redis.createClient();

redisClient.on('connect', () => {
  console.log('Connected to Redis successfully!');
});

redisClient.on('error', (err) => {
  console.error('Redis error: ', err);
});

const pool = new Pool({
  user: 'postgres',
  host: 'localhost',
  database: 'fooddb',
  password: 'admin', // மச்சான், இங்க உங்க pgAdmin பாஸ்வேர்ட்டை டைப் பண்ணிக்கோங்க!
  port: 5432,
});

pool.connect((err) => {
  if (err) {
    console.error('Database connection error:', err.stack);
  } else {
    console.log('Connected to PostgreSQL database successfully!');
  }
});
app.get('/dress-items', async (req, res) => {
  try {
    const result = await pool.query(
      "SELECT * FROM items WHERE name ILIKE '%T-Shirt%' OR name ILIKE '%Jeans%' OR name ILIKE '%Sneakers%' OR name ILIKE '%Watch%' OR name ILIKE '%Shoes%'"
    );
    res.json(result.rows);
  } catch (err) {
    console.error(err);
    res.status(500).send('Server Error');
  }
});
redisClient.on('error', (err) => {
    console.error('Redis error: ', err);
});

app.get('/search', async (req, res) => {
    const query = req.query.q;

    try {
        // ரெடிஸ்ல இருந்து டேட்டாவை ப்ராமிஸ் (Promise) வழியா எடுக்குறோம்
        const data = await redisClient.get(query);

        if (data) {
            return res.send(`Served from Memory: ${data}`);
        } else {
            const item = await Item.findOne({ name: query });
            if (item) {
                // ரெடிஸ்ல டேட்டாவை சேவ் பண்றோம்
                await redisClient.setEx(query, 3600, item.price.toString());
                return res.send(`Served from DB: ${item.price}`);
            } else {
                return res.send('Item not found');
            }
        }
    } catch (err) {
        console.error(err);
        return res.status(500).send("Server Error");
    }
});


const PORT = 3000;
app.listen(PORT, () => {
    console.log(`Server is running on port ${PORT}`);
});