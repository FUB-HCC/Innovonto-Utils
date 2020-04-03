package de.fuberlin.innovonto.utils.ideasimilarityappbackend.model;

import com.google.gson.*;

import java.lang.reflect.Type;

public class IdeaPairSerializer implements JsonSerializer<IdeaPair> {
    @Override
    public JsonElement serialize(IdeaPair src, Type typeOfSrc, JsonSerializationContext context) {
        final JsonArray target = new JsonArray(2);
        target.set(0, new JsonPrimitive(src.getLeftIdea()));
        target.set(1, new JsonPrimitive(src.getRightIdea()));
        return target;
    }
}
