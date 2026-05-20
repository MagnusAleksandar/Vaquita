const express = require("express");

const router = express.Router();

const {
    createPerson,
    findPeople,
    updatePerson,
    discardPerson
} = require("../controllers/personController");

router.post("/", createPerson);

router.get("/", findPeople);

router.put("/:id", updatePerson);

router.delete("/:id", discardPerson);

module.exports = router;