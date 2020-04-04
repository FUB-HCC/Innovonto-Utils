package de.fuberlin.innovonto.utils.ideasimilarityappbackend.jackson;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import de.fuberlin.innovonto.utils.ideasimilarityappbackend.model.IdeaPair;

import java.io.IOException;

public class IdeaPairDeserializer extends JsonDeserializer<IdeaPair> {

    @Override
    public IdeaPair deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        final JsonNode jsonArray = jp.getCodec().readTree(jp);
        return new IdeaPair(jsonArray.get(0).asText(),jsonArray.get(1).asText());
    }
}
