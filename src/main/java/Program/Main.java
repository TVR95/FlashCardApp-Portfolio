package Program;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    public static void main(String args[]) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/repeat-scene.fxml"));
        primaryStage.setTitle("Repeat until memorized - RUM");
        primaryStage.setScene(new Scene(root, primaryStage.getMaxWidth(), primaryStage.getMaxHeight()));
        //primaryStage.setMaximized(true);
        primaryStage.show();
    }
}