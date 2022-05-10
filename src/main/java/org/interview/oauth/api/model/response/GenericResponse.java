package org.interview.oauth.api.model.response;

public class GenericResponse<T> {
    private final T body;
    private final Response response;

    public GenericResponse(T body, Response response) {
        this.body = body;
        this.response = response;
    }

    public GenericResponse(T body) {
        this.body = body;
        this.response = new Response(200,"SUCCESS");
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
