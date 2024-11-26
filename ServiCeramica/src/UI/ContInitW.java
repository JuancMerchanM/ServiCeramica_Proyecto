package UI;

import Persistence.UserManager;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class ContInitW {
    @FXML
    private VBox content;

    @FXML
    private TextField tfUserName;
    @FXML
    private PasswordField tfPassword;

    @FXML
    private CheckBox checkRemind;

    @FXML
    public void initialize() {
        content.setSpacing(10);
        VBox.setVgrow(content, Priority.ALWAYS);
        remind();
    }

    public void remind() {
        boolean remindState = UserManager.loadStateRemind();
        String userName = UserManager.loadUserName();
        String password = UserManager.loadPassword();
        if(remindState){
            tfPassword.setText(password);
            tfUserName.setText(userName);
        }
    }

    @FXML
    public void login() throws Exception {
        String userName = UserManager.loadUserName();
        String password = UserManager.loadPassword();
        if (userName.equals(tfUserName.getText()) && password.equals(tfPassword.getText())) {
            ViewManager.changeRecord();
            if (checkRemind.isSelected()) {
                UserManager.saveStateRemind(true);
            }else{
                UserManager.saveStateRemind(false);
            }
        }
    }
}
