const mongoose = require("mongoose");

const goal = new mongoose.Schema({
    goalName:{
        type: String,
        required: true
    },
    dueDate:{
        type: String,
        required: true
    },
    contributions: [{
        type: mongoose.Schema.Types.ObjectId,
        ref: "Contribution",
        required: true
    }],
    image: {
        type: String
    }
})

module.exports = mongoose.model("Goal", goal);