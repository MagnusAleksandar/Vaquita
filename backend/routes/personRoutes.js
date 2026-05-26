const express = require("express");

const router = express.Router();

const {
    createPerson,
    findAll
} = require("../controllers/personController");

router.post("/", createPerson);

router.get("/", findAll);

module.exports = router;