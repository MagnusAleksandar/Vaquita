const mongoose = require("mongoose");

const contribution = new mongoose.Schema({
    contributor: {
        type: mongoose.Schema.Types.ObjectId,
        ref: "Person",
        required: true
    },
    amount:{
        type: Number
    }
})

const goal = new mongoose.Schema({
    goalName:{
        type: String,
        required: true
    },
    dueDate:{
        type: String
    },
    contributions: [ contribution ],
    image: {
        type: mongoose.Schema.Types.ObjectId,
        ref: "Image"
    }
})

module.exports = mongoose.model("Goal", goal);;