const mongoose = require("mongoose");

const personSchema = new mongoose.Schema({
    persName: { type: String },
    idNum: { type: String },
    persPhone: { type: String }
}, {
    versionKey: false
})

module.exports = mongoose.model("Person", personSchema);