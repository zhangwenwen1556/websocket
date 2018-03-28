package com.example.websocket.context;

public class UserSystemContext {

    /**
     * 用户Id
     */
    private String userId;

    /**
     * token
     * @return
     */
    private String token;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
