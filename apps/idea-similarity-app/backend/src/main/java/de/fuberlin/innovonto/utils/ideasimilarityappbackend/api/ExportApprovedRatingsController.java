package de.fuberlin.innovonto.utils.ideasimilarityappbackend.api;

import de.fuberlin.innovonto.utils.common.vocabulary.MTURK;
import de.fuberlin.innovonto.utils.ideasimilarityappbackend.management.export.RatingsExportService;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.riot.RDFDataMgr;
import org.apache.jena.riot.RDFFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.io.StringWriter;

import static de.fuberlin.innovonto.utils.common.JsonLDUtils.framedJsonLdOutput;

@RestController
@RequestMapping("/api/management/export")
public class ExportApprovedRatingsController {
    private final RatingsExportService ratingsExportService;

    @Autowired
    public ExportApprovedRatingsController(RatingsExportService ratingsExportService) {
        this.ratingsExportService = ratingsExportService;
    }

    @GetMapping(value = "/usableRatingsForProject/xml", produces = MediaType.APPLICATION_XML_VALUE)
    @ResponseBody
    public String getUsableRatingsForProjectAsXml(@RequestParam(value = "ratingProjectId") String ratingProjectId,
                                                  @RequestParam(value = "includeIdeaDetails", required = false) Boolean includeIdeaDetails) {
        if (includeIdeaDetails == null) {
            includeIdeaDetails = false;
        }
        final Model ratingsModel = ratingsExportService.exportUsableRatings(ratingProjectId, includeIdeaDetails);
        final StringWriter graphWriter = new StringWriter();
        RDFDataMgr.write(graphWriter, ratingsModel, RDFFormat.RDFXML_PRETTY);
        return graphWriter.toString();
    }

    @GetMapping(value = "/usableRatingsForProject", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String getUsableRatingsForProjectAsJson(@RequestParam(value = "ratingProjectId") String ratingProjectId,
                                                   @RequestParam(value = "includeIdeaDetails", required = false) Boolean includeIdeaDetails) {
        if (includeIdeaDetails == null) {
            includeIdeaDetails = false;
        }
        final Model ratingsModel = ratingsExportService.exportUsableRatings(ratingProjectId, includeIdeaDetails);
        return framedJsonLdOutput(ratingsModel, MTURK.mturkSession.toString());
    }
}
