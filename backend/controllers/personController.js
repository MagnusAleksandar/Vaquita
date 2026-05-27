const Person = require("../models/Person");

exports.createPerson = async (req, res) => {
    try {
        const person = new Person(req.body);
        await person.save();
        if (person) {
            res.status(200).json(person);
        } else {
            res.status(404).json({ message: "No se pudo crear a la persona." })
        }
    } catch (error) {
        res.status(500).json({ error: error.message });
    }
}

exports.findAll = async (req, res) => {
    try {
        const people = await Person.find();
        if (people) {
            res.status(200).json(people);
        } else {
            res.status(404).json({ message: "No se encontraron miembros." })
        }
    } catch (error) {
        res.status(500).json({ error: error.message });
    }
}