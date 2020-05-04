package de.fuberlin.innovonto.utils.batchmanager.services;

import de.fuberlin.innovonto.utils.batchmanager.api.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.apache.commons.lang3.ObjectUtils.isNotEmpty;

public class BatchAllocationService {
    private static final Logger log = LoggerFactory.getLogger(BatchAllocationService.class);

    private final ProjectService projectService;
    private final BatchService batchService;

    public BatchAllocationService(ProjectService projectService, BatchService batchService) {
        this.projectService = projectService;
        this.batchService = batchService;
    }

    private Batch findABatchForAProject(String hitId, String workerId, String assignmentId, Project project) {
        final List<Batch> batches = project.getBatches();
        final List<Batch> unallocatedBatches = batches.stream().filter((b) -> b.getBatchState().equals(BatchState.UNALLOCATED)).sorted(Comparator.comparing(Batch::getLastPublished)).collect(Collectors.toList());
        final Batch result;
        if (isNotEmpty(unallocatedBatches)) {
            result = unallocatedBatches.get(0);
        } else {
            final List<Batch> unsubmittedBatches = batches.stream()
                    .filter((b) -> !b.getBatchState().equals(BatchState.SUBMITTED)).sorted(Comparator.comparing(Batch::getLastPublished)).collect(Collectors.toList());
            if (isNotEmpty(unsubmittedBatches)) {
                result = unsubmittedBatches.get(0);
                log.warn("RE-ALLOCATING UNSUBMITTED BATCH: " + result);
                //TODO mturk specifics
                log.warn("Old Allocation was: (" + result.getHWA() + "), new allocation will be: (" + hitId + "|" + workerId + "|" + assignmentId + "). Continuing.");
            } else {
                throw new IllegalStateException("Tried to allocate a batch for a project where all batches are already submitted.");
            }
        }
        return result;
    }

    public Batch allocateBatchFor(String projectId, String hitId, String workerId, String assignmentId) {
        Optional<Project> byId = projectService.findById(projectId);
        if (byId.isPresent()) {
            //If there is submit-data for this Batch, return the same batch
            Optional<Batch> alreadySubmittedBatch = batchService.findByHitIdAndWorkerIdAndAssignmentId(hitId, workerId, assignmentId);
            if (alreadySubmittedBatch.isPresent()) {
                final Batch batch = alreadySubmittedBatch.get();
                if (batch.getBatchState().equals(BatchState.SUBMITTED)) {
                    log.info("Returning already submitted batch: " + batch);
                    return batch;
                } else {
                    log.info("Got HWA batch that was not submitted yet, returning it");
                    //TODO update something here?
                    return batch;
                }
            }
            //if there is already a batch for the assignment id: return this one
            Optional<Batch> byAssignmentId = batchService.findByAssignmentId(assignmentId);
            if (byAssignmentId.isPresent()) {
                Batch batch = byAssignmentId.get();
                if (batch.getBatchState().equals(BatchState.SUBMITTED)) {
                    log.error("Tried to allocate a batch that is already submitted! Batch was:" + batch + " ,HWA is: (" + hitId + "|" + workerId + "|" + assignmentId + ")");
                    log.error("Try to find another batch that we could give to this assignment.");
                    batch = findABatchForAProject(hitId, workerId, assignmentId, byId.get());
                }
                if (!hitId.equals(batch.getHitId()) || !(workerId.equals(batch.getWorkerId())) || assignmentId.equals(batch.getAssignmentId())) {
                    log.info("HWA Missmatch between batch: (" + batch.getHWA() + ") and allocation request (" + hitId + "|" + workerId + "|" + assignmentId + "). Continuing.");
                }
                batch.setLastPublished(LocalDateTime.now());
                batch.setBatchState(BatchState.ALLOCATED);
                batch.setHitId(hitId);
                batch.setWorkerId(workerId);
                batch.setAssignmentId(assignmentId);
                return batch;
            } else {
                final Project project = byId.get();
                final Batch result = findABatchForAProject(hitId, workerId, assignmentId, project);
                result.setLastPublished(LocalDateTime.now());
                result.setBatchState(BatchState.ALLOCATED);
                result.setHitId(hitId);
                result.setWorkerId(workerId);
                result.setAssignmentId(assignmentId);
                //TODO
                //Collections.shuffle(result.getPairs());
                batchService.save(result);
                return result;
            }
        } else {
            throw new RuntimeException("Couldn't find project for id: " + projectId);
        }
    }


}
