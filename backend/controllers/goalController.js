const Goal = require("../models/Goal");

// Controladores para Metas (Goals)
exports.createGoal = async (req, res) => {
    console.log("Intentando crear una nueva meta...");
    try {
        const goal = new Goal(req.body);
        await goal.save();
        if (goal) {
            const savedGoal = await Goal.findById(goal._id)
                .populate("contributions.contributor")
                .populate("image");
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
        const goals = await Goal.find()
            .populate("contributions.contributor")
            .populate("image");
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
        const goal = await Goal.findById(req.params.goalId)
            .populate("contributions.contributor")
            .populate("image");
        if (goal) {
            res.status(200).json(goal)
        } else {
            res.status(404).json({ message: "Meta no encontrada." })
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

        const { contributor, amount, description } = req.body;

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
            amount: Number(amount),
            description: description || ""
        };

        goal.contributions.push(newContribution);
        await goal.save();

        const updatedGoal = await Goal.findById(goal._id)
            .populate("contributions.contributor")
            .populate("image");
        res.status(200).json(updatedGoal);

    } catch (error) {
        console.error("Error al registrar aporte:", error.message);
        res.status(500).json({ error: error.message });
    }
}
