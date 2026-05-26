const mongoose = require("mongoose");

const contributionSchema = new mongoose.Schema({
    contributor: {
        type: mongoose.Schema.Types.ObjectId,
        ref: "Person"
    },
    amount: {
        type: Number
    },
    createdAt: {
        type: Date,
        default: Date.now
    }
}, {
    versionKey: false
})

const goalSchema = new mongoose.Schema({
    name: { type: String },
    amount: { type: Number },
    dueDate: { type: String },
    contributions: [contributionSchema],
    image: {
        type: mongoose.Schema.Types.ObjectId,
        ref: "Image"
    }
}, {
    versionKey: false
})

module.exports = mongoose.model("Goal", goalSchema);