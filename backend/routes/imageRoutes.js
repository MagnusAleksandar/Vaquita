const express = require("express");

const router = express.Router();

const {
    createImage,
    findAll,
    findOne,
    updateImage,
    discardImage
} = require("../controllers/imageController");

router.post("/", createImage);

router.get("/", findAll);

router.get("/:mongoId", findOne);

router.put("/:mongoId", updateImage);

router.delete("/:mongoId", discardImage);

module.exports = router;