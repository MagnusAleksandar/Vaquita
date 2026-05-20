const express = require("express");

const router = express.Router();

const {
    createContribution,
    findContributions,
    updateContribution,
    discardContribution
} = require("../controllers/contribController");

router.post("/", createContribution);

router.get("/", findContributions);

router.put("/:id", updateContribution);

router.delete("/:id", discardContribution);

module.exports = router;