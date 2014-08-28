package fractal2d.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.CheckBox;
import javafx.scene.image.Image;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by lenni on 27.08.14.
 */
public class MainController {
    @FXML
    private InteractiveController interactive;
    @FXML
    private SettingsController settings;
    @FXML
    private FileSelectController fileSelect;
    @FXML
    private CheckBox checkBoxAutoRender;
    @FXML
    private Canvas canvas;

    private boolean shouldAutoRender = false;

    private void initialize(URL location, ResourceBundle resources) {
        interactive.getTab().setExpanded(true);

    }
    @FXML
    protected void draw(ActionEvent event) {
        if (interactive.isActive()) {
            System.out.println("Interactive Mode");
        } else if (settings.isActive()) {
            System.out.println("File Mode");
        } else {
            System.out.println("No Mode");
        }
    }

    @FXML
    protected void setAutoRender(ActionEvent event) {
        shouldAutoRender = checkBoxAutoRender.isSelected();
        System.out.println(shouldAutoRender);
    }

    public void drawImage(double x, double y, Image img) {
        canvas.getGraphicsContext2D().drawImage(img, 0, 0);
    }

    public double getCanvasHeight() {
        return canvas.getHeight();
    }
    public double getCanvasWidth() {
        return canvas.getWidth();
    }
}
