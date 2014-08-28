package fractal2d.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.stage.FileChooser;

import java.io.File;
import java.nio.file.Path;

/**
 * Created by lenni on 27.08.14.
 */
public class FileSelectController {
    @FXML
    private TitledPane fileTab;
    @FXML
    private File file;
    @FXML
    private TextField fileSelectTextField;

    public boolean isActive() {
        return fileTab.isExpanded();
    }

    public File getFile() {
        if (file != null) {
            if (!file.canRead()) {
                System.err.println("Can't find or read the file \""+file.getAbsolutePath()+"\"");
                return null;
            }
        }
        return file;
    }

    public TitledPane getTab() {
        return fileTab;
    }

    @FXML
    protected void openFileDialog(ActionEvent event) {
        FileChooser fc = new FileChooser();
        fc.setTitle("Select lua source file...");
        this.file = fc.showOpenDialog(fileTab.getScene().getWindow());
        if (this.file != null) {
            fileSelectTextField.setPromptText(file.getAbsolutePath());
        }
    }
}
