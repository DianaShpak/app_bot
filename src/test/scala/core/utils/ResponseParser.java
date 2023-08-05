package core.utils;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Map;

public class ResponseParser {
    private static final Gson gson = new Gson();

    public static JsonElement parseStringToJson(String str) {
        return gson.toJsonTree(str);
    }

    public static Map<String, String> jsonToMap(JsonObject jsonObject) {
        Type stringStringMap = new TypeToken<Map<String, String>>() {
        }.getType();
        return gson.fromJson(jsonObject, stringStringMap);
    }
}