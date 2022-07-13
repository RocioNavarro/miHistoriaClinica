package org.austral.ing.lab1.json;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import org.eclipse.jetty.util.MultiMap;
import org.eclipse.jetty.util.UrlEncoded;

import java.util.ArrayList;

public class JsonParser {

    private static final Gson parser = new Gson();

    public static <T> T fromJson(String json, Class<T> classOfT) throws JsonSyntaxException {
        return parser.fromJson(json, classOfT);
    }

    public static int fromJson(String json){
        final MultiMap<String> params = new MultiMap<>();
        UrlEncoded.decodeTo(json, params, "UTF-8");
        params.getValues("dni");
        return Integer.parseInt(params.getValue("dni", 0));
    }

    public static String toJson(Object src) {
        return parser.toJson(src);
    }

}