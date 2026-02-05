package it.dosti.justit.utils;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

public class FilesToBlob {

    public byte[] convertFileToBlob(File file) {
        Image resizedImage = new Image(file.toURI().toString(), 200, 0, true, true);

        if (resizedImage.isError()) {
            return new byte[0];
        }
        BufferedImage bImage = SwingFXUtils.fromFXImage(resizedImage, null);
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        try {
            ImageIO.write(bImage, "png", out);
            return out.toByteArray();
        } catch (IOException e) {
            JustItLogger.getInstance().error("Error while converting file to blob", e);
            return new byte[0];
        }
    }
}