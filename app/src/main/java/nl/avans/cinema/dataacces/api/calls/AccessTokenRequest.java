package nl.avans.cinema.dataacces.api.calls;

public class AccessTokenRequest {
    private String request_token;

    public AccessTokenRequest(String requestToken) {
        this.request_token = requestToken;
    }

    public String getRequest_token() {
        return request_token;
    }

    public void setRequest_token(String request_token) {
        this.request_token = request_token;
    }
}
