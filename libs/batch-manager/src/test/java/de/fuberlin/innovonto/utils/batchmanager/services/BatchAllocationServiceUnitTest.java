package de.fuberlin.innovonto.utils.batchmanager.services;


import de.fuberlin.innovonto.utils.batchmanager.api.Batch;
import de.fuberlin.innovonto.utils.batchmanager.api.BatchService;
import de.fuberlin.innovonto.utils.batchmanager.api.BatchState;
import de.fuberlin.innovonto.utils.batchmanager.api.ProjectService;
import de.fuberlin.innovonto.utils.batchmanager.services.testmodel.InMemoryBatch;
import de.fuberlin.innovonto.utils.batchmanager.services.testmodel.InMemoryProject;
import de.fuberlin.innovonto.utils.batchmanager.services.testmodel.MockBatchElement;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

class BatchAllocationServiceUnitTest {

    /**
     * This is the case when there are still unallocated batches in the project. The requester
     * should the first unallocated batch from the current project
     */
    @Test
    public void testSimpleAllocation() {
        final InMemoryProject testProject = new InMemoryProject();
        testProject.setId("testproject");

        final List<InMemoryBatch> sourceBatches = new ArrayList<>(2);
        sourceBatches.add(new InMemoryBatch(Collections.singletonList(new MockBatchElement(1L))));
        sourceBatches.add(new InMemoryBatch(Collections.singletonList(new MockBatchElement(2L))));
        testProject.setBatches(sourceBatches);

        final ProjectService mockProjectRepository = mock(ProjectService.class);
        when(mockProjectRepository.findById("testproject")).thenReturn(Optional.of(testProject));

        final BatchService batchRepository = mock(BatchService.class);

        final BatchAllocationService batchAllocationService = new BatchAllocationService(mockProjectRepository, batchRepository);

        final Batch firstAllocation = batchAllocationService.allocateBatchFor("testproject", "test-hit", "test-worker", "test-assignment");

        verify(batchRepository).findByHitIdAndWorkerIdAndAssignmentId("test-hit", "test-worker", "test-assignment");
        verify(batchRepository).save(any(Batch.class));

        assertThat(firstAllocation).isInstanceOf(InMemoryBatch.class);
        assertThat(firstAllocation).isNotNull();
        assertThat(firstAllocation.getBatchState()).isEqualTo(BatchState.ALLOCATED);
        assertThat(firstAllocation.getLastPublished()).isAfter(LocalDateTime.MIN);
        assertThat(firstAllocation.getHitId()).isEqualTo("test-hit");
        assertThat(firstAllocation.getWorkerId()).isEqualTo("test-worker");
        assertThat(firstAllocation.getAssignmentId()).isEqualTo("test-assignment");

        assertThat(((InMemoryBatch) firstAllocation).getBatchElements()).hasSize(1);

        assertThat(testProject.getNumberOfBatches()).isEqualTo(2L);
        assertThat(testProject.getNumberOfBatchesInProgress()).isEqualTo(1L);
        assertThat(testProject.getNumberOfBatchesSubmitted()).isEqualTo(0L);

        final Batch secondAllocation = batchAllocationService.allocateBatchFor("testproject", "test-hit", "test-worker-2", "test-assignment-2");
        assertThat(secondAllocation).isNotNull();
        assertThat(secondAllocation.getBatchState()).isEqualTo(BatchState.ALLOCATED);
        assertThat(secondAllocation.getLastPublished()).isAfter(LocalDateTime.MIN);
        assertThat(secondAllocation.getHitId()).isEqualTo("test-hit");
        assertThat(secondAllocation.getWorkerId()).isEqualTo("test-worker-2");
        assertThat(secondAllocation.getAssignmentId()).isEqualTo("test-assignment-2");
        assertThat(((InMemoryBatch) secondAllocation).getBatchElements()).hasSize(1);

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
        final InMemoryProject testProject = new InMemoryProject();
        testProject.setId("testproject");

        final List<InMemoryBatch> sourceBatches = new ArrayList<>(1);
        sourceBatches.add(new InMemoryBatch(Collections.singletonList(new MockBatchElement(1L))));
        testProject.setBatches(sourceBatches);

        final ProjectService<InMemoryProject> mockProjectRepository = mock(ProjectService.class);
        when(mockProjectRepository.findById("testproject")).thenReturn(Optional.of(testProject));

        final BatchService<InMemoryBatch> batchService = mock(BatchService.class);

        final BatchAllocationService batchAllocationService = new BatchAllocationService(mockProjectRepository, batchService);

        final Batch firstAllocation = batchAllocationService.allocateBatchFor("testproject", "test-hit", "test-worker", "test-assignment");
        assertThat(firstAllocation).isNotNull();
        assertThat(firstAllocation.getHitId()).isEqualTo("test-hit");
        assertThat(firstAllocation.getWorkerId()).isEqualTo("test-worker");
        assertThat(firstAllocation.getAssignmentId()).isEqualTo("test-assignment");
        assertThat(testProject.getNumberOfBatchesInProgress()).isEqualTo(1L);
        final Batch secondAllocation = batchAllocationService.allocateBatchFor("testproject", "test-hit", "test-worker-2", "test-assignment-2");
        assertThat(secondAllocation).isNotNull();
        assertThat(secondAllocation.getBatchState()).isEqualTo(BatchState.ALLOCATED);
        assertThat(secondAllocation.getLastPublished()).isAfter(LocalDateTime.MIN);
        assertThat(secondAllocation.getHitId()).isEqualTo("test-hit");
        assertThat(secondAllocation.getWorkerId()).isEqualTo("test-worker-2");
        assertThat(secondAllocation.getAssignmentId()).isEqualTo("test-assignment-2");
        assertThat(testProject.getNumberOfBatchesInProgress()).isEqualTo(1L);
    }

