const Image = require("../models/Image");

// Controladores para Imágenes
exports.createImage = async (req, res) => {
    console.log("Subiendo una nueva imagen...");
    try {
        const image = new Image(req.body);
        await image.save();
        if (image){
            res.status(200).json(image);
        }else{
            res.status(404).json({ message: "No se pudo guardar la imagen." })
        }
    }
    catch (error) {
        console.error("Error al guardar imagen:", error.message);
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
            res.status(404).json({ message: "No se encontraron imágenes." })
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
            res.status(404).json({ message: "Imagen no encontrada." })
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
            res.status(404).json({ message: "No se encontró la imagen para actualizar." })
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
            res.status(200).json({ message: "Imagen eliminada correctamente" });
        }else{
            res.status(404).json({ message: "No se encontró la imagen para eliminar." })
        }
    }
    catch (error) {
        res.status(500).json({
            error: error.message
        });
    }
}