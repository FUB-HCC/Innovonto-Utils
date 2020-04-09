package de.fuberlin.innovonto.utils.ideasimilarityappbackend.management.export;

import de.fuberlin.innovonto.utils.common.vocabulary.GI2MO;
import de.fuberlin.innovonto.utils.common.vocabulary.INOV;
import de.fuberlin.innovonto.utils.common.vocabulary.MTURK;
import de.fuberlin.innovonto.utils.common.vocabulary.OID;
import de.fuberlin.innovonto.utils.ideasimilarityappbackend.model.*;
import org.apache.jena.datatypes.xsd.XSDDatatype;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.vocabulary.DCTerms;
import org.apache.jena.vocabulary.RDF;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Service
public class RatingsExportService {
    private static final Logger log = LoggerFactory.getLogger(RatingsExportService.class);
    //TODO make configurable (per RatingProject)
    private static final int MIN_RATING_VALUE = 0;
    private static final int MAX_RATING_VALUE = 400;


    private final MturkRatingSessionRepository mturkRatingSessionRepository;
    private final IdeaRepository ideaRepository;

    @Autowired
    public RatingsExportService(MturkRatingSessionRepository mturkRatingSessionRepository, IdeaRepository ideaRepository) {
        this.mturkRatingSessionRepository = mturkRatingSessionRepository;
        this.ideaRepository = ideaRepository;
    }

    //TODO export approved ratings
    public Model exportUsableRatings(String ratingProjectId, boolean includeIdeaDetails) {
        final Model model = ModelFactory.createDefaultModel();
        model.setNsPrefix("gi2mo", GI2MO.uri);
        model.setNsPrefix("dcterms", DCTerms.getURI());
        model.setNsPrefix("inov", INOV.uri);
        model.setNsPrefix("mturk", MTURK.uri);
        model.setNsPrefix("oid", OID.uri);

        final Iterable<MturkRatingSession> usableSessions = mturkRatingSessionRepository.findAllByRatingProjectIdAndReviewStatus(ratingProjectId, ReviewStatus.USABLE);
        final Set<UUID> localIdeaIds = new HashSet<>();
        for (MturkRatingSession session : usableSessions) {
            //Block Session
            final Resource outputSession = model.createResource(OID.MTURK_SESSION_PREFIX + session.getId());
            outputSession.addProperty(RDF.type, MTURK.mturkSession);
            outputSession.addProperty(MTURK.hitId, MTURK.HIT_PREFIX + session.getHitId());
            outputSession.addProperty(MTURK.workerId, MTURK.WORKER_PREFIX + session.getWorkerId());
            outputSession.addProperty(MTURK.assignmentId, MTURK.ASSIGNMENT_PREFIX + session.getAssignmentId());
            outputSession.addProperty(OID.hasRatingProject, OID.RATING_PROJECT_PREFIX + session.getRatingProjectId());

            outputSession.addProperty(DCTerms.created, session.getSubmitted().format(DateTimeFormatter.ISO_DATE_TIME), XSDDatatype.XSDdateTime);

            outputSession.addProperty(OID.fulltextFeedback, session.getFulltextFeedback());
            outputSession.addLiteral(OID.clarityRating, session.getClarityRating());

            //Block Similarity Rating
            for (RatedIdeaPair pair : session.getRatings()) {
                if (pair.getReviewStatus().equals(ReviewStatus.USABLE)) {
                    final Resource outputRating = model.createResource(OID.RATING_PREFIX + pair.getId());
                    outputRating.addProperty(RDF.type, OID.SimilarityRating);
                    outputRating.addProperty(MTURK.hasMturkSession, outputSession);
                    outputRating.addLiteral(GI2MO.ratingValue, pair.getSimilarityRating());
                    outputRating.addLiteral(GI2MO.minRatingValue, MIN_RATING_VALUE);
                    outputRating.addLiteral(GI2MO.maxRatingValue, MAX_RATING_VALUE);
                    outputRating.addProperty(DCTerms.title, "Manual Crowdsourced Rating");
                    outputRating.addProperty(DCTerms.description, "A similarity rating, obtained via a slider rating between 0 and 400.");
                    //TODO make idea prefix configurable
                    outputRating.addProperty(OID.hasLeftIdea, INOV.IDEA_PREFIX + pair.getLeftIdea());
                    outputRating.addProperty(OID.hasRightIdea, INOV.IDEA_PREFIX + pair.getRightIdea());
                    localIdeaIds.add(UUID.fromString(pair.getLeftIdea()));
                    localIdeaIds.add(UUID.fromString(pair.getRightIdea()));
                    outputRating.addProperty(DCTerms.created, session.getSubmitted().format(DateTimeFormatter.ISO_DATE_TIME), XSDDatatype.XSDdateTime);

                    outputSession.addProperty(OID.hasSimilarityRating, outputRating);
                } else {
                    log.warn("Skipping non-usable rating: " + pair + " in usable session: " + session);
                }
            }

            //Block Ideas
            //TODO not included in framed output?
            if (includeIdeaDetails) {
                for (Idea idea : ideaRepository.findAllById(localIdeaIds)) {
                    final Resource outputIdea = model.createResource(INOV.IDEA_PREFIX + idea.getId());
                    outputIdea.addProperty(RDF.type, GI2MO.Idea);
                    outputIdea.addProperty(GI2MO.content, idea.getContent());
                    outputIdea.addProperty(GI2MO.hasIdeaContest, idea.getHasIdeaContest());
                }
            }
        }
        return model;
    }


}
