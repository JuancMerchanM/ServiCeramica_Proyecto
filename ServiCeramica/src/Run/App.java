package Run;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {

    public static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML("../UI/InitialWindow"));
        
        scene.getStylesheets().add(App.class.getResource("../UI/InitialWindowCSS.css").toExternalForm());
        stage.setScene(scene);
        stage.setTitle("Inicio");
        stage.setMaximized(true);
        stage.show();
    }

    public static void setRoot(String fxml) throws IOException {
        try {
            scene.setRoot(loadFXML(fxml));    
        } catch (Exception e) {
            System.out.println(e.getMessage()+"\n"+e.getCause());
        }
        
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }
}
