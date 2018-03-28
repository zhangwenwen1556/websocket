package com.example.websocket.entity;

import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.Date;

@Document(collection = "ChatRecord")
public class ChatRecord implements Serializable {

    /**
     * 是否阅读 - 是
     */
    private final static Integer FLAG_READING = 1;
    /**
     * 是否阅读 - 否
     */
    private final static Integer FLAG_NO_READING = 2;

    /**
     * Id
     */
    private String id;
    /**
     * 发送人
     */
    private String sendUserId;

    /**
     * 接收人
     */
    private String receiveUserId;

    /**
     * 房间Id
     */
    private String roomId;

    /**
     * 发送的信息
     */
    private String massage;

    /**
     * 是否已读
     */
    private Integer flagReading;

    /**
     * 创建时间
     */
    private Date createDate;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSendUserId() {
        return sendUserId;
    }

    public void setSendUserId(String sendUserId) {
        this.sendUserId = sendUserId;
    }

    public String getReceiveUserId() {
        return receiveUserId;
    }

    public void setReceiveUserId(String receiveUserId) {
        this.receiveUserId = receiveUserId;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public String getMassage() {
        return massage;
    }

    public void setMassage(String massage) {
        this.massage = massage;
    }

    public Integer getFlagReading() {
        return flagReading;
    }

    public void setFlagReading(Integer flagReading) {
        this.flagReading = flagReading;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    @Override
    public String toString() {
        return "ChatRecord{" +
                "id='" + id + '\'' +
                ", sendUserId='" + sendUserId + '\'' +
                ", receiveUserId='" + receiveUserId + '\'' +
                ", roomId='" + roomId + '\'' +
                ", massage='" + massage + '\'' +
                ", flagReading=" + flagReading +
                ", createDate=" + createDate +
                '}';
    }
}
