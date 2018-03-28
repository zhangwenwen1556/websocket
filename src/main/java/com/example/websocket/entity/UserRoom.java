package com.example.websocket.entity;

import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.Date;

@Document(collection = "UserRoom")
public class UserRoom implements Serializable {

    /**
     * id
     */
    private String id;
    /**
     * 用户Id
     */
    private String userId;
    /**
     * 房间Id
     */
    private String roomId;
    /**
     * 最后修改时间
     */
    private Date lastUpdateDate;
    /**
     * 房间名称
     */
    @Transient
    private String roomName;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public Date getLastUpdateDate() {
        return lastUpdateDate;
    }

    public void setLastUpdateDate(Date lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }
}
