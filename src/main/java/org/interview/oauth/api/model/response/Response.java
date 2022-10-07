package org.interview.oauth.api.model.response;

public class Response {
    private final int status;
    private final String message;

    public Response(final int status, final String message) {
        this.status = status;
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}