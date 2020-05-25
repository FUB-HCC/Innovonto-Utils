package de.fuberlin.innovonto.utils.ideasimilarityappbackend.api.client.annotation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/api/")
public class AnnotationApiProxyController {
    private final AnnotationService annotationService;

    @Autowired
    public AnnotationApiProxyController(AnnotationService annotationService) {
        this.annotationService = annotationService;
    }

    //TODO parameters are missing
    @GetMapping(value = "/annotate", produces = MediaType.APPLICATION_JSON_VALUE)
    public String annotate(@RequestParam(name = "text") String text) throws IOException {
        return annotationService.getResponse(text);
    }

}
