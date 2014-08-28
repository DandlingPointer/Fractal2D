package fractal2d.controller;

import javafx.fxml.FXML;
import javafx.scene.control.TitledPane;

/**
 * Created by lenni on 27.08.14.
 */
public class FileSelectController {
    @FXML
    private TitledPane fileTab;

    public boolean isActive() {
        return fileTab.isExpanded();
    }

    public TitledPane getTab() {
        return fileTab;
    }
}
