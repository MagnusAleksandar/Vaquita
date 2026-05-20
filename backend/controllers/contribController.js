const Contribution = require("../models/Contribution");

exports.createContribution = async (req, res) => {
    try {
        const contribution = new Contribution(req.body);
        await contribution.save();
        
        res.status(200).json(contribution);
    }
    catch (error) {
        res.status(500).json({
            error: error.message
        });
    }
}

exports.findContributions = async (req, res) => {
    try {
        const contributions = await Contribution.find();

        res.status(200).json(contributions);    
    }   
    catch (error) {
        res.status(500).json({
            error: error.message
        });
    }
}

exports.updateContribution = async (req, res) => {
    try {
        const updtdContribution = await Contribution.findByIdAndUpdate(
            req.params.mongoId,
            req.body,
            { new: true }
        );

        res.status(200).json(updtdContribution);
    }
    catch (error) {
        res.status(500).json({
            error: error.message
        });
    }
}

exports.discardContribution = async (req, res) => {
    try {
        await Contribution.findByIdAndDelete(req.params.mongoId);

        res.status(200).json({ message: "Contribution deleted successfully" });
    }
    catch (error) {
        res.status(500).json({
            error: error.message
        });
    }
}