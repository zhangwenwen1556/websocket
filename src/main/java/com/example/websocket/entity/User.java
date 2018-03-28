package com.example.websocket.entity;

import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.*;

@Document(collection = "User")
public class User implements Serializable {

    /**
     * 用户角色 - 管理员
     */
    public static final Integer ROLE_ADMIN = 1;
    /**
     * 用户角色 - 普通用户
     */
    public static final Integer ROLE_AVERAGE = 2;

    /**
     * 用户状态 - 正常
     */
    public static final Integer STATUS_NORMAL = 1;
    /**
     * 用户状态 - 删除
     */
    public static final Integer STATUS_DELETE = 2;

    /**
     * 用户角色数字中文对照
     */
    public static final Map<Integer, String> roleStr = new HashMap<Integer, String>(){{
        put(ROLE_ADMIN, "管理员");
        put(ROLE_AVERAGE, "普通用户");
    }};
    /**
     * 用户状态数字中文对照
     */
    public static final Map<Integer, String> statusStr = new HashMap<Integer, String>(){{
        put(STATUS_NORMAL, "正常");
        put(STATUS_DELETE, "删除");
    }};

    /**
     * Id
     */
    private String id;

    /**
     * 登录名
     */
    private String userName;

    /**
     * 登录密码
     */
    private String password;

    /**
     * 用户状态
     */
    private Integer status;

    /**
     * 用户角色
     */
    private Integer userRole;

    /**
     * 昵称
     */
    private String nickName;
    /**
     * 用户名字
     */
    private String name;

    /**
     * 用户性别
     */
    private String sex;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 用户所拥有的房间
     */
    private Set<String> roomIdSet;

    @Transient
    private String token;

    @Transient
    private List<UserRoom> userRooms;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getUserRole() {
        return userRole;
    }

    public void setUserRole(Integer userRole) {
        this.userRole = userRole;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Set<String> getRoomIdSet() {
        return roomIdSet;
    }

    public void setRoomIdSet(Set<String> roomIdSet) {
        this.roomIdSet = roomIdSet;
    }

    public List<UserRoom> getUserRooms() {
        return userRooms;
    }

    public void setUserRooms(List<UserRoom> userRooms) {
        this.userRooms = userRooms;
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", status=" + status +
                ", userRole=" + userRole +
                ", nickName='" + nickName + '\'' +
                ", name='" + name + '\'' +
                ", sex='" + sex + '\'' +
                ", createTime=" + createTime +
                ", token='" + token + '\'' +
                '}';
    }
}
