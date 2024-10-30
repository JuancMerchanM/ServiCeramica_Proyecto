package UI;

import Run.App;
import javafx.fxml.FXML;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ControllerInitW {
    @FXML
    private VBox content;

    @FXML
    public void initialize() {
        content.setSpacing(10);
        VBox.setVgrow(content, Priority.ALWAYS);
    }

    @FXML
    public void login() throws Exception{
        App.setRoot("../UI/RecordWindow");
        App.scene.getStylesheets().clear();
        App.scene.getStylesheets().add(App.class.getResource("../UI/RecordWindowCSS.css").toExternalForm());
    }
}
