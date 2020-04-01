package de.fuberlin.innovonto.utils.ideasimilarityappbackend.api.results;

import de.fuberlin.innovonto.utils.ideasimilarityappbackend.api.MturkRatingSession;
import de.fuberlin.innovonto.utils.ideasimilarityappbackend.api.MturkRatingSessionRepository;
import de.fuberlin.innovonto.utils.ideasimilarityappbackend.api.MturkSesssionInformationMissingException;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Map;

import static org.apache.commons.lang3.StringUtils.isBlank;

@RestController
@RequestMapping("/api/mturk/")
public class SubmitResultRestController {
    private MturkRatingSessionRepository mturkRatingSessionRepository;

    @Autowired
    public SubmitResultRestController(MturkRatingSessionRepository mturkRatingSessionRepository) {
        this.mturkRatingSessionRepository = mturkRatingSessionRepository;
    }

    @ResponseBody
    @PostMapping(value = "/rating/submit")
    public MturkRatingSession submitRatingTask(MturkSimilarityTaskResultDTO result) {
        if (result == null || isBlank(result.getHitId()) || isBlank(result.getAssignmentId()) || isBlank(result.getWorkerId())) {
            throw new MturkSesssionInformationMissingException("Could not find mturk session information (HWA) on the result object.");
        }
        //TODO implement.
        return new MturkRatingSession();
    }

    //TODO this is just a debug view:
    @ResponseBody
    @PostMapping(value = "/externalSubmit", produces = MediaType.APPLICATION_JSON_VALUE)
    public String submitHITDebug(HttpServletRequest request) {
        final JSONObject result = new JSONObject();
        for (Map.Entry<String, String[]> parameter : request.getParameterMap().entrySet()) {
            result.put(parameter.getKey(), Arrays.toString(parameter.getValue()));
        }
        return result.toString();
    }
}
