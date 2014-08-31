package fractal2d.controller;

import fractal2d.Helpers.Helpers;
import fractal2d.Helpers.Range;
import fractal2d.model.PictureGenerator;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Worker;
import javafx.event.ActionEvent;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Accordion;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TitledPane;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.awt.*;
import java.io.File;
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

    private Range currentZoomStatus;


    private boolean shouldAutoRender = false;

    private boolean zooming = false;
    private boolean canvasMousePressed = false;

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
        currentZoomStatus = new Range(-2.0, -2.0, 2.0, 2.0);

        canvas.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent event) -> {
           canvasMousePressed = true;
        });

        canvas.addEventHandler(MouseEvent.MOUSE_MOVED, (MouseEvent event) -> {
        });

        canvas.addEventHandler(MouseEvent.MOUSE_RELEASED, (MouseEvent event) -> {
        });
    }

    @FXML
    protected void draw(ActionEvent event) {
        if (interactiveController.isActive()) {
            if (interactiveController.getCode() == null || interactiveController.getCode() == "" || interactiveController.getCode().matches("[ ]*")) {
                Helpers.displayErrorMessage("No Input!", "Interactive input field is empty", "Write your lua code and hit render");
                return;
            }
            System.out.println("Interactive Mode");
            final PictureGenerator pictureGenerator = new PictureGenerator((int) Math.round(canvas.getWidth()),
                    (int) Math.round(canvas.getHeight()), interactiveController.getCode(), currentZoomStatus);
            drawInBackground(pictureGenerator);

        } else if (fileSelectController.isActive()) {
            System.out.println("File Mode");
            File file = fileSelectController.getFile();
            if (file == null) {
                Helpers.displayErrorMessage("File Error!", "Could not read file", "Please provide a path to an existing and readable file");
                return;
            }
            final PictureGenerator pictureGenerator = new PictureGenerator((int) Math.round(canvas.getWidth()),
                    (int) Math.round(canvas.getHeight()), file, currentZoomStatus);
            drawInBackground(pictureGenerator);
        } else {
            System.out.println("No Mode");
            Toolkit.getDefaultToolkit().beep();

        }
    }

    private void drawInBackground(final PictureGenerator pictureGenerator) {
        if (pictureGenerator == null) {
            Toolkit.getDefaultToolkit().beep();
            return;
        }
        progressBar.setVisible(true);
        progressBar.progressProperty().bind(pictureGenerator.progressProperty());
        pictureGenerator.stateProperty().addListener(new ChangeListener<Worker.State>() {
            @Override
            public void changed(ObservableValue<? extends Worker.State> observable, Worker.State oldValue, Worker.State newValue) {
                if (newValue == Worker.State.SUCCEEDED) {
                    progressBar.progressProperty().unbind();
                    progressBar.setVisible(false);
                    Image img = pictureGenerator.valueProperty().get();
                    canvas.getGraphicsContext2D().drawImage(img, 0, 0);
                } else if (newValue == Worker.State.FAILED) {
                    progressBar.progressProperty().unbind();
                    progressBar.setVisible(false);
                    Helpers.displayErrorMessage("Lua Error", pictureGenerator.exceptionProperty().get());
                    System.err.println("Something went wrong while creating the picture, Task failed:");
                    System.err.println(pictureGenerator.exceptionProperty().get());
                }
            }
        });
        Thread t = new Thread(pictureGenerator);
        t.start();
    }

    @FXML
    protected void setAutoRender(ActionEvent event) {
        shouldAutoRender = checkBoxAutoRender.isSelected();
        System.out.println(shouldAutoRender);
    }

    @FXML
    protected void close(ActionEvent event) {
        ((Stage) canvas.getScene().getWindow()).close();
    }

    @FXML
    protected void minimize(ActionEvent event) {
        ((Stage) canvas.getScene().getWindow()).setIconified(true);
    }

    @FXML
    protected void maximize(ActionEvent event) {
        Toolkit.getDefaultToolkit().beep();
        System.err.println("Not implemented yet");
    }
}
