const mongoose = require("mongoose");

const contribution = new mongoose.Schema({
    contrbutor: {
        type: mongoose.Schema.Types.ObjectId,
        ref: "Person",
        required: true
    },
    amount:{
        type: Number,
        required: true
    }
})

module.exports = mongoose.model("Contribution", contribution);