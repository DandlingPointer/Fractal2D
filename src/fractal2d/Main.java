package fractal2d;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * Created by DandlingPointer on 27.08.14.
 */
public class Main extends Application {
    private double width = 0.0;
    private double height = 0.0;
    @Override
    public void start(final Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/fractal2d/view/main.fxml"));

        primaryStage.setTitle("Fractal2D");
        primaryStage.setScene(new Scene(root, 600, 400));
        primaryStage.show();
    }

    public static void main(String[] args) {
        //double x_c = MathHelpers.mapValue(x, 0, width, range.getStartX(), range.getEndX());
        //double y_c = MathHelpers.mapValue(y, 0, height, range.getStartY(), range.getEndY());
        launch(args);
    }
}
