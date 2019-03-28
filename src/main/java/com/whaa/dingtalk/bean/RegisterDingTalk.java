package com.whaa.dingtalk.bean;

import java.io.Serializable;

/**
 * created by wangzelong 2019/3/28 15:10
 */
public class RegisterDingTalk implements Serializable {

    private String fromServer;

    private String authToken;

    public String getFromServer() {
        return fromServer;
    }

    public void setFromServer(String fromServer) {
        this.fromServer = fromServer;
    }

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }
}
