const mongoose = require("mongoose");

const person = new mongoose.Schema({
    persName:{
        type: String,
        required: true
    }
})

module.exports = mongoose.model("Person", person);