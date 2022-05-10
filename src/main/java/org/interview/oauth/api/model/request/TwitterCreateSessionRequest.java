package org.interview.oauth.api.model.request;

public class TwitterCreateSessionRequest {
    private final String pinCode;

    public TwitterCreateSessionRequest(String pinCode) {
        this.pinCode = pinCode;
    }

    public String getPinCode() {
        return pinCode;
    }
}
