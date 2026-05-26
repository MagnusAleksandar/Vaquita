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

router.get("/:goalId", findOneGoal);

router.put("/:goalId", updateGoal);

router.delete("/:goalId", discardGoal);

// Contributions

router.post("/:goalId/contributions", createContribution);

router.get("/:goalId/contributions", findAllContributions);

router.get("/:goalId/contributions/:contributionId", findOneContribution);

router.put("/:goalId/contributions/:contributionId", updateContribution);

router.delete("/:goalId/contributions/:contributionId", discardContribution);


module.exports = router;
/*
Create:
{
    "goalName": "Nombre de la meta",
    "dueDate": "AAAA-MM-DD",
    "contributions": [ 
        "contributor": "_id", <- de la contribución
        "amount": n
    ],
    "image": "_id" <- de la imagen
}

Example:
{
    "goalName": "Comprar carro",
    "dueDate": "2026-01-01",
    "contributions": [
        {
            "contributor": "6a0dc6589a212803ac363f70",
            "amount": 100000
        }
    ],
    "image": "6a0d16e1b9bf9d974cd337e2"
}
*/