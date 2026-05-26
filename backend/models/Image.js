const mongoose = require("mongoose");

const imageSchema = new mongoose.Schema({
    url: {
        type: String
    },
    name: {
        type: String
    }
}, {
    versionKey: false
})

module.exports = mongoose.model("Image", imageSchema);