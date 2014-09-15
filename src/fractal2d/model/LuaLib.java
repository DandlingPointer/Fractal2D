package fractal2d.model;

import fractal2d.Helpers.Helpers;
import fractal2d.model.PictureGenerator;
import javafx.concurrent.Task;
import org.luaj.vm2.LuaFunction;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.OneArgFunction;
import org.luaj.vm2.lib.TwoArgFunction;
import org.luaj.vm2.lib.ZeroArgFunction;

/**
 * Created by vogelsang on 15.09.2014.
 */
public class LuaLib extends TwoArgFunction {
    private PictureGenerator task;
    public LuaLib(PictureGenerator task) {
        this.task = task;
    }

    @Override
    public LuaValue call(LuaValue modname, LuaValue env) {
        env.set("updateProgess", new updateProgress(task));
        return LuaValue.NIL;
    }

    static class updateProgress extends TwoArgFunction {
        private PictureGenerator task;
        updateProgress(PictureGenerator task) {
            this.task = task;
        }

        @Override
        public LuaValue call(LuaValue progress, LuaValue max) {
            if (!progress.isnumber() || !max.isnumber()) {
                return LuaValue.error("You called updateProgess with a type other than a number");
            }
            task.update(progress.toint(), max.toint());
            return LuaValue.NIL;
        }
    }
}
