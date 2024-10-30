package UI;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ViewManager {
    private Stage stage;
    public ViewManager(){
        this.stage = new Stage();
        this.stage.setMaximized(true);
    }

    public void showWindow(String fxmlFilePath, String title, String stylePath) throws Exception {
        FXMLLoader loader = new FXMLLoader(ViewManager.class.getResource(fxmlFilePath));
        Parent root = loader.load();
        stage.setTitle(title);
        Scene scene = new Scene(root);
        scene.getStylesheets().add(ViewManager.class.getResource(stylePath).toExternalForm());
        stage.setScene(scene);
        stage.show();
    }

}
