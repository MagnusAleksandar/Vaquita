const mongoose = require("mongoose");

const image = new mongoose.Schema({
    imageURL: {
        type: String
    },
    imageName: {
        type: String,
        required: true
    }
})

module.exports = mongoose.model("Image", image);