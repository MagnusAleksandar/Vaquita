const mongoose = require("mongoose");

const person = new mongoose.Schema({
    id: {
        type: String,
        required: true
    },
    persName:{
        type: String,
        required: true
    }
})

const goal = new mongoose.Schema({
    id: {
        type: String,
        required: true
    },
    goalName:{
        type: String,
        required: true
    },
    dueDate:{
        type: String,
        required: true
    },
    contributions: [person],
    image: {
        type: String
    }
})

module.exports = mongoose.model("Goal", goal);