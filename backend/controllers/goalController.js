const Goal = require("../models/Goal");

// Controladores para Metas (Goals)
exports.createGoal = async (req, res) => {
    console.log("Intentando crear una nueva meta...");
    try {
        const goal = new Goal(req.body);
        await goal.save();
        if (goal) {
            const savedGoal = await Goal.findById(goal._id).populate("contributions.contributor");
            res.status(200).json(savedGoal);
        } else {
            res.status(404).json({ message: "No se pudo crear la meta." })
        }
    } catch (error) {
        console.error("Error al crear meta:", error.message);
        res.status(500).json({ error: error.message });
    }
}

exports.findAllGoals = async (req, res) => {
    try {
        const goals = await Goal.find().populate("contributions.contributor");
        if (goals) {
            res.status(200).json(goals);
        } else {
            res.status(404).json({ message: "No se encontraron metas." })
        }
    } catch (error) {
        res.status(500).json({ error: error.message });
    }
}

exports.findOneGoal = async (req, res) => {
    try {
        const goal = await Goal.findById(req.params.goalId).populate("contributions.contributor");
        if (goal) {
            res.status(200).json(goal)
        } else {
            res.status(404).json({ message: "Meta no encontrada." })
        }
    } catch (error) {
        res.status(500).json({ error: error.message });
    }
}

exports.updateGoal = async (req, res) => {
    try {
        const updtdGoal = await Goal.findByIdAndUpdate(
            req.params.goalId,
            req.body,
            { new: true }
        ).populate("contributions.contributor");

        if (updtdGoal) {
            res.status(200).json(updtdGoal);
        } else {
            res.status(404).json({ message: "Meta no encontrada para actualizar." })
        }
    } catch (error) {
        res.status(500).json({ error: error.message });
    }
}

exports.discardGoal = async (req, res) => {
    try {
        const deleted = await Goal.findByIdAndDelete(req.params.goalId);
        if (deleted) {
            res.status(200).json({ message: "Meta eliminada correctamente." });
        } else {
            res.status(404).json({ message: "No se encontró la meta a eliminar." });
        }
    } catch (error) {
        res.status(500).json({ error: error.message });
    }
}

// Controladores para Contribuciones (Contributions)
exports.createContribution = async (req, res) => {
    const { goalId } = req.params;
    console.log("Registrando aporte para la meta:", goalId);

    try {
        const goal = await Goal.findById(goalId);
        if (!goal) {
            return res.status(404).json({ message: "Meta no encontrada." });
        }

        const { contributor, amount } = req.body;
        
        let contributorId = contributor;
        if (contributor && typeof contributor === 'object') {
            contributorId = contributor._id;
        }

        if (!contributorId) {
            return res.status(400).json({ message: "El miembro es obligatorio." });
        }

        if (!amount || isNaN(amount)) {
            return res.status(400).json({ message: "El monto debe ser un número válido." });
        }

        const newContribution = {
            contributor: contributorId,
            amount: Number(amount)
        };

        goal.contributions.push(newContribution);
        await goal.save();
        
        const updatedGoal = await Goal.findById(goal._id).populate("contributions.contributor");
        res.status(200).json(updatedGoal);

    } catch (error) {
        console.error("Error al registrar aporte:", error.message);
        res.status(500).json({ error: error.message });
    }
}

exports.findAllContributions = async (req, res) => {
    try {
        const goal = await Goal.findById(req.params.goalId).populate("contributions.contributor");
        if (goal) {
            res.status(200).json(goal.contributions || []);
        } else {
            res.status(404).json({ message: "Meta no encontrada." })
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
                res.status(404).json({ message: "Aporte no encontrado." });
            }
        } else {
            res.status(404).json({ message: "Meta no encontrada." });
        }
    } catch (error) {
        res.status(500).json({ error: error.message });
    }
}

exports.updateContribution = async (req, res) => {
    try {
        const goal = await Goal.findById(req.params.goalId);
        if (goal) {
            const contribution = goal.contributions.id(req.params.contributionId);
            if (contribution) {
                Object.assign(contribution, req.body);
                await goal.save();
                res.status(200).json(goal);
            } else {
                res.status(404).json({ message: "Aporte no encontrado." })
            }
        } else {
            res.status(404).json({ message: "Meta no encontrada." })
        }
    } catch (error) {
        res.status(500).json({ error: error.message });
    }
}

exports.discardContribution = async (req, res) => {
    try {
        const goal = await Goal.findById(req.params.goalId);
        if (goal) {
            goal.contributions.pull(req.params.contributionId);
            await goal.save();
            res.status(200).json(goal);
        } else {
            res.status(404).json({ message: "Meta no encontrada." })
        }
    } catch (error) {
        res.status(500).json({ error: error.message });
    }
}