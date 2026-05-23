const Goal = require("../models/Goal");

// Goals
exports.createGoal = async (req, res) => {
    try {
        const goal = new Goal(req.body);
        await goal.save();
        if (goal){
            res.status(200).json(goal);
        }else{
            res.status(404).json({ message: "Unable to create goal." })
        }

    } catch (error) {
        res.status(500).json({
            error: error.message
        });
        
    }
}

exports.findAllGoals = async (req, res) => {
    try {
        const goals = await Goal.find();
        if(goals){
            res.status(200).json(goals);
        }else{
            res.status(404).json({ message: "No goals found." })
        }
    }
    catch (error) {
        res.status(500).json({
            error: error.message
        });
    }
}

exports.findOneGoal = async (req, res) => {
    try {
        const goal = await Goal.findById(req.params.mongoId)
        if(goal){
            res.status(200).json(goal)
        }else{
            res.status(404).json({ message: "No Goal found." })
        }
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

        if(updtdGoal){
            res.status(200).json(updtdGoal);
        }else{
            res.status(404).json({ message: "No Goal found." })
        }
    } catch (error) {
        res.status(500).json({
            error: error.message
        });
    }
}

exports.discardGoal = async (req, res) => {
    try {
        const deleted = await Goal.findByIdAndDelete (req.params.mongoId);
        if (deleted){
            res.status(200).json({ message: "Goal discarded successfully." });
        }else{
            res.status(404).json({ message: "No Goal found." });
        }
    } catch (error) {
        res.status(500).json({
            error: error.message
        });

    }
}

// Contributions
exports.createContribution = async (req, res) => {
    try {
        const goal = await Goal.findById(req.params.goalId);
        if(goal){
            goal.contributions.push(req.body);
            await goal.save();
            res.status(200).json(goal);
        }else{
            res.status(404).json({ message: "No Goal found." })
        }
    } catch (error) {
        res.status(500).json({ error: error.message });
    }
}

exports.findAllContributions = async (req, res) => {
    try {
        const goal = await Goal.findById(req.params.goalId);
        if(goal){
            if (goal.contributions){
                res.status(200).json(goal.contributions);
            }else{
                res.status(404).json({ message: "No Contribution found." })
            }
        }else{
            res.status(404).json({ message: "No Goal found." })
        }
    } catch (error) {
        res.status(500).json({ error: error.message });
    }
}

exports.findOneContribution = async (req, res) => {
    try {
        const goal = await Goal.findById(req.params.goalId);
        if (goal) {
            const contribution = goal.contributions.id(req.params.contributionId);
            if (contribution) {
                res.status(200).json(contribution);
            } else {
                res.status(404).json({ message: "No Contribution found." });
            }
        } else {
            res.status(404).json({ message: "No Goal for this Contribution found." });
        }
    } catch (error) {
        res.status(500).json({ error: error.message });
    }
}

exports.updateContribution = async (req, res) => {
    try {
        const goal = await Goal.findById(req.params.goalId);
        if(goal){
            const contribution = goal.contributions.id(req.params.contributionId);
            if(contribution){
                Object.assign(contribution, req.body);
                await goal.save();
                res.status(200).json(goal);
            }else{
                res.status(404).json({ message: "No Contribution found." })
            }
        }else{
            res.status(404).json({ message: "No Goal found." })
        }
    } catch (error) {
        res.status(500).json({ error: error.message });
    }
}

exports.discardContribution = async (req, res) => {
    try {
        const goal = await Goal.findById(req.params.goalId);
        if(goal){
            const contribution = goal.contributions.id(req.params.contributionId);
            if(contribution){
                goal.contributions.pull(req.params.contributionId);
                await goal.save();
                res.status(200).json(goal);
            }else{
                res.status(404).json({ message: "No Contribution found." })
            }
        }else{
            res.status(404).json({ message: "No Goal found." })
        }
    } catch (error) {
        res.status(500).json({ error: error.message });
    }
}