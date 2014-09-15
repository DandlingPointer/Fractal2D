package fractal2d.model;

import fractal2d.Helpers.MathHelpers;
import fractal2d.Helpers.Range;
import javafx.concurrent.Task;
import javafx.scene.image.Image;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

import java.io.File;
import java.util.DoubleSummaryStatistics;

/**
 * Created by DandlingPointer on 28.08.14.
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

    protected void update(int progress, int max) {
        updateProgress(progress, max);
    }

    protected void updateColor(float x, float y, int r, int g, int b) {
        int x_m = (int) MathHelpers.mapValue(x, range.getStartX(), range.getEndX(), 0, width);
        int y_m = (int) MathHelpers.mapValue(y, range.getStartY(), range.getEndY(), 0, height);
    }

    @Override
    protected Image call() throws Exception {
        WritableImage image = new WritableImage(width, height);
        PixelWriter pw = image.getPixelWriter();
        if (code != null) {
            luaHandler.compileString(code);
        } else if (path != null) {
            luaHandler.compileFile(path.toString());
        } else {
            throw new Exception("No Lua Code provided!");
        }

        System.out.println("Started picture generation");
        int progress = 0;
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                double x_c = MathHelpers.mapValue(x, 0, width, range.getStartX(), range.getEndX());
                double y_c = MathHelpers.mapValue(y, 0, height, range.getStartY(), range.getEndY());

                Color c = luaHandler.getColorForPosition(x_c, y_c);
                pw.setColor(x, y, c);

                updateProgress(progress, width * height);
                progress++;
            }
        }
        System.out.println("Ended picture generation");
        return image;
    }
}
