package Run;

import java.io.IOException;

import Persistence.DBApp;
import UI.ViewManager;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {

    public static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        ViewManager.initApp(stage);
    }

    public static void main(String[] args) {
        DBApp.loadDataApp();
        launch();
    }
}
