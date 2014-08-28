package fractal2d.model;

import fractal2d.Helpers.Helpers;
import fractal2d.Helpers.MathHelpers;
import fractal2d.Helpers.Range;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import org.luaj.vm2.LuaError;

import java.awt.image.BufferedImage;
import java.io.File;
import java.nio.file.Path;

/**
 * Created by lenni on 28.08.14.
 */
public class PictureGenerator extends Task<Image> {
    private LuaHandler luaHandler;
    private String code = null;
    private String path = null;
    int width;
    int height;
    Range range;

    public PictureGenerator(int width, int height, String code, Range range) {
        super();
        luaHandler = new LuaHandler();
        this.width = width;
        this.height = height;
        this.range = range;
        this.code = code;
    }
    public PictureGenerator(int width, int height, File file, Range range) {
        super();
        this.path = file.getAbsolutePath();
        luaHandler = new LuaHandler();
        luaHandler.compileFile(path.toString());
        this.width = width;
        this.height = height;
        this.range = range;
    }

    @Override
    protected Image call() throws Exception {
        WritableImage image = new WritableImage(width, height);
        PixelWriter pw = image.getPixelWriter();
        try {
            if (code != null) {
                luaHandler.compileString(code);
            } else if (path != null) {
                luaHandler.compileFile(path.toString());
            } else {
                System.err.println("This shouldn't have happened. Code String and File Path are null");
                return null;
            }
        } catch (LuaError e) {
            Helpers.displayErrorMessage("Lua Compilation error!", e);
            System.err.println("Compilation of lua code failed:");
            System.err.println(e);
            return null;
        }

        System.out.println("Started picture generation");
        int progress = 0;
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                try {
                    double x_c = MathHelpers.mapValue(x, 0, width, range.getStartX(), range.getEndX());
                    double y_c = MathHelpers.mapValue(y, 0, height, range.getStartY(), range.getEndY());

                    Color c = luaHandler.getColorForPosition(x_c, y_c);
                    pw.setColor(x, y, c);
                } catch(LuaError error) {
                    Helpers.displayErrorMessage("Lua Error!", error);
                    System.err.println("Lua Error:");
                    System.err.println(error);
                    return null;
                } catch(ArithmeticException e) {
                    pw.setColor(x, y, Color.WHITE);
                }
                updateProgress(progress, width + height);
                progress++;
            }
        }
        System.out.println("Ended picture generation");
        return image;
    }
}
