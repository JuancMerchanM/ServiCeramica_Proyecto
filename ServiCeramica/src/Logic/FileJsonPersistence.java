package Logic;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;
import com.google.gson.reflect.TypeToken;

import Model.CustomerSaleView;

public class FileJsonPersistence<T> {
    private Gson gson;
    private String filePath;

    public FileJsonPersistence(String path) {
        this.filePath = path;
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(LocalDate.class,new LocalDateAdapter());
        this.gson = gsonBuilder.setPrettyPrinting().create();
    }

    public void writeObject(T object, Class<T> clazz) {
        List<T> listObjects = getObjects(clazz);
        listObjects.add(object);
        try (FileWriter fw = new FileWriter(filePath)) {
            gson.toJson(listObjects, fw);
        } catch (IOException e) {
            System.err.println("Error al escribir en el archivo JSON: " + e.getMessage());
        }
    }

    public List<T> getObjects(Class<T> clazz) {
        Type listType = TypeToken.getParameterized(List.class, clazz).getType();
        File file = new File(filePath);
        try (FileReader fr = new FileReader(file)) {
            List<T> deserializeObjects = gson.fromJson(fr, listType);
            return deserializeObjects != null ? deserializeObjects : new ArrayList<T>(); // Retorna una lista vacía si es null
        } catch (IOException e) {
            System.err.println("Error al leer el archivo JSON: ");
            return new ArrayList<T>(); // Retorna una lista vacía en caso de error
        }
    }
}
