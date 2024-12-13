package Logic;

import java.util.function.Predicate;

import javafx.scene.control.TextField;

public class TextFieldAdapter {
    public static void validateTextField(TextField textField, Predicate<String> condition){
        textField.textProperty().addListener((_, _, newValue) -> {
            if (condition.test(newValue)) {
                textField.setStyle("-fx-border-color: red; -fx-border-width: 1px;");
            } else {
                textField.setStyle("-fx-border-color: blue; -fx-border-width: 1px;");
            }
        });
    }

    public static Predicate<String> containsNum_isEmpty() {
		return text -> text.isBlank() || text.matches(".*\\d.*");
	}

    public static Predicate<String> containsLet_isEmpty() {
		return text -> text.isBlank() || text.matches(".*[^0-9].*");
	}

    public static Predicate<String> isBlank() {
        return text -> text.isBlank();
    }

    public static Predicate<String> length10(){
        return text -> text.length() != 10;
    }
}