    /**
     * This is the case when we get a new allocation request for an already submitted batch.
     * This is the case if the submit to the backend succeeded but the submit to amazon failed for some reason.
     * This should return the already existing/submitted batch.
     */
    @Test
    public void testReAllocationAfterSubmit() {

    }

    /**
     * This is the case if we get a batch that is submitted with an assignment,
     * but the workerId and hitId are different. I think i saw this once when letting
     * the similarity batches. The result should be a new (unsubmitted or unallocated) batch for this assignment
     */
    @Test
    public void testReAllocationForSubmittedAssignmentId() {
        final InMemoryProject testProject = new InMemoryProject();
        testProject.setId("testproject");

        final List<InMemoryBatch> sourceBatches = new ArrayList<>(1);
        final InMemoryBatch submittedBatch = createAlreadyAllocatedBatch();
        submittedBatch.setBatchState(BatchState.SUBMITTED);
        sourceBatches.add(submittedBatch);
        sourceBatches.add(new InMemoryBatch(Collections.singletonList(new MockBatchElement(2L))));
        testProject.setBatches(sourceBatches);

        final ProjectService<InMemoryProject> mockProjectRepository = mock(ProjectService.class);
        when(mockProjectRepository.findById("testproject")).thenReturn(Optional.of(testProject));

        final BatchService<InMemoryBatch> batchRepository = mock(BatchService.class);
        when(batchRepository.findByHitIdAndWorkerIdAndAssignmentId("test-hit", "test-worker", "test-assignment")).thenReturn(Optional.of(submittedBatch));
        when(batchRepository.findByAssignmentId("test-assignment")).thenReturn(Optional.of(submittedBatch));

        final BatchAllocationService batchAllocationService = new BatchAllocationService(mockProjectRepository, batchRepository);

        final Batch firstAllocation = batchAllocationService.allocateBatchFor("testproject", "test-hit", "test-worker", "test-assignment");
        assertThat(firstAllocation).isNotNull();
        assertThat(firstAllocation.getHitId()).isEqualTo("test-hit");
        assertThat(firstAllocation.getWorkerId()).isEqualTo("test-worker");
        assertThat(firstAllocation.getAssignmentId()).isEqualTo("test-assignment");
        assertThat(testProject.getBatches()).hasSize(2);
        assertThat(testProject.getNumberOfBatchesSubmitted()).isEqualTo(1L);
        assertThat(testProject.getNumberOfBatchesInProgress()).isEqualTo(0L);

        final Batch secondAllocation = batchAllocationService.allocateBatchFor("testproject", "test-hit", "test-worker-2", "test-assignment");
        assertThat(secondAllocation).isNotNull();
        assertThat(secondAllocation.getBatchState()).isEqualTo(BatchState.ALLOCATED);
        assertThat(secondAllocation.getLastPublished()).isAfter(LocalDateTime.MIN);
        assertThat(secondAllocation.getHitId()).isEqualTo("test-hit");
        assertThat(secondAllocation.getWorkerId()).isEqualTo("test-worker-2");
        assertThat(secondAllocation.getAssignmentId()).isEqualTo("test-assignment");
        assertThat(testProject.getBatches()).hasSize(2);
        assertThat(testProject.getNumberOfBatchesSubmitted()).isEqualTo(1L);
        assertThat(testProject.getNumberOfBatchesInProgress()).isEqualTo(1L);
    }

