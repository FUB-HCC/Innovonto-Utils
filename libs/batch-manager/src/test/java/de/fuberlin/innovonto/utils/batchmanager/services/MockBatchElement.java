package de.fuberlin.innovonto.utils.batchmanager.services;

import de.fuberlin.innovonto.utils.batchmanager.model.ReviewStatus;
import de.fuberlin.innovonto.utils.batchmanager.model.Reviewable;

public class MockBatchElement implements Reviewable {
    private final long id;
    private ReviewStatus reviewStatus;

    public MockBatchElement(long id) {
        this.id = id;
    }

    @Override
    public ReviewStatus getReviewStatus() {
        return reviewStatus;
    }

    public long getId() {
        return id;
    }
}
