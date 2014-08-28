package fractal2d.controller;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TitledPane;

/**
 * Created by lenni on 27.08.14.
 */
public class InteractiveController {
    @FXML
    private TextArea interactiveModeTextField;
    @FXML
    private TitledPane interactiveTab;

    public String getCode() {
        return interactiveModeTextField.getText();
    }

    public boolean isActive() {
        return interactiveTab.isExpanded();
    }

    public TitledPane getTab() {
        return interactiveTab;
    }
}
