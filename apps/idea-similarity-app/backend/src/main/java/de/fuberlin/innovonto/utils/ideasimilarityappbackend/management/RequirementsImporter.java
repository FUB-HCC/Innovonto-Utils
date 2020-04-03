package de.fuberlin.innovonto.utils.ideasimilarityappbackend.management;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import de.fuberlin.innovonto.utils.ideasimilarityappbackend.model.IdeaPair;
import de.fuberlin.innovonto.utils.ideasimilarityappbackend.model.IdeaPairDeserializer;
import de.fuberlin.innovonto.utils.ideasimilarityappbackend.model.IdeaPairSerializer;

public class RequirementsImporter {

    public Requirements importRequirements(String jsonInput) {
        //Step 1: Import from JSON
        final Gson gson = new GsonBuilder()
                .registerTypeAdapter(IdeaPair.class, new IdeaPairDeserializer())
                .registerTypeAdapter(IdeaPair.class, new IdeaPairSerializer())
                .create();

        final Requirements importedRequirements = gson.fromJson(jsonInput, Requirements.class);
        //Step 2: Check if already existing id
        //TODO data transformation: transform ideas to local id: here or elsewhere?
        //Step 3: Check That all ideas are present
        //Step 4: Check that pairs is divisible by batch size * numRatings (TODO long-term: generify)

        return importedRequirements;
    }

}
