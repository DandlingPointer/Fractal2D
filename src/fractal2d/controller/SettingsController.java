package fractal2d.controller;

import javafx.fxml.FXML;
import javafx.scene.control.TitledPane;

/**
 * Created by lenni on 27.08.14.
 */
public class SettingsController {
    @FXML
    private TitledPane settingsTab;

    public boolean isActive() {
        return settingsTab.isExpanded();
    }

    public TitledPane getTab() {
        return settingsTab;
    }

}
