package de.fuberlin.innovonto.utils.ideasimilarityappbackend.jackson;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import de.fuberlin.innovonto.utils.ideasimilarityappbackend.model.IdeaPair;

import java.io.IOException;

public class IdeaPairSerializer extends JsonSerializer<IdeaPair> {

    @Override
    public void serialize(IdeaPair value, JsonGenerator jgen, SerializerProvider provider) throws IOException {
        jgen.writeStartArray();
        jgen.writeString(value.getLeftIdea());
        jgen.writeString(value.getRightIdea());
        jgen.writeEndArray();
    }
}
