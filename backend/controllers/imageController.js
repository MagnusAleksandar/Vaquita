const Image = require("../models/Image");

exports.createImage = async (req, res) => {
    try {
        const image = new Image(req.body);
        await image.save();

        res.status(200).json(image);
    }
    catch (error) {
        res.status(500).json({
            error: error.message
        });
    }
}

exports.findImages = async (req, res) => {
    try {
        const images = await Image.find();

        res.status(200).json(images);
    }
    catch (error) {
        res.status(500).json({
            error: error.message
        });
    }
}

exports.updateImage = async (req, res) => {
    try {
        const updtdImage = await Image.findByIdAndUpdate(
            req.params.mongoId,
            req.body,
            { new: true }
        );

        res.status(200).json(updtdImage);
    }
    catch (error) {
        res.status(500).json({
            error: error.message
        });
    }
}

exports.discardImage = async (req, res) => {
    try {
        await Image.findByIdAndDelete(req.params.mongoId);

        res.status(200).json({ message: "Image discarded successfully" });
    }
    catch (error) {
        res.status(500).json({
            error: error.message
        });
    }
}