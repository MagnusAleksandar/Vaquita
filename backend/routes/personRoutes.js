const express = require("express");

const router = express.Router();

const {
    createPerson,
    findAll,
    findOne,
    updatePerson,
    discardPerson
} = require("../controllers/personController");

router.post("/", createPerson);

router.get("/", findAll);

router.get("/:mongoId", findOne);

router.put("/:mongoId", updatePerson);

router.delete("/:mongoId", discardPerson);

module.exports = router;