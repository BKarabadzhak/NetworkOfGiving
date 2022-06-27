package org.finaltask.networkofgiving.dtos.response;

public class LoginResponse {
    private String token;

    private UserResponse userResponse;

    public LoginResponse(String token, UserResponse userResponse) {
        this.token = token;
        this.userResponse = userResponse;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String accessToken) {
        this.token = token;
    }

    public UserResponse getUserResponse() {
        return userResponse;
    }

    public void setUserResponse(UserResponse userResponse) {
        this.userResponse = userResponse;
    }
}
