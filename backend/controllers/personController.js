const Person = require("../models/Person");

exports.createPerson = async (req, res) => {
    try {
        const person = new Person(req.body);
        await person.save();
        if (person){
            res.status(200).json(person);
        }else{
            res.status(404).json({ message: "Unable to create person." })
        }

    } catch (error) {
        res.status(500).json({
            error: error.message
        });
    }
}

exports.findAll = async (req, res) => {
    try {
        const people = await Person.find();
        if(people){
            res.status(200).json(people);
        }else{
            res.status(404).json({ message: "No one found." })
        }
    }
    catch (error) {
        res.status(500).json({
            error: error.message
        });
    }
}

exports.findOne = async (req, res) => {
    try {
        const person = await Person.findById(req.params.mongoId)
        if(person){
            res.status(200).json(person)
        }else{
            res.status(404).json({ message: "No Person found with that ID." })
        }
    } catch (error) {
        res.status(500).json({
            error: error.message
        });
    }
}

exports.updatePerson = async (req, res) => {
    try {
        const updtdPerson = await Person.findByIdAndUpdate(
            req.params.mongoId,
            req.body,
            { returnDocument: 'after' }
        );

        if(updtdPerson){
            res.status(200).json(updtdPerson);            
        }else{
            res.status(404).json({ message: "No Person found." })
        }
    }
    catch (error) {
        res.status(500).json({
            error: error.message
        });
    }
}

exports.discardPerson = async (req, res) => {
    try {
        const deleted = await Person.findByIdAndDelete(req.params.mongoId);
        if (deleted){
            res.status(200).json({ message: "Person deleted successfully" });
        }else{
            res.status(404).json({ message: "No Person found." })
        }
    }
    catch (error) {
        res.status(500).json({
            error: error.message
        });
    }
}