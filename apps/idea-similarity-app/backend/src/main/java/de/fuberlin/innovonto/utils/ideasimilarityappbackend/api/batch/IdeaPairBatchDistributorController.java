package de.fuberlin.innovonto.utils.ideasimilarityappbackend.api.batch;

import de.fuberlin.innovonto.utils.ideasimilarityappbackend.api.MturkSesssionInformationMissingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import static org.apache.commons.lang3.StringUtils.isBlank;

@RestController
@RequestMapping("/api/mturk/content/")
public class IdeaPairBatchDistributorController {
    private IdeaPairBatchDistributorService distributorService;

    @Autowired
    public IdeaPairBatchDistributorController(IdeaPairBatchDistributorService distributorService) {
        this.distributorService = distributorService;
    }

    @ResponseBody
    @GetMapping(value = "ratingpairs")
    public IdeaPairBatchDTO getRatingPairsFor(@RequestParam String hitId, @RequestParam String workerId, @RequestParam String assignmentId, @RequestParam int numberOfPairs) {
        if (isBlank(hitId) || isBlank(workerId) || isBlank(assignmentId)) {
            throw new MturkSesssionInformationMissingException("Could not find mturk session information (HWA) on the result object.");
        }
        return distributorService.getBatchFor(hitId,workerId,assignmentId,numberOfPairs);
    }

}
