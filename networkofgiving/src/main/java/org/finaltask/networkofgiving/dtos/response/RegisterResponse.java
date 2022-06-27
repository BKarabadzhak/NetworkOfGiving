package org.finaltask.networkofgiving.dtos.response;

public class RegisterResponse {
    private MessageResponse messageResponse;
    private UserResponse userResponse;

    public RegisterResponse(MessageResponse messageResponse, UserResponse userResponse) {
        this.messageResponse = messageResponse;
        this.userResponse = userResponse;
    }

    public MessageResponse getMessageResponse() {
        return messageResponse;
    }

    public void setMessageResponse(MessageResponse messageResponse) {
        this.messageResponse = messageResponse;
    }

    public UserResponse getUserResponse() {
        return userResponse;
    }

    public void setUserResponse(UserResponse userResponse) {
        this.userResponse = userResponse;
    }
}