    /**
     * This is the case when we get a new allocation request, but there is already an allocation
     * for the HWA combination. The request should get the same batch as before.
     */
    @Test
    public void testReloading() {
        //Setup
        final InMemoryProject testProject = new InMemoryProject();
        testProject.setId("testproject");

        final List<InMemoryBatch> sourceBatches = new ArrayList<>(1);
        sourceBatches.add(createAlreadyAllocatedBatch());
        sourceBatches.add(new InMemoryBatch(Collections.singletonList(new MockBatchElement(2L))));
        testProject.setBatches(sourceBatches);

        final ProjectService<InMemoryProject> mockProjectRepository = mock(ProjectService.class);
        when(mockProjectRepository.findById("testproject")).thenReturn(Optional.of(testProject));

        final BatchService<InMemoryBatch> batchRepository = mock(BatchService.class);
        when(batchRepository.findByHitIdAndWorkerIdAndAssignmentId("test-hit", "test-worker", "test-assignment")).thenReturn(Optional.of(sourceBatches.get(0)));

        final BatchAllocationService batchAllocationService = new BatchAllocationService(mockProjectRepository, batchRepository);

        //Test
        final Batch firstAllocation = batchAllocationService.allocateBatchFor("testproject", "test-hit", "test-worker", "test-assignment");
        assertThat(firstAllocation).isNotNull();
        assertThat(firstAllocation.getHitId()).isEqualTo("test-hit");
        assertThat(firstAllocation.getWorkerId()).isEqualTo("test-worker");
        assertThat(firstAllocation.getAssignmentId()).isEqualTo("test-assignment");
        assertThat(testProject.getNumberOfBatchesInProgress()).isEqualTo(1L);

        final Batch secondAllocation = batchAllocationService.allocateBatchFor("testproject", "test-hit", "test-worker", "test-assignment");
        assertThat(secondAllocation).isNotNull();
        assertThat(secondAllocation.getBatchState()).isEqualTo(BatchState.ALLOCATED);
        assertThat(secondAllocation.getLastPublished()).isAfter(LocalDateTime.MIN);
        assertThat(firstAllocation.getHitId()).isEqualTo("test-hit");
        assertThat(firstAllocation.getWorkerId()).isEqualTo("test-worker");
        assertThat(firstAllocation.getAssignmentId()).isEqualTo("test-assignment");
        assertThat(testProject.getNumberOfBatchesInProgress()).isEqualTo(1L);
    }

