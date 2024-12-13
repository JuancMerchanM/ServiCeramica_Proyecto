package Logic;

import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.util.StringConverter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class DatePickerAdapter {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    public static void configureDatePicker(DatePicker datePicker) {

        datePicker.setConverter(new StringConverter<LocalDate>() {
            @Override
            public String toString(LocalDate date) {
                return date != null ? FORMATTER.format(date) : "";
            }

            @Override
            public LocalDate fromString(String text) {
                try {
                    return text != null && !text.isEmpty() ? LocalDate.parse(text, FORMATTER) : null;
                } catch (DateTimeParseException e) {
                    showAlert("Fecha invalida", "Por favor ingrese una fecha en el formato dd-MM-yyyy");
                    return null;
                }
            }
        });
    }

    public static void configureOnlyMonth(DatePicker datePicker){
        datePicker.setPromptText("MM-yyyy");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-yyyy");
        datePicker.setConverter(new StringConverter<>() {

            @Override
            public String toString(LocalDate date) {
                return (date != null) ? date.format(formatter) : "";
            }

            @Override
            public LocalDate fromString(String text) {
                try {
                    return text != null && !text.isEmpty() ? LocalDate.parse("01-" + text, FORMATTER) : null;
                } catch (DateTimeParseException e) {
                    showAlert("Fecha invalida", "Por favor ingrese una fecha en el formato MM-yyyy");
                    return null;
                }
            }
        });
    }

    public static void configureOnlyYear(DatePicker datePicker){
        datePicker.setPromptText("yyyy");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy");
        datePicker.setConverter(new StringConverter<>() {

            @Override
            public String toString(LocalDate date) {
                return (date != null) ? date.format(formatter) : "";
            }

            @Override
            public LocalDate fromString(String text) {
                try {
                    return text != null && !text.isEmpty() ? LocalDate.parse("01-01" + text, FORMATTER) : null;
                } catch (DateTimeParseException e) {
                    showAlert("Fecha invalida", "Por favor ingrese una fecha en el formato yyyy");
                    return null;
                }
            }
        });
    }

    private static void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
