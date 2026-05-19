const express = require("express");

const router = express.Router();

const {
    createGoal,
    findGoals,
    updateGoal,
    discardGoal
} = require("../controllers/goalController");

router.post("/", createGoal);

router.get("/", findGoals);

router.put("/:id", updateGoal);

router.delete("/:id", discardGoal);

module.exports = router;