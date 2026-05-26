const mongoose = require("mongoose");

const contribution = new mongoose.Schema({
    contributor: {
        type: mongoose.Schema.Types.ObjectId,
        ref: "Person"
    },
    amount:{
        type: Number
    },
    createdAt: {
        type: Date,
        default: Date.now
    }
})

const goal = new mongoose.Schema({
    name:{
        type: String
    },
    amount:{
        type: Number
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