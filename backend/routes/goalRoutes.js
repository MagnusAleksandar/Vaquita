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
    "name": "Nombre de la meta",
    "amount": 1000000,
    "dueDate": "AAAA-MM-DD",
    "contributions": [ 
        {
            "contributor": "_id",
            "amount": 100000
        }
    ],
    "image": "_id"
}

Example:
{
    "name": "Comprar carro",
    "amount": 5000000,
    "dueDate": "2026-01-01",
    "contributions": [],
    "image": "6a0d16e1b9bf9d974cd337e2"
}
*/