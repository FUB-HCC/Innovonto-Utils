package de.fuberlin.innovonto.utils.ideasimilarityappbackend;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.AbstractRequestLoggingFilter;

import javax.servlet.http.HttpServletRequest;

import static de.fuberlin.innovonto.utils.common.logging.HttpRequestUtils.getIP;
import static de.fuberlin.innovonto.utils.common.logging.HttpRequestUtils.getParameters;

@Component
public class CustomResponseLogger extends AbstractRequestLoggingFilter {
    private static final Logger log = LoggerFactory.getLogger(CustomResponseLogger.class);

    @Override
    protected void beforeRequest(HttpServletRequest request, String message) {
        final String requestURI = request.getRequestURI();
        log.info("[{}][{}]'{}'", getIP(request), request.getMethod(), requestURI + getParameters(request));
    }

    @Override
    protected void afterRequest(HttpServletRequest request, String message) {
        //skip
    }
}