    private InMemoryBatch createAlreadyAllocatedBatch() {
        final InMemoryBatch reloadableBatch = new InMemoryBatch(Collections.singletonList(new MockBatchElement(1L)));
        reloadableBatch.setHitId("test-hit");
        reloadableBatch.setWorkerId("test-worker");
        reloadableBatch.setAssignmentId("test-assignment");
        reloadableBatch.setBatchState(BatchState.ALLOCATED);
        reloadableBatch.setLastPublished(LocalDateTime.now());
        return reloadableBatch;
    }

    /**
     * This is the case when we get a new allocation request, and all batches for the project are submitted.
     * The requester should get the batch that was allocated the longest ago?
     * TODO rename to testAllocationToFinishedProject?
     */
    @Test
    public void testOvercommitment() {
        //Setup
        final InMemoryProject testProject = new InMemoryProject();
        testProject.setId("testproject");

        final List<InMemoryBatch> sourceBatches = new ArrayList<>(1);
        sourceBatches.add(new InMemoryBatch(Collections.singletonList(new MockBatchElement(1L))));
        testProject.setBatches(sourceBatches);

        final ProjectService<InMemoryProject> mockProjectRepository = mock(ProjectService.class);
        when(mockProjectRepository.findById("testproject")).thenReturn(Optional.of(testProject));

        final BatchService<InMemoryBatch> batchRepository = mock(BatchService.class);

        final BatchAllocationService batchAllocationService = new BatchAllocationService(mockProjectRepository, batchRepository);

        //Test
        final Batch firstAllocation = batchAllocationService.allocateBatchFor("testproject", "test-hit", "test-worker", "test-assignment");
        assertThat(firstAllocation).isNotNull();
        assertThat(firstAllocation.getHitId()).isEqualTo("test-hit");
        assertThat(firstAllocation.getWorkerId()).isEqualTo("test-worker");
        assertThat(firstAllocation.getAssignmentId()).isEqualTo("test-assignment");
        assertThat(testProject.getNumberOfBatchesInProgress()).isEqualTo(1L);

        //TODO submit firstAllocation.


        final Batch secondAllocation = batchAllocationService.allocateBatchFor("testproject", "test-hit", "test-worker-2", "test-assignment-2");
        assertThat(secondAllocation).isNotNull();
        assertThat(secondAllocation.getBatchState()).isEqualTo(BatchState.ALLOCATED);
        assertThat(secondAllocation.getLastPublished()).isAfter(LocalDateTime.MIN);
        assertThat(secondAllocation.getHitId()).isEqualTo("test-hit");
        assertThat(secondAllocation.getWorkerId()).isEqualTo("test-worker-2");
        assertThat(secondAllocation.getAssignmentId()).isEqualTo("test-assignment-2");
        //TODO what to do with the project here? this just shouldn't happen :/
        assertThat(testProject.getNumberOfBatchesInProgress()).isEqualTo(1L);
    }

    /**
     * This is the case when we get a new allocation request and the projectId is set to something not found in the database
     * This should throw an error.
     */
    @Test
    public void testAllocationToNonexistentProject() {
        final InMemoryProject testProject = new InMemoryProject();
        testProject.setId("testproject");

        final List<InMemoryBatch> sourceBatches = new ArrayList<>(1);
        sourceBatches.add(new InMemoryBatch(Collections.singletonList(new MockBatchElement(1L))));
        testProject.setBatches(sourceBatches);

        final ProjectService<InMemoryProject> mockProjectRepository = mock(ProjectService.class);
        when(mockProjectRepository.findById("testproject")).thenReturn(Optional.of(testProject));

        final BatchService<InMemoryBatch> batchRepository = mock(BatchService.class);

        final BatchAllocationService batchAllocationService = new BatchAllocationService(mockProjectRepository, batchRepository);

        assertThatThrownBy(() -> batchAllocationService.allocateBatchFor("NON-EXISTENT", "test-hit", "test-worker", "test-assignment"))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Couldn't find project for id: NON-EXISTENT");
    }
}