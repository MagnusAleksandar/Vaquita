const Person = require("../models/Person");

exports.createPerson = async (req, res) => {
    try {
        const person = new Person(req.body);
        await person.save();

        res.status(200).json(person);

    } catch (error) {
        res.status(500).json({
            error: error.message
        });
    }
}

exports.findPeople = async (req, res) => {
    try {
        const people = await Person.find();

        res.status(200).json(people);
    }
    catch (error) {
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
            { new: true }
        );

        res.status(200).json(updtdPerson);
    }
    catch (error) {
        res.status(500).json({
            error: error.message
        });
    }
}

exports.discardPerson = async (req, res) => {
    try {
        await Person.findByIdAndDelete(req.params.mongoId);
        
        res.status(200).json({ message: "Person deleted successfully" });
    }
    catch (error) {
        res.status(500).json({
            error: error.message
        });
    }
}