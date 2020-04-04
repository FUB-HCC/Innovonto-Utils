package de.fuberlin.innovonto.utils.ideasimilarityappbackend.management;


import de.fuberlin.innovonto.utils.ideasimilarityappbackend.model.Batch;
import de.fuberlin.innovonto.utils.ideasimilarityappbackend.model.IdeaPair;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * Convert A List of Pairs to Batches
 * Input:
 * Pairs: [['1','2'],['3','4'],['5','6']
 * Output:
 * Batch 1: [[1,2][3,4]]
 * Batch 2: [[1,2][5,6]]
 * Parameter:
 * Anzahl Pairs pro Batch (Also nicht vom frontend setzbar)
 * <p>
 * Requirements:
 * Kein Pair doppelt in einem Batch
 */
@Service
public class BatchSplitter {

    public List<Batch> createBatchesFor(Requirements requirements) {
        final List<Batch> result = new ArrayList<>((requirements.getPairs().size() / requirements.getBatchSize()) * requirements.getGoalRatingsPerPair());
        final AtomicInteger counter = new AtomicInteger();

        //TODO randomize here or not? currently I'm randomizing in the MturkClientRestController
        final Collection<List<IdeaPair>> chunks = requirements.getPairs().stream()
                .collect(Collectors.groupingBy(it -> counter.getAndIncrement() / requirements.getBatchSize()))
                .values();
        for (List<IdeaPair> chunk : chunks) {
            for (int i = 0; i < requirements.getGoalRatingsPerPair(); i++) {
                result.add(new Batch(chunk));
            }
        }
        return result;
    }
}
