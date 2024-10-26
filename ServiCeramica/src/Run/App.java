package Run;

import UI.ViewManager;
import javafx.application.Application;
import javafx.stage.Stage;

public class App extends Application {
    @Override
    public void start(Stage arg0) throws Exception {
        ViewManager.showWindow(
                "../UI/RecordWindow.fxml",
                "Inicio",
                "../UI/InitialWindowCSS.css");
    }
    public static void main(String[] args) throws Exception {
        launch(args);
    }
}
