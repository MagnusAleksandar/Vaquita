const mongoose = require("mongoose");

const image = new mongoose.Schema({
    imageURL: {
        type: String,
        required: true
    },
    imageName: {
        type: String,
        required: true
    }
})

module.exports = mongoose.model("Image", image);