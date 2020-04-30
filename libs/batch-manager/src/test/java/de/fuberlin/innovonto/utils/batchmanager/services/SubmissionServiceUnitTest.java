package de.fuberlin.innovonto.utils.batchmanager.services;

import de.fuberlin.innovonto.utils.batchmanager.model.*;
import de.fuberlin.innovonto.utils.batchmanager.services.testutils.MockBatchElement;
import de.fuberlin.innovonto.utils.batchmanager.services.testutils.MockBatchResultElement;
import de.fuberlin.innovonto.utils.batchmanager.services.testutils.MockSurvey;
import org.junit.jupiter.api.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.time.LocalDateTime;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class SubmissionServiceUnitTest {

    /**
     * This is the "good" case: There is a submission for a batch, that is allocated, but not submitted yet.
     */
    @Test
    public void testSuccessfulSubmission() {
        final UUID submissionId = UUID.fromString("fabb5465-b653-4e68-976b-573b88f7956c");
        final Project<MockBatchElement, MockBatchResultElement, MockSurvey> testProject = new Project<>();
        testProject.setId("testproject");

        final List<Batch<MockBatchElement>> sourceBatches = new ArrayList<>(1);
        Batch<MockBatchElement> mockBatch = new Batch<>(Collections.singletonList(new MockBatchElement(1L)));
        sourceBatches.add(mockBatch);
        testProject.setBatches(sourceBatches);

        final ProjectRepository<MockBatchElement, MockBatchResultElement, MockSurvey> projectRepository = mock(ProjectRepository.class);
        when(projectRepository.findById("testproject")).thenReturn(Optional.of(testProject));

        final BatchRepository batchRepository = mock(BatchRepository.class);
        when(batchRepository.findByAssignmentId("test-assignment")).thenReturn(Optional.of(mockBatch));

        final SubmissionRepository<MockBatchResultElement,MockSurvey> submissionRepository = createMockSubmissionRepository();

        final SubmissionService<MockBatchElement, MockBatchResultElement, MockSurvey> submissionService = new SubmissionService<>(submissionRepository, projectRepository, batchRepository);
        final Submission<MockBatchResultElement,MockSurvey> inputSubmission = new Submission<>();
        inputSubmission.setHitId("test-hit");
        inputSubmission.setWorkerId("test-worker");
        inputSubmission.setAssignmentId("test-assignment");
        inputSubmission.setProjectId("testproject");
        final Submission<MockBatchResultElement, MockSurvey> result = submissionService.updateProjectAndBatchAndSave(inputSubmission);
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(submissionId);

        assertThat(mockBatch.getBatchState()).isEqualTo(BatchState.SUBMITTED);
        assertThat(mockBatch.getSubmissionIds().get(0)).isEqualTo(submissionId);

        assertThat(testProject.getNumberOfBatchesInProgress()).isEqualTo(0L);
        assertThat(testProject.getNumberOfBatchesSubmitted()).isEqualTo(1L);
        assertThat(testProject.getSubmissions().get(0)).isNotNull();
        assertThat(testProject.getSubmissions().get(0).getId()).isEqualTo(submissionId);
    }

    private SubmissionRepository<MockBatchResultElement,MockSurvey> createMockSubmissionRepository() {
        final SubmissionRepository<MockBatchResultElement,MockSurvey> submissionRepository = mock(SubmissionRepository.class);
        when(submissionRepository.save(any(Submission.class))).thenAnswer(
                (Answer<Submission<MockBatchResultElement,MockSurvey>>) invocation -> {
                    Object argument = invocation.getArgument(0);
                    //TODO isAssignableFrom
                    if (argument instanceof Submission) {
                        Submission<MockBatchResultElement,MockSurvey> submission = (Submission<MockBatchResultElement,MockSurvey>) argument;
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