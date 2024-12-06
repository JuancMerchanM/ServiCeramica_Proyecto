package Logic;

import java.lang.reflect.Type;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import Model.CashPayment;
import Model.Payment;
import Model.TransferPayment;

public class PaymentAdapter implements JsonDeserializer<Payment>, JsonSerializer<Payment> {

    @Override
    public JsonElement serialize(Payment src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject json = context.serialize(src).getAsJsonObject();
        return json;
    }

    @Override
    public Payment deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) 
            throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();
        String type = jsonObject.get("type").getAsString();

        Class<?> clazz = type.equals("Transfer") ? TransferPayment.class : CashPayment.class;
        return context.deserialize(jsonObject, clazz);
    }

}
