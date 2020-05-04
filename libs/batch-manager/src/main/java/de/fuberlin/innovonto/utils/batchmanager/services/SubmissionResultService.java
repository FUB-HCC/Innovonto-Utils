package de.fuberlin.innovonto.utils.batchmanager.services;

import de.fuberlin.innovonto.utils.batchmanager.api.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.Optional;

public class SubmissionResultService {
    private static final Logger log = LoggerFactory.getLogger(SubmissionResultService.class);

    private final SubmissionService submissionRepository;
    private final ProjectService projectRepository;
    private final BatchService batchRepository;

    public SubmissionResultService(SubmissionService submissionRepository, ProjectService projectRepository, BatchService batchRepository) {
        this.submissionRepository = submissionRepository;
        this.projectRepository = projectRepository;
        this.batchRepository = batchRepository;
    }

    //TODO refactor: this does multiple things at once.
    public Submission updateProjectAndBatchAndSave(Submission submission) {
        //Check if there is already a submission for this HWA combination
        final Optional<Submission> alreadySubmittedSession = submissionRepository.findByHitIdAndWorkerIdAndAssignmentId(submission.getHitId(), submission.getWorkerId(), submission.getAssignmentId());
        if (alreadySubmittedSession.isPresent()) {
            log.info("Skipping duplicate submit of: " + alreadySubmittedSession.get());
            return alreadySubmittedSession.get();
        }

        //Check if rating project is in db
        Optional<Project> projectById = projectRepository.findById(submission.getProjectId());
        if (projectById.isEmpty()) {
            throw new IllegalStateException("Tried to save submission for a project that is not in the database: " + submission.getProjectId());
        }

        //Get the batch for this assignment-id.
        final Optional<Batch> batchByAssignmentId = batchRepository.findByAssignmentId(submission.getAssignmentId());
        if (batchByAssignmentId.isEmpty()) {
            throw new IllegalStateException("Tried to submit ratings without an allocated batch. AssignmentId is: " + submission.getAssignmentId());
        }
        Project sourceProject = projectById.get();
        final Batch sourceBatch = batchByAssignmentId.get();

        //TODO die timestamps werden aktualisiert
        //todo does this save the survey data as well?
        final Submission savedSubmission = submissionRepository.save(submission);
        //Update Source Batch
        sourceBatch.setBatchState(BatchState.SUBMITTED);
        sourceBatch.setSubmitted(LocalDateTime.now());
        sourceBatch.addSubmissionId(savedSubmission.getId());
        batchRepository.save(sourceBatch);

        //TODO what would be the benefit of having project -> batches -> submissions instead of project -> batches && project -> submissions?
        //Update rating project
        sourceProject.addSubmission(savedSubmission);
        projectRepository.save(sourceProject);

        return savedSubmission;
    }
}
