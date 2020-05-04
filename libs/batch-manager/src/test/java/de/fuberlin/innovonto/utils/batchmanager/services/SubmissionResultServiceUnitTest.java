package de.fuberlin.innovonto.utils.batchmanager.services;

import de.fuberlin.innovonto.utils.batchmanager.api.*;
import de.fuberlin.innovonto.utils.batchmanager.services.testmodel.InMemoryBatch;
import de.fuberlin.innovonto.utils.batchmanager.services.testmodel.InMemoryProject;
import de.fuberlin.innovonto.utils.batchmanager.services.testmodel.InMemorySubmission;
import de.fuberlin.innovonto.utils.batchmanager.services.testmodel.MockBatchElement;
import org.junit.jupiter.api.Test;
import org.mockito.stubbing.Answer;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class SubmissionResultServiceUnitTest {

    /**
     * This is the "good" case: There is a submission for a batch, that is allocated, but not submitted yet.
     */
    @Test
    public void testSuccessfulSubmission() {
        final UUID submissionId = UUID.fromString("fabb5465-b653-4e68-976b-573b88f7956c");
        final InMemoryProject testProject = new InMemoryProject();
        testProject.setId("testproject");

        final List<InMemoryBatch> sourceBatches = new ArrayList<>(1);
        InMemoryBatch mockBatch = new InMemoryBatch(Collections.singletonList(new MockBatchElement(1L)));
        sourceBatches.add(mockBatch);
        testProject.setBatches(sourceBatches);

        final ProjectService<InMemoryProject> projectRepository = mock(ProjectService.class);
        when(projectRepository.findById("testproject")).thenReturn(Optional.of(testProject));

        final BatchService<InMemoryBatch> batchRepository = mock(BatchService.class);
        when(batchRepository.findByAssignmentId("test-assignment")).thenReturn(Optional.of(mockBatch));

        final SubmissionService<InMemorySubmission> submissionRepository = createMockSubmissionRepository();

        final SubmissionResultService submissionResultService = new SubmissionResultService(submissionRepository, projectRepository, batchRepository);
        final InMemorySubmission inputSubmission = new InMemorySubmission();
        inputSubmission.setHitId("test-hit");
        inputSubmission.setWorkerId("test-worker");
        inputSubmission.setAssignmentId("test-assignment");
        inputSubmission.setProjectId("testproject");
        final Submission result = submissionResultService.updateProjectAndBatchAndSave(inputSubmission);
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(submissionId);

        assertThat(mockBatch.getBatchState()).isEqualTo(BatchState.SUBMITTED);
        assertThat(mockBatch.getSubmissionIds().get(0)).isEqualTo(submissionId);

        assertThat(testProject.getNumberOfBatchesInProgress()).isEqualTo(0L);
        assertThat(testProject.getNumberOfBatchesSubmitted()).isEqualTo(1L);
        //TODO
        //assertThat(testProject.getSubmissions().get(0)).isNotNull();
        //assertThat(testProject.getSubmissions().get(0).getId()).isEqualTo(submissionId);
    }

    private SubmissionService<InMemorySubmission> createMockSubmissionRepository() {
        final SubmissionService<InMemorySubmission> submissionRepository = mock(SubmissionService.class);
        when(submissionRepository.save(any(InMemorySubmission.class))).thenAnswer(
                (Answer<Submission>) invocation -> {
                    Object argument = invocation.getArgument(0);
                    if (argument instanceof InMemorySubmission) {
                        InMemorySubmission submission = (InMemorySubmission) argument;
                        submission.setId(UUID.fromString("fabb5465-b653-4e68-976b-573b88f7956c"));
                        return submission;
                    } else {
                        throw new RuntimeException("Weird submission save:" + invocation);
                    }
                }
        );
        return submissionRepository;
    }
}