const Image = require("../models/Image");

exports.createImage = async (req, res) => {
    try {
        const image = new Image(req.body);
        await image.save();
        if (image){
            res.status(200).json(image);
        }else{
            res.status(404).json({ message: "Unable to create image." })
        }
    }
    catch (error) {
        res.status(500).json({
            error: error.message
        });
    }
}

exports.findAll = async (req, res) => {
    try {
        const images = await Image.find();
        if(images){
            res.status(200).json(images);
        }else{
            res.status(404).json({ message: "No images found." })
        }
    }
    catch (error) {
        res.status(500).json({
            error: error.message
        });
    }
}

exports.findOne = async (req, res) => {
    try {
        const image = await Image.findById(req.params.mongoId)
        if(image){
            res.status(200).json(image)
        }else{
            res.status(404).json({ message: "No Image found." })
        }
    } catch (error) {
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

        if(updtdImage){
            res.status(200).json(updtdImage);
        }else{
            res.status(404).json({ message: "No Image found." })
        }
    }
    catch (error) {
        res.status(500).json({
            error: error.message
        });
    }
}

exports.discardImage = async (req, res) => {
    try {
        const deleted = await Image.findByIdAndDelete(req.params.mongoId);
        if (deleted){
            res.status(200).json({ message: "Image discarded successfully" });
        }else{
            res.status(404).json({ message: "No Image found." })
        }
    }
    catch (error) {
        res.status(500).json({
            error: error.message
        });
    }
}