package org.interview.oauth.api.model.response;

import static com.google.api.client.http.HttpStatusCodes.STATUS_CODE_OK;
import static org.interview.oauth.util.AppConstants.SUCCESS;

public class GenericResponse<T> {
    private final T body;
    private final Response response;

    public GenericResponse(T body, Response response) {
        this.body = body;
        this.response = response;
    }

    public GenericResponse(T body) {
        this.body = body;
        this.response = new Response(STATUS_CODE_OK,SUCCESS);
    }

    public GenericResponse(Response response) {
        this.body = null;
        this.response = response;
    }

    public T getT() {
        return body;
    }

    public Response getResponse() {
        return response;
    }

    public T getBody() {
        return body;
    }
}
