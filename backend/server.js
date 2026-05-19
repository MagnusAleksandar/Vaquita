const express = require("express");
const mongoose = require("mongoose");
const cors = require("cors");
require("dotenv").config();
const goalRoutes = require("./routes/goalRoutes");

const app = express();

app.use(cors());
app.use(express.json());

mongoose.connect(process.env.MONGODB_URI)
.then(() => {() => console.log("Connected to MongoDB")})
.catch((err) => {console.log(err)});

app.use("/api/goals", goalRoutes);

const PORT = process.env.PORT || 27017;

app.listen(PORT, () => {
    console.log(`Server is running on port ${PORT}`);
});