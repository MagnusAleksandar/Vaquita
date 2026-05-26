const express = require("express");

const router = express.Router();

const {
    // Goals
    createGoal,
    findAllGoals,
    findOneGoal,
    // Contributions
    createContribution
} = require("../controllers/goalController");

// Goals

router.post("/", createGoal);

router.get("/", findAllGoals);

router.get("/:goalId", findOneGoal);

// Contributions

router.post("/:goalId/contributions", createContribution);

module.exports = router;