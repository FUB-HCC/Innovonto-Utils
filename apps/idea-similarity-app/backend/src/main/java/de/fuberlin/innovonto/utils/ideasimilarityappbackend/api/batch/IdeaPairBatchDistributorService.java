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

    public IdeaPairBatchDTO getBatchFor(String hitId, String workerId, String assignmentId, int numberOfPairs) {
        return mockData;
    }
}
