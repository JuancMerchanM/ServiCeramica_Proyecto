package UI;

import javafx.fxml.FXML;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class ControllerInitW {
    @FXML
    private VBox content;

    @FXML
    public void initialize() {
        content.setSpacing(10);
        VBox.setVgrow(content, Priority.ALWAYS);
    }

    @FXML
    public void login(){

    }
}
