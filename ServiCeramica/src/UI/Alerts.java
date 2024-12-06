package UI;

import Model.Sale;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class Alerts {

    public static void errorParsingNumber(String field) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Error en el campo '"+field+"''.");
        alert.setContentText("El campo '" + field + "' esta vacio, contiene letras o es un numero negativo");
        alert.getDialogPane().getStylesheets().add(Alerts.class.getResource("AlertStyle.css").toExternalForm());

        alert.showAndWait();
    }

    public static void errorParsingString(String field) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Error en el campo '"+field+"''.");
        alert.setContentText("El campo '" + field + "' esta vacio o contiene numeros.");
        alert.getDialogPane().getStylesheets().add(Alerts.class.getResource("AlertStyle.css").toExternalForm());
        alert.showAndWait();
    }

    public static void errorListProducts() {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Error al registrar la venta");
        alert.setContentText("No hay productos seleccionados para la venta.");
        alert.getDialogPane().getStylesheets().add(Alerts.class.getResource("AlertStyle.css").toExternalForm());
        alert.showAndWait();
    }

    public static void succesRegisterSale(Sale newSale, double amountTurned){
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Factura");
        alert.setHeaderText("Exito al registrar la venta");
        alert.setContentText("Fecha: "+ newSale.getSaleDate() + " Hora: " + newSale.getSaleTime() + "\nCliente: " + newSale.getCustomer().getName() + " Devolver: " + amountTurned);
        alert.getDialogPane().getStylesheets().add(Alerts.class.getResource("AlertStyle.css").toExternalForm());
        alert.showAndWait();
    }

    public static void failedAmountReceived(){
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Error");
        alert.setHeaderText("Error al registrar la venta");
        alert.setContentText("El monto recibido es menor al monto de la venta");
        alert.getDialogPane().getStylesheets().add(Alerts.class.getResource("AlertStyle.css").toExternalForm());
        alert.showAndWait();
    }

    
}
