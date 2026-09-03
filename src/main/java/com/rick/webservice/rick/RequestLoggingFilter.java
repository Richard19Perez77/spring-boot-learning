package com.rick.webservice.rick;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * Logs incoming requests before controllers handle them.
 * When JPA is available, each request is also stored in user_request_logs.
 */
@Component
public class RequestLoggingFilter implements Filter {

    private static final Logger logger = LoggerFactory.getLogger(RequestLoggingFilter.class);
    private static final ObjectMapper objectMapper = new ObjectMapper();

    private final UserRequestLogRepository logRepository;

    public RequestLoggingFilter(UserRequestLogRepository logRepository) {
        this.logRepository = logRepository;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        String method = req.getMethod();
        String url = req.getRequestURL().toString();
        String ipAddress = req.getRemoteAddr();

        Map<String, String> headersMap = new HashMap<>();
        Enumeration<String> headerNames = req.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            headersMap.put(headerName, req.getHeader(headerName));
        }

        String headersJson = objectMapper.writeValueAsString(headersMap);
        UserRequestLog logEntry = new UserRequestLog(method, url, ipAddress, headersJson, "{}");
        logRepository.save(logEntry);

        logger.info("Logged Request: {} {} from {}", method, url, ipAddress);
        logger.info("=== Incoming Request ===");
        logger.info("Method: {}", req.getMethod());
        logger.info("URL: {}", req.getRequestURL());
        logger.info("Query Params: {}", req.getQueryString());
        logger.info("Remote Address: {}", req.getRemoteAddr());

        headerNames = req.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            logger.info("Header: {} = {}", headerName, req.getHeader(headerName));
        }

        req.getParameterMap().forEach((key, value) ->
                logger.info("Param: {} = {}", key, String.join(",", value)));

        chain.doFilter(request, response);
    }
}
