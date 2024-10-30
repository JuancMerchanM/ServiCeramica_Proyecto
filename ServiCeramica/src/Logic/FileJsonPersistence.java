package Logic;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

public class FileJsonPersistence<T> {
    private Gson gson;
    private String filePath;

    public FileJsonPersistence(String path){
        this.filePath = path;
        this.gson = new GsonBuilder().setPrettyPrinting().create();
    }

    public void writeObject(T object){
        List<T> listObjects = getObjects();
        if (listObjects == null){
            listObjects = new ArrayList<>();
        }
        listObjects.add(object);
        try (FileWriter fw = new FileWriter(filePath)) {
            gson.toJson(listObjects, fw);
        } catch (Exception e) {
            System.err.println("Error al escribir en el archivo JSON: " + e.getMessage());
        }
    }

    public List<T> getObjects() {
        Type listType = new TypeToken<List<T>>() {}.getType();
        File file = new File(filePath);
        try (FileReader fr = new FileReader(file)) {
            List<T> deserializeObjects = gson.fromJson(fr, listType);
            return deserializeObjects;
        } catch (IOException e) {
            System.err.println("Error al leer el archivo JSON: " + e.getMessage());
            return null;
        }
    }
}
