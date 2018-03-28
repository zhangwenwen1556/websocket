package com.example.websocket.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.Date;

@Document(collection = "Token")
public class Token implements Serializable {

    @Id
    private String token;
    /**
     * 用户Id
     */
    private String userId;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 过期时间
     */
    private Date expirationDate;


    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }

    @Override
    public String toString() {
        return "Token{" +
                "token='" + token + '\'' +
                ", userId=" + userId +
                ", expirationDate=" + expirationDate +
                '}';
    }
}
