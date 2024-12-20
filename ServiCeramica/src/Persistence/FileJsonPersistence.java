package Persistence;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import Logic.PaymentAdapter;
import Model.Payment;


public class FileJsonPersistence<T> {
    private Gson gson;
    private String filePath;

    public FileJsonPersistence(String path) {
        this.filePath = path;
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(LocalDate.class,new LocalDateAdapter());
        gsonBuilder.registerTypeAdapter(Payment.class, new PaymentAdapter());
        this.gson = gsonBuilder.setPrettyPrinting().create();
    }

    public void writeObject(List<T> objects, Class<T> clazz) {
        try (FileWriter fw = new FileWriter(filePath)) {
            gson.toJson(objects, fw);
        } catch (IOException e) {
            System.err.println("Error al escribir en el archivo JSON: " + e.getMessage());
        }
    }

    public List<T> getObjects(Class<T> clazz) {
        Type listType = TypeToken.getParameterized(List.class, clazz).getType();
        File file = new File(filePath);
        if (!file.exists()) {
            return new ArrayList<>();
        }
        try (FileReader fr = new FileReader(file)) {
            List<T> deserializeObjects = gson.fromJson(fr, listType);
            return deserializeObjects != null ? deserializeObjects : new ArrayList<T>();
        } catch (IOException e) {
            System.err.println("Error al leer el archivo JSON: ");
            return new ArrayList<T>();
        }
    }
}
