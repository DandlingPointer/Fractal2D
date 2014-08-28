package fractal2d.controller;

import fractal2d.model.PictureGenerator;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Accordion;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TitledPane;
import javafx.scene.image.Image;

import javax.swing.event.DocumentEvent;
import java.beans.EventHandler;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by lenni on 27.08.14.
 */
public class MainController implements Initializable {
    @FXML
    private Accordion menuAccordion;
    @FXML
    private InteractiveController interactiveController;
    @FXML
    private SettingsController settingsController;
    @FXML
    private FileSelectController fileSelectController;
    @FXML
    private CheckBox checkBoxAutoRender;
    @FXML
    private Canvas canvas;

    private PictureGenerator pictureGenerator;

    private boolean shouldAutoRender = false;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //active pane shouldn't be collapsible:
        menuAccordion.expandedPaneProperty().addListener(new ChangeListener<TitledPane>() {
            @Override
            public void changed(ObservableValue<? extends TitledPane> property, final TitledPane oldPane, final TitledPane newPane) {
                if (oldPane != null) oldPane.setCollapsible(true);
                if (newPane != null) Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        newPane.setCollapsible(false);
                    }
                });
            }
        });
        //makes the interactive-tab visible on launch
        menuAccordion.setExpandedPane(interactiveController.getTab());

        //pictureGenerator = new PictureGenerator();
        System.out.println("Initialized Main Controller");
    }

    @FXML
    protected void draw(ActionEvent event) {
        if (interactiveController.isActive()) {
            System.out.println("Interactive Mode");
        } else if (fileSelectController.isActive()) {
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

    private void drawImage(double x, double y, Image img) {
        canvas.getGraphicsContext2D().drawImage(img, 0, 0);
    }
}
