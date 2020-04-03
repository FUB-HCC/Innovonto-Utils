package de.fuberlin.innovonto.utils.ideasimilarityappbackend.model;

import com.google.gson.*;

import java.lang.reflect.Type;

public class IdeaPairDeserializer implements JsonDeserializer<IdeaPair> {
    @Override
    public IdeaPair deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        final JsonArray jsonArray = json.getAsJsonArray();
        return new IdeaPair(jsonArray.get(0).getAsString(),jsonArray.get(1).getAsString());
    }
}
