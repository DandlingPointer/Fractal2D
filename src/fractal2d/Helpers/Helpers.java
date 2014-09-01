package fractal2d.Helpers;

import org.controlsfx.dialog.Dialogs;

import java.awt.*;

/**
 * Created by DandlingPointer on 28.08.14.
 */
public class Helpers {
    public static void displayErrorMessage(String title, String message, String explanation) {
        Toolkit.getDefaultToolkit().beep();
        Dialogs.create().title(title).masthead(message).message(explanation).showError();
    }

    public static void displayErrorMessage(String title, Throwable e) {
        Toolkit.getDefaultToolkit().beep();
        Dialogs.create()
                .title(title != null ? title : "Error")
                .masthead(e.getMessage() != null ? e.getClass().getName() : "Unknown Error")
                .message(e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "")
                .showError();
    }
}
