package de.fuberlin.innovonto.utils.common.vocabulary;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.Resource;

/**
 * This namespace is used for legacy interactions with the ontoidea platform
 */
public class OID {
    /**
     * The namespace of the vocabulary as a string
     */
    public static final String uri = "http://purl.org/innovonto/legacy/types#";
    public static final String ANNOTATION_PREFIX = "http://purl.org/innovonto/i2m-legacy/cscw/annotator-submit/";

    private static final Model m = ModelFactory.createDefaultModel();

    public static final String STATUS_PREFIX = "http://purl.org/innovonto/i2m-legacy/status/";
    public static final Resource IdeaStatus = m.createResource(uri + "IdeaStatus");

    //TODO CeRRI Ideas Stuff
    //TODO maybe group phase stuff? filtered, enhanced, contentGerman, userRole
    public static final Property legacyStatus  = m.createProperty(uri, "status");

    //TODO MTURK STUFF
    public static final String HIT_PREFIX = "http://purl.org/innovonto/i2m-legacy/mturk/hit/";
    public static final String ASSIGNMENT_PREFIX = "http://purl.org/innovonto/i2m-legacy/mturk/assignment/";

    public static final Resource HIT = m.createResource(uri + "HIT");
    public static final Property hitId  = m.createProperty(uri, "hitId");
    public static final Property assignmentId  = m.createProperty(uri, "assignmentId");

    //TODO CSCW STUFF
    public static final Resource Annotation = m.createResource(uri + "Annotation");
    public static final Property hasAnnotationResult  = m.createProperty(uri, "hasAnnotationResult");
}
