package fractal2d.controller;

import fractal2d.Helpers.Range;
import fractal2d.model.PictureGenerator;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Worker;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Accordion;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ProgressBar;
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
    @FXML
    private ProgressBar progressBar;

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
        System.out.println("Initialized Main Controller");
    }

    @FXML
    protected void draw(ActionEvent event) {
        if (interactiveController.isActive()) {
            final PictureGenerator pictureGenerator = new PictureGenerator((int)Math.round(canvas.getWidth()),
                    (int)Math.round(canvas.getHeight()), interactiveController.getCode(), new Range(0.0, 0.0, 1.0, 1.0));
            progressBar.setVisible(true);
            progressBar.progressProperty().bind(pictureGenerator.progressProperty());
            pictureGenerator.stateProperty().addListener(new ChangeListener<Worker.State>() {
                @Override
                public void changed(ObservableValue<? extends Worker.State> observable, Worker.State oldValue, Worker.State newValue) {
                    if (newValue == Worker.State.SUCCEEDED) {
                        progressBar.progressProperty().unbind();
                        progressBar.setVisible(false);
                        Image img = pictureGenerator.valueProperty().get();
                        if (img == null) {

                            System.err.println("Something went wrong while creating the picture, img was null");
                            return;
                        }
                        canvas.getGraphicsContext2D().drawImage(img, 0, 0);
                    } else if (newValue == Worker.State.FAILED) {
                        System.err.println("Something went wrong while creating the picture, Task failed:");
                        System.err.println(pictureGenerator.exceptionProperty().get());
                    }
                }
            });
            Thread t = new Thread(pictureGenerator);
            t.start();

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
}
