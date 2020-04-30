package de.fuberlin.innovonto.utils.batchmanager.services;

import de.fuberlin.innovonto.utils.batchmanager.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.Optional;

public class SubmissionService<BE, BRE, S> {
    private static final Logger log = LoggerFactory.getLogger(SubmissionService.class);

    private final SubmissionRepository<BRE, S> submissionRepository;
    private final ProjectRepository<BE, BRE, S> projectRepository;
    private final BatchRepository batchRepository;

    public SubmissionService(SubmissionRepository<BRE, S> submissionRepository, ProjectRepository<BE, BRE, S> projectRepository, BatchRepository batchRepository) {
        this.submissionRepository = submissionRepository;
        this.projectRepository = projectRepository;
        this.batchRepository = batchRepository;
    }

    //TODO refactor
    public Submission<BRE, S> updateProjectAndBatchAndSave(Submission<BRE, S> submission) {
        //Check if there is already a submission for this HWA combination
        final Optional<Submission<BRE, S>> alreadySubmittedSession = submissionRepository.findByHitIdAndWorkerIdAndAssignmentId(submission.getHitId(), submission.getWorkerId(), submission.getAssignmentId());
        if (alreadySubmittedSession.isPresent()) {
            log.info("Skipping duplicate submit of: " + alreadySubmittedSession.get());
            return alreadySubmittedSession.get();
        }

        //Check if rating project is in db
        Optional<Project<BE, BRE, S>> projectById = projectRepository.findById(submission.getProjectId());
        if (projectById.isEmpty()) {
            throw new IllegalStateException("Tried to save submission for a project that is not in the database: " + submission.getProjectId());
        }

        //Get the batch for this assignment-id.
        final Optional<Batch<?>> batchByAssignmentId = batchRepository.findByAssignmentId(submission.getAssignmentId());
        if (batchByAssignmentId.isEmpty()) {
            throw new IllegalStateException("Tried to submit ratings without an allocated batch. AssignmentId is: " + submission.getAssignmentId());
        }
        Project<BE, BRE, S> sourceProject = projectById.get();
        final Batch<?> sourceBatch = batchByAssignmentId.get();

        //TODO die timestamps werden aktualisiert
        //todo does this save the survey data as well?
        final Submission<BRE, S> savedSubmission = submissionRepository.save(submission);
        //Update Source Batch
        sourceBatch.setBatchState(BatchState.SUBMITTED);
        sourceBatch.setSubmitted(LocalDateTime.now());
        sourceBatch.addSubmissionId(savedSubmission.getId());
        batchRepository.save(sourceBatch);

        //Update rating project
        sourceProject.addSubmission(savedSubmission);
        projectRepository.save(sourceProject);

        return savedSubmission;
    }
}
