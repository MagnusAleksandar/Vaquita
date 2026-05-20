const express = require("express");

const router = express.Router();

const {
    createImage,
    findImages,
    updateImage,
    discardImage
} = require("../controllers/imageController");

router.post("/", createImage);

router.get("/", findImages);

router.put("/:id", updateImage);

router.delete("/:id", discardImage);

module.exports = router;