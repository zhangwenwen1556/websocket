package com.example.websocket.entity;

import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Document(collection = "Room")
public class Room implements Serializable {

    /**
     * Id
     */
    private String id;
    /**
     * 房间名称
     */
    private String roomName;

    /**
     * 房间类型 (单独聊天、群聊)
     */
    private Integer roomType;

    /**
     * 用户list
     */
    private Set<String> userList;

    /**
     * 创建时间
     */
    private Date createTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public Integer getRoomType() {
        return roomType;
    }

    public void setRoomType(Integer roomType) {
        this.roomType = roomType;
    }

    public Set<String> getUserList() {
        return userList;
    }

    public void setUserList(Set<String> userList) {
        this.userList = userList;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "Room{" +
                "id='" + id + '\'' +
                ", roomName='" + roomName + '\'' +
                ", roomType=" + roomType +
                ", userList=" + userList +
                ", createTime=" + createTime +
                '}';
    }
}
