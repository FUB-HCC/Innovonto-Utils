package de.fuberlin.innovonto.utils.ideasimilarityappbackend.management;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import de.fuberlin.innovonto.utils.ideasimilarityappbackend.jackson.IdeaPairDeserializer;
import de.fuberlin.innovonto.utils.ideasimilarityappbackend.jackson.IdeaPairSerializer;
import de.fuberlin.innovonto.utils.ideasimilarityappbackend.model.IdeaPair;

public class RequirementsImporter {

    public Requirements importRequirements(String jsonInput) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        SimpleModule module = new SimpleModule();
        module.addSerializer(IdeaPair.class, new IdeaPairSerializer());
        module.addDeserializer(IdeaPair.class,new IdeaPairDeserializer());
        mapper.registerModule(module);

        //Step 1: Import from JSON
        final Requirements importedRequirements = mapper.readValue(jsonInput, Requirements.class);
        //Step 2: Check if already existing id
        //TODO data transformation: transform ideas to local id: here or elsewhere?
        //Step 3: Check That all ideas are present
        //Step 4: Check that pairs is divisible by batch size * numRatings (TODO long-term: generify)

        return importedRequirements;
    }

}
