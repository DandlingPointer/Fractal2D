package fractal2d.model;

import javafx.scene.paint.Color;
import org.luaj.vm2.Globals;
import org.luaj.vm2.LuaError;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.jse.JsePlatform;

/**
 * Created by DandlingPointer on 28.08.14.
 */
public class LuaHandler {

    private Globals luaGlobals;
    private LuaValue currentChunk = null;

    public LuaHandler() {
        luaGlobals = JsePlatform.standardGlobals();
        loadCustomLibs();

    }

    public void compileString(String code) {
        currentChunk = luaGlobals.load(code);
    }

    public void compileFile(String path) {
        currentChunk = luaGlobals.loadfile(path);
    }

    private void loadCustomLibs() {
        LuaValue complexLib = luaGlobals.load(getClass().getResourceAsStream("complex.lua"), "complexLib", "bt", luaGlobals);
        complexLib.call();
    }

    public Color getColorForPosition(double x, double y) throws LuaError {
        luaGlobals.set("x", x);
        luaGlobals.set("y", y);
        if (currentChunk == null) {
            throw new LuaError("No code to execute!");
        }
        Varargs result = currentChunk.invoke();
        double r, g, b;
        r = luaGlobals.get("r").checkdouble();
        g = luaGlobals.get("g").checkdouble();
        b = luaGlobals.get("b").checkdouble();
        if (r > 1 || g > 1 || b > 1 && r < 257 && g < 257 && b < 257) {
            return Color.rgb((int) Math.round(r), (int) Math.round(g), (int) Math.round(b));
        }
        return new Color(r, g, b, 1);
    }
}
