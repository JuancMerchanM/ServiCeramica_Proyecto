package UI;

import java.io.IOException;

import Persistence.DBApp;
import Run.App;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class ViewManager {
    private static Scene scene;
    private static String pathInitalW = "/UI/InitialWindow.fxml";
    private static String pathSaleW = "/UI/SaleRecordW.fxml";
    private static String pathRecordW = "/UI/RecordWindow.fxml";
    private static String pathStyle = "/UI/InitialWindowCSS.css";
    private static Stage stage;

    public static void initApp(Stage stage) throws IOException{
        scene = new Scene(loadFXML(pathInitalW));
        scene.getStylesheets().add(App.class.getResource(pathStyle).toExternalForm());
        stage.setTitle("Inicio");
        stage.setScene(scene);
        stage.setMaximized(true);
        ViewManager.stage = stage;
        executeLoadSaveData();
        ViewManager.stage.show();
    }

    public static void backLogin(){
        setRoot(pathInitalW);
    }

    public static void changeSaleW(){
        setRoot(pathSaleW);
    }

    public static void changeRecord(){
        setRoot(pathRecordW);
    }

    public static void showPopup(Popup popup){
        popup.show(stage);
    }

    private static void setRoot(String fxml){
        try {
            scene.setRoot(loadFXML(fxml));    
        } catch (Exception e) {
            System.out.println(e.getMessage()+"\n"+e.getCause());
        }
        
    }

    private static Parent loadFXML(String fxml) throws IOException {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml));
            return fxmlLoader.load();
        } catch (Exception e) {
            System.err.println("Error al cargar la ruta: "+fxml);
        }
        return null;
    }

    private static void executeLoadSaveData(){
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent arg0) {
                DBApp.saveDataApp();
            }
        });
    }


}
