const express = require("express");

const router = express.Router();

const {
    // Goals
    createGoal,
    findAllGoals,
    findOneGoal,
    updateGoal,
    discardGoal,
    // Contributions
    createContribution,
    findAllContributions,
    findOneContribution,
    updateContribution,
    discardContribution
} = require("../controllers/goalController");

// Goals

router.post("/", createGoal);

router.get("/", findAllGoals);

router.get("/:mongoId", findOneGoal);

router.put("/:mongoId", updateGoal);

router.delete("/:mongoId", discardGoal);

// Contributions

router.post("/:mongoId/contributions", createContribution);

router.get("/:mongoId/contributions", findAllContributions);

router.get("/:mongoId/contributions/:mongoId", findOneContribution);

router.put("/:mongoId/contributions/:mongoId", updateContribution);

router.delete("/:mongoId/contributions/:mongoId", discardContribution);


module.exports = router;
/*
Create:
{
    "goalName": "Nombre de la meta",
    "dueDate": "AAAA-MM-DD",
    "contributions": [ "_id" <- de la contribución ],
    "image": "_id" <- de la imagen
}

Example:
{
    "goalName": "Comprar carro",
    "dueDate": "2026-01-01",
    "contributions": [ "6a0dc6589a212803ac363f70" ],
    "image": "6a0d16e1b9bf9d974cd337e2"
}
*/