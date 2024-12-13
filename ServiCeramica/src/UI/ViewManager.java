package UI;

import java.io.IOException;

import Persistence.DBApp;
import Run.App;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
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
    private static Popup popupInfUser;
    private static VBox boxUserInf;

    public static void initApp(Stage stage) throws IOException {
        scene = new Scene(loadFXML(pathInitalW));
        scene.getStylesheets().add(App.class.getResource(pathStyle).toExternalForm());
        stage.setTitle("Inicio");
        stage.setScene(scene);
        stage.setMaximized(true);
        ViewManager.stage = stage;
        executeLoadSaveData();
        ViewManager.stage.show();
        configurePopupUser();
    }

    public static void backLogin() {
        hideLogOut();
        setRoot(pathInitalW);
    }

    public static void changeSaleW() {
        setRoot(pathSaleW);
    }

    public static void changeRecord() {
        setRoot(pathRecordW);
    }

    public static void showLogOut() {
        popupInfUser.setX(stage.getX() + stage.getWidth() - 170);
        popupInfUser.setY(stage.getY() + 135);
        popupInfUser.show(stage);
    }

    public static void hideLogOut() {
        popupInfUser.hide();
    }

    public static boolean isShowingLogOut(){
        return popupInfUser.isShowing();
    }

    private static void setRoot(String fxml) {
        try {
            scene.setRoot(loadFXML(fxml));
        } catch (Exception e) {
            System.out.println(e.getMessage() + "\n" + e.getCause());
        }
    }

    private static Parent loadFXML(String fxml) throws IOException {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml));
            return fxmlLoader.load();
        } catch (Exception e) {
            System.err.println("Error al cargar la ruta: " + fxml);
        }
        return null;
    }

    private static void executeLoadSaveData() {
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent arg0) {
                DBApp.saveDataApp();
            }
        });
    }

    private static void configurePopupUser() {
        popupInfUser = new Popup();
        configureVBoxUser();
        popupInfUser.getContent().add(boxUserInf);
        popupInfUser.setAutoHide(true);
    }

    private static void configureVBoxUser() {
        boxUserInf = new VBox();
        boxUserInf.setAlignment(Pos.CENTER);
        boxUserInf.setSpacing(7);
        Button logoutButton = new Button("Cerrar sesion");
        logoutButton.setOnAction(_ -> backLogin());

        boxUserInf.getChildren().addAll(logoutButton);
        boxUserInf.setStyle("-fx-background-color: white;" +
                            "-fx-border-width: 2px;" +
                            "-fx-border-color: rgb(114,0,0);" +
                            "-fx-background-radius: 0 0 20 20;" +
                            " -fx-border-radius: 0 0 20 20;" +
                            "-fx-padding: 15;");
        // boxUserInf.setLayoutX(1420);
        // boxUserInf.setLayoutY(-220);
    }
}
