const mongoose = require("mongoose");

const contribution = new mongoose.Schema({
    contrbutor: { person },
    amount:{
        type: integer,
        required: true
    }
})

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
    contributions: [ contribution ],
    image: {
        type: String
    }
})

module.exports = mongoose.model("Goal", goal);