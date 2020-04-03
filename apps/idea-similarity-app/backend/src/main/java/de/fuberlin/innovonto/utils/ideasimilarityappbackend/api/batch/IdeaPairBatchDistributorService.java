package de.fuberlin.innovonto.utils.ideasimilarityappbackend.api.batch;

import org.springframework.stereotype.Service;

@Service
public class IdeaPairBatchDistributorService {

    private IdeaPairBatchDTO mockData;


    public IdeaPairBatchDTO getMockData() {
        return mockData;
    }

    public void setMockData(IdeaPairBatchDTO mockData) {
        this.mockData = mockData;
    }

    /**
     * This should take one of the pre-defined batches and allocate it to one HWA combination
     * If the combination is already present: use the one that is there
     *
     */
    public IdeaPairBatchDTO getBatchFor(String hitId, String workerId, String assignmentId, int numberOfPairs) {
        return mockData;
    }
}
