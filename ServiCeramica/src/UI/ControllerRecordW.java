package UI;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class ControllerRecordW {
    @FXML
    Button registros;
    @FXML
    Button registrarVenta;
    @FXML
    Button reportes;
    @FXML
    VBox tableBox;
    @FXML
    VBox filterBox;
    @FXML
    VBox content;
    @FXML
    HBox records;
    @FXML
    public void initialize(){
        HBox.setHgrow(registros, Priority.ALWAYS);
        HBox.setHgrow(registrarVenta, Priority.ALWAYS);
        HBox.setHgrow(reportes, Priority.ALWAYS);
        HBox.setHgrow(tableBox, Priority.ALWAYS);
        VBox.setVgrow(content, Priority.ALWAYS);
        VBox.setVgrow(records, Priority.ALWAYS);
        tableBox.setMaxWidth(Double.MAX_VALUE);
        registros.setMaxWidth(Double.MAX_VALUE);
        registrarVenta.setMaxWidth(Double.MAX_VALUE);
        reportes.setMaxWidth(Double.MAX_VALUE);
        content.setMaxHeight(Double.MAX_VALUE);
        records.setMaxHeight(Double.MAX_VALUE);
    }
}
