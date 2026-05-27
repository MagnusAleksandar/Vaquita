const Image = require("../models/Image");

// Controladores para Imágenes
exports.createImage = async (req, res) => {
    console.log("Subiendo una nueva imagen...");
    try {
        const image = new Image(req.body);
        await image.save();
        if (image) {
            res.status(200).json(image);
        } else {
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
