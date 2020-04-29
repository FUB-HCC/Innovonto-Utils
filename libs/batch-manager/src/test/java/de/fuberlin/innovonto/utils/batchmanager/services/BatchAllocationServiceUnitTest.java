package de.fuberlin.innovonto.utils.batchmanager.services;


import de.fuberlin.innovonto.utils.batchmanager.model.*;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class BatchAllocationServiceUnitTest {

    /**
     * This is the case when there are still unallocated batches in the project. The requester
     * should the first unallocated batch from the current project
     */
    @Test
    public void testSimpleAllocation() {
        final Project<MockBatchElement, ?, ?> testProject = new Project<>();
        testProject.setId("testproject");

        final List<Batch<MockBatchElement>> sourceBatches = new ArrayList<>(2);
        sourceBatches.add(new Batch<>(Collections.singletonList(new MockBatchElement(1L))));
        sourceBatches.add(new Batch<>(Collections.singletonList(new MockBatchElement(2L))));
        testProject.setBatches(sourceBatches);

        final ProjectRepository<MockBatchElement> mockProjectRepository = mock(ProjectRepository.class);
        when(mockProjectRepository.findById("testproject")).thenReturn(Optional.of(testProject));

        final BatchRepository batchRepository = mock(BatchRepository.class);

        final BatchAllocationService<MockBatchElement> batchAllocationService = new BatchAllocationService<>(mockProjectRepository, batchRepository);

        final Batch<?> firstAllocation = batchAllocationService.allocateBatchFor("testproject", "test-hit", "test-worker", "test-assignment");

        assertThat(firstAllocation).isNotNull();
        assertThat(firstAllocation.getBatchState()).isEqualTo(BatchState.ALLOCATED);
        assertThat(firstAllocation.getLastPublished()).isAfter(LocalDateTime.MIN);
        assertThat(firstAllocation.getHitId()).isEqualTo("test-hit");
        assertThat(firstAllocation.getWorkerId()).isEqualTo("test-worker");
        assertThat(firstAllocation.getAssignmentId()).isEqualTo("test-assignment");
        //TODO cast?
        assertThat(firstAllocation.getBatchElements()).hasSize(1);

        assertThat(testProject.getBatches()).hasSize(2);
        assertThat(testProject.getNumberOfBatchesInProgress()).isEqualTo(1L);
        assertThat(testProject.getNumberOfBatchesSubmitted()).isEqualTo(0L);

        final Batch<?> secondAllocation = batchAllocationService.allocateBatchFor("testproject", "test-hit", "test-worker-2", "test-assignment-2");
        assertThat(secondAllocation).isNotNull();
        assertThat(secondAllocation.getBatchState()).isEqualTo(BatchState.ALLOCATED);
        assertThat(secondAllocation.getLastPublished()).isAfter(LocalDateTime.MIN);
        assertThat(secondAllocation.getHitId()).isEqualTo("test-hit");
        assertThat(secondAllocation.getWorkerId()).isEqualTo("test-worker-2");
        assertThat(secondAllocation.getAssignmentId()).isEqualTo("test-assignment-2");
        //TODO cast?
        assertThat(secondAllocation.getBatchElements()).hasSize(1);

        assertThat(testProject.getBatches()).hasSize(2);
        assertThat(testProject.getNumberOfBatchesInProgress()).isEqualTo(2L);
        assertThat(testProject.getNumberOfBatchesSubmitted()).isEqualTo(0L);

    }

    /**
     * This is the case when we get a new allocation request, all batches within the project are already allocated
     * but there are still unsubmitted batches.
     * In this case, the requester should get back the request, that was allocated the longest ago.
     */
    @Test
    public void testReAllocation() {

    }

    //TODO gleiches assignment aber andere W/A? Oder ist das bei "reallocation" mit abgedeckt?

    /**
     * This is the case when we get a new allocation request, but there is already an allocation
     * for the HWA combination. The request should get the same batch as before.
     */
    @Test
    public void testReloading() {

    }

    /**
     * This is the case when we get a new allocation request, and all batches for the project are submitted.
     * The requester should get the batch that was allocated the longest ago?
     * TODO rename to testAllocationToFinishedProject?
     */
    @Test
    public void testOvercommitment() {

    }

    /**
     * This is the case when we get a new allocation request and the projectId is set to something not found in the database
     * This should throw an error.
     */
    @Test
    public void testAllocationToNonexistentProject() {

    }
}