package Logic;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class UserManager {

    private static Properties properties = new Properties();
    private static String PATH = "resources/userCredentials.properties";

    public static void saveCredentials(String userName, String password) {
        try (FileOutputStream fos = new FileOutputStream(PATH)) {
            properties.setProperty("userName", userName);
            properties.setProperty("password", password);
            properties.setProperty("remind", "false");
            properties.store(fos, "Configuracion de usuario");
        } catch (Exception e) {
            System.err.println("No se pudieron guardar credenciales");
        }
    }

    public static String loadUserName() {
        try (FileInputStream fis = new FileInputStream(PATH)) {
            properties.load(fis);
            return properties.getProperty("userName");
        } catch (IOException e) {
            System.err.println("No se leyo");
        }
        return null;
    }

    public static String loadPassword() {
        try (FileInputStream fis = new FileInputStream(PATH)) {
            properties.load(fis);
            return properties.getProperty("password");
        } catch (IOException e) {
            System.err.println("No se leyo");
        }
        return null;
    }

    public static Boolean loadStateRemind() {
        try (FileInputStream fis = new FileInputStream(PATH)) {
            properties.load(fis);
            return Boolean.parseBoolean(properties.getProperty("remind"));
        } catch (IOException e) {
            System.err.println("No se leyo");
        }
        return false;
    }

    public static void saveStateRemind(Boolean state) {
        try (FileOutputStream fos = new FileOutputStream(PATH)) {
            properties.setProperty("remind", Boolean.toString(state));
            properties.store(fos, "Guardar estado");
        } catch (Exception e) {
            System.err.println("No se pudieron guardar credenciales");
        }
    }
}
