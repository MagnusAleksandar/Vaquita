const express = require("express");
const mongoose = require("mongoose");
const cors = require("cors");

const persRoutes = require("./routes/personRoutes");
const goalRoutes = require("./routes/goalRoutes");
const imgRoutes = require("./routes/imageRoutes");

require("dotenv").config();

const app = express();

app.use(cors());
app.use(express.json());
app.get("/ping", (req, res) => res.json({ ok: true }));

mongoose.connect(process.env.MONGODB_URI)
.then(() => {
    console.log("Connected to MongoDB Atlas successfully");
})
.catch((err) => {
    console.log("Error connecting to MongoDB:", err.message);
});

app.use("/api/person", persRoutes);
app.use("/api/goal", goalRoutes);
app.use("/api/image", imgRoutes);

const PORT = process.env.PORT || 5000;

try {
    app.listen(PORT, "0.0.0.0", () => {
        console.log(`Server running on port ${PORT}`);
    });
} catch (error) {
    console.error("Error starting server:", error.message);
}