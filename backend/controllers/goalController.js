const Goal = require("../models/Goal");

exports.createGoal = async (req, res) => {
    try {
        const goal = new Goal(req.body);
        await goal.save();

        res.status(200).json(goal);

    } catch (error) {
        res.status(500).json({
            error: error.message
        });
        
    }
}

exports.findGoals = async (req, res) => {
    try {
        const goals = await Goal.find();

        res.status(200).json(goals)
    } catch (error) {
        res.status(500).json({
            error: error.message
        });
    }
}

exports.updateGoal = async (req, res) => {
    try {
        const updtdGoal = await Goal.findByIdAndUpdate (
            req.params.mongoId,
            req.body,
            { new: true }

        );

        res.json(updtdGoal);
    } catch (error) {
        res.status(500).json({
            error: error.message
        });
    }
}

exports.discardGoal = async (req, res) => {
    try {
        await Goal.findByIdAndDelete (req.params.mongoId);

        res.json({ message: "Goal discarded successfully." })
    } catch (error) {
        res.status(500).json({
            error: error.message
        });

    }
}