const mongoose = require("mongoose");

const person = new mongoose.Schema({
    persName:{
        type: String
    },
    idNum:{
        type: String
    }
})

module.exports = mongoose.model("Person", person);