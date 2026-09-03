package com.rick.webservice.rick;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

import java.util.Date;

@Entity
@Table(name = "user_request_logs")
public class UserRequestLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Temporal(TemporalType.TIMESTAMP)
    private Date timestamp = new Date();

    private String method;
    private String url;
    private String ipAddress;

    @Column(columnDefinition = "TEXT")
    private String headers;

    @Column(columnDefinition = "TEXT")
    private String requestBody;

    public UserRequestLog() {}

    public UserRequestLog(String method, String url, String ipAddress, String headers, String requestBody) {
        this.method = method;
        this.url = url;
        this.ipAddress = ipAddress;
        this.headers = headers;
        this.requestBody = requestBody;
    }

    public Long getId() {
        return id;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public String getMethod() {
        return method;
    }

    public String getUrl() {
        return url;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public String getHeaders() {
        return headers;
    }

    public String getRequestBody() {
        return requestBody;
    }
}
