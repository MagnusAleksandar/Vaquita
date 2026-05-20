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
    console.log("Connected to MongoDB");
})
.catch((err) => {
    console.log(err);
});

app.use("/api/person", persRoutes);
app.use("/api/goal", goalRoutes);
app.use("/api/image", imgRoutes);

const PORT = process.env.PORT || 5000;

try {
    app.listen(PORT, () => {
        console.log(`Server running on port ${PORT}`);
    });
} catch (error) {
    res.status(500).json({
        error: error.message
    });
}

// sudo systemctl start mongod