package fractal2d.model;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;

import java.awt.image.BufferedImage;
import java.io.File;

/**
 * Created by lenni on 28.08.14.
 */
public class PictureGenerator {
    private LuaHandler luaHandler;
    private double startX;
    private double startY;
    private double endX;
    private double endY;

    public PictureGenerator(double startX, double startY, double endX, double endY) {
        luaHandler = new LuaHandler();
        this.startX = startX;
        this.startY = startY;
        this.endX = endX;
        this.endY = endY;
    }

    public Image generateImage(File file, int width, int height) {
        throw new UnsupportedOperationException();
    }

    public Image generateImage(String code, int width, int height) {
        WritableImage img = new WritableImage(width, height);
        PixelWriter pixelWriter = img.getPixelWriter();
        return img;
    }

    public double getStartX() {
        return startX;
    }

    public void setStartX(double startX) {
        this.startX = startX;
    }

    public double getStartY() {
        return startY;
    }

    public void setStartY(double startY) {
        this.startY = startY;
    }


    public double getEndY() {
        return endY;
    }

    public void setEndY(double endY) {
        this.endY = endY;
    }

    public double getEndX() {
        return endX;
    }

    public void setEndX(double endX) {
        this.endX = endX;
    }
}
